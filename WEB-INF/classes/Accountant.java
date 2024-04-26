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

import com.mysql.cj.jdbc.CallableStatement;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.jdbc.result.ResultSetMetaData;

@SuppressWarnings("serial")
public class Accountant extends HttpServlet{
	
	private static FileInputStream filein = null;
	
	private static Properties accountant = new Properties();
	
	private static MysqlDataSource dataSource = null;
	
	private static Connection connection = null;
	
	private CallableStatement callableStatement = null;
	
	private static ResultSet selectResults = null;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String message = "";
		HttpSession session = request.getSession();
		String sqlStatement = "";
		
		filein = new FileInputStream("C://Program Files//Apache Software Foundation//Tomcat 10.1//webapps//Project-4//WEB-INF//lib//accountant.properties/");
		accountant.load(filein);
		dataSource = new MysqlDataSource();
	
		dataSource.setURL(accountant.getProperty("MYSQL_DB_URL"));
		dataSource.setUser(accountant.getProperty("MYSQL_DB_USERNAME"));
		dataSource.setPassword(accountant.getProperty("MYSQL_DB_PASSWORD"));
	
		
		String selectedOperation = request.getParameter("operation");
		
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (selectedOperation != null) {
	        switch (selectedOperation) {
	            case "max_status":
				try {
				callableStatement = (CallableStatement) connection.prepareCall("{call Get_The_Maximum_Status_Of_All_Suppliers()}");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	                break;
	            case "total_weight":
				try {
					callableStatement = (CallableStatement) connection.prepareCall("{call Get_The_Sum_Of_All_Parts_Weights()}");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	                break;
	            case "total_shipments":
				try {
					callableStatement = (CallableStatement) connection.prepareCall("{call Get_The_Total_Number_Of_Shipments()}");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	               
	                break;
	            case "job_with_most_workers":
				try {
					callableStatement = (CallableStatement) connection.prepareCall("{call Get_The_Name_Of_The_Job_With_The_Most_Workers()}");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            	
	                break;
	            default:
				try {
					callableStatement = (CallableStatement) connection.prepareCall("{call List_The_Name_And_Status_Of_All_Suppliers()}");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            	
	                break;
	        }
	    }
		else
		{
			message = "<tr bgcolor=\"#ff0000\"><td><font color=\"#ffffff\"><b>Error executing the SQL statement:</b><br>Please select an option</td></tr>";
  			 session.setAttribute("message",  message);
  			 RequestDispatcher dispatcher = request.getRequestDispatcher("accountantHome.jsp");
  			 dispatcher.forward(request, response);
		}
		
		try {
			
			callableStatement.execute();
			
			selectResults = callableStatement.getResultSet();
			
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
			RequestDispatcher dispatcher = request.getRequestDispatcher("accountantHome.jsp");
			dispatcher.forward(request, response);
			 
			callableStatement.close();
			
		} catch (SQLException e) {
			message = "<tr bgcolor=\"#ff0000\"><td><font color=\"#ffffff\"><b>Error executing the SQL statement:</b><br>" + e.getMessage() + "</td></tr>";
			 session.setAttribute("message",  message);
			 session.setAttribute("sqlStatement",sqlStatement);
			 RequestDispatcher dispatcher = request.getRequestDispatcher("accountantHome.jsp");
			 dispatcher.forward(request, response);
		}
	
	}
}
