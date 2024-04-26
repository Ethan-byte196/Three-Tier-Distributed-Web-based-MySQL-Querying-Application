/*  Name: Ethan Fuller  
Course: CNT 4714 – Spring 2024 – Project Four 
Assignment title:  A Three-Tier Distributed Web-Based Application 
Date:  April 23, 2024 
*/ 
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.jdbc.result.ResultSetMetaData;


@SuppressWarnings("serial")
public class Root extends HttpServlet {
	
	private static FileInputStream filein = null;
	
	private static Properties root = new Properties();
	
	private static MysqlDataSource dataSource = null;
	
	private static Connection connection = null;
	
	private PreparedStatement pstatement = null;
	
	private static ResultSet selectResults = null;
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String sqlStatement = request.getParameter("sqlStatement");
		sqlStatement = sqlStatement.trim();
		String message = "";
		HttpSession session = request.getSession();
		
		if(sqlStatement.contains("select")) {
		try {
			filein = new FileInputStream("C://Program Files//Apache Software Foundation//Tomcat 10.1//webapps//Project-4//WEB-INF//lib//root.properties/");
			root.load(filein);
			dataSource = new MysqlDataSource();
		
			dataSource.setURL(root.getProperty("MYSQL_DB_URL"));
			dataSource.setUser(root.getProperty("MYSQL_DB_USERNAME"));
			dataSource.setPassword(root.getProperty("MYSQL_DB_PASSWORD"));
			
			connection = dataSource.getConnection();
			
			pstatement = connection.prepareStatement(sqlStatement);
			
			selectResults = pstatement.executeQuery();
			
			
			ResultSetMetaData metaData = (ResultSetMetaData) selectResults.getMetaData();

            // Get the number of columns in the ResultSet
            int columnCount = metaData.getColumnCount();

            // Add table headers with red color
            message += "<tr>";
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                message += "<th style='color: red;'>" + columnName + "</th>";
            }
            message += "</tr>";

            // Add table rows with alternating row colors
            boolean evenRow = false;
            while (selectResults.next()) {
                // Determine the background color for the row
                String rowColor = evenRow ? "#f2f2f2" : "#ffffff";
                evenRow = !evenRow;

                // Start the row with the determined background color
                message += "<tr style='background-color: " + rowColor + "'>";

                // Add table cells with column values and black text color
                for (int i = 1; i <= columnCount; i++) {
                    String columnValue = selectResults.getString(i);
                    message += "<td style='color: black;'>" + columnValue + "</td>";
                }

                // End the row
                message += "</tr>";
            }
			
			session.setAttribute("message",  message);
			session.setAttribute("sqlStatement",sqlStatement);
			RequestDispatcher dispatcher = request.getRequestDispatcher("rootHome.jsp");
			dispatcher.forward(request, response);
			 
			pstatement.close();
		}
		catch (IOException | SQLException e) {
			message = "<tr bgcolor=\"#ff0000\"><td><font color=\"#ffffff\"><b>Error executing the SQL statement:</b><br>" + e.getMessage() + "</td></tr>";
			 session.setAttribute("message",  message);
			 session.setAttribute("sqlStatement",sqlStatement);
			 RequestDispatcher dispatcher = request.getRequestDispatcher("rootHome.jsp");
			 dispatcher.forward(request, response);
        }
		}
		else //contains insert or update
		{
			boolean logic = false;
			int suppliers = 0;
			
			try {
				
				filein = new FileInputStream("C://Program Files//Apache Software Foundation//Tomcat 10.1//webapps//Project-4//WEB-INF//lib//root.properties/");
				root.load(filein);
				dataSource = new MysqlDataSource();
			
				dataSource.setURL(root.getProperty("MYSQL_DB_URL"));
				dataSource.setUser(root.getProperty("MYSQL_DB_USERNAME"));
				dataSource.setPassword(root.getProperty("MYSQL_DB_PASSWORD"));
				
				connection = dataSource.getConnection();
				
				pstatement = connection.prepareStatement(sqlStatement);
				
				int rowsAffected = pstatement.executeUpdate();
				
				
				if(sqlStatement.contains("shipments") && sqlStatement.contains("insert"))
				{
					// Regular expression to extract the string from the first spot and the fourth value after "VALUES"
			        Pattern pattern = Pattern.compile("values \\('([^']+)',\\s*.*?,\\s*.*?,\\s*(\\d+)\\)");
			        Matcher matcher = pattern.matcher(sqlStatement);
			        
			        String firstValue = ""; // Default value if not found
			        int fourthValue = -1; // Default value if not found
			        
			        if (matcher.find()) {
			            firstValue = matcher.group(1);
			            fourthValue = Integer.parseInt(matcher.group(2));
			        }
			        
			        if(fourthValue >= 100)
			        {
			        	logic = true;
		                                                                                                                                	        	
			        	String logicStatement = "UPDATE suppliers " +
		                          "SET status = status + 5 " +
		                          "WHERE snum IN (SELECT snum FROM shipments WHERE quantity >= 100)";
			        	
			        	pstatement = connection.prepareStatement(logicStatement);
			            
			            suppliers = pstatement.executeUpdate();
			        }
				}
				
				else if(sqlStatement.contains("shipments") && sqlStatement.contains("update") && sqlStatement.contains("quantity"))
				{
			        logic = true;
			        
			        String logicStatement = "UPDATE suppliers " +
	                          "SET status = status + 5 " +
	                          "WHERE snum IN (SELECT snum FROM shipments WHERE quantity >= 100)";

			        pstatement = connection.prepareStatement(logicStatement);

			        suppliers = pstatement.executeUpdate();
				}
				
				
				if (logic == false) {
				    message = "<tr bgcolor=\"#00ff00\"><td><font color=\"#000000\"><b>The statement executed successfully.</b><br>"+rowsAffected+" row(s) affected.<br>Business Logic Not Triggered</td></tr>";
				} else {
				    message = "<tr bgcolor=\"#00ff00\"><td><font color=\"#000000\"><b>The statement executed successfully.</b><br>"+rowsAffected+" row(s) affected.<br>Business Logic Detected! - Updated Supplier Status<br>Business Logic updated "+suppliers+" supplier status marks</td></tr>";
				}
			
				session.setAttribute("message",  message);
				session.setAttribute("sqlStatement",sqlStatement);
				RequestDispatcher dispatcher = request.getRequestDispatcher("rootHome.jsp");
				dispatcher.forward(request, response);
				 
				pstatement.close();
			
			}
			catch (IOException | SQLException e) {
				message = "<tr bgcolor=\"#ff0000\"><td><font color=\"#ffffff\"><b>Error executing the SQL statement:</b><br>" + e.getMessage() + "</td></tr>";
				 session.setAttribute("message",  message);
				 session.setAttribute("sqlStatement",sqlStatement);
				 RequestDispatcher dispatcher = request.getRequestDispatcher("rootHome.jsp");
				 dispatcher.forward(request, response);
	        }
			
		}
		
	}
	

}
