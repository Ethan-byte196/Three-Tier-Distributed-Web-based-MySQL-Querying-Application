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

import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.jdbc.result.ResultSetMetaData;


public class ShipmentInsert extends HttpServlet {
	
	private static FileInputStream filein = null;
	
	private static Properties dataentry = new Properties();
	
	private static MysqlDataSource dataSource = null;
	
	private static Connection connection = null;
	
	private PreparedStatement pstatement = null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		boolean logic = false; 
		
		String ship_snum_input = request.getParameter("ship_snum_input");
		String ship_pnum_input = request.getParameter("ship_pnum_input");
		String ship_jnum_input = request.getParameter("ship_jnum_input");
		String quantity_input = request.getParameter("quantity_input");
		
		int quantity = Integer.parseInt(quantity_input);
		
		if(quantity >= 100)
		{
			logic = true;
		}
		
	
		String message = "";
		HttpSession session = request.getSession();
		
		try {
			filein = new FileInputStream("C://Program Files//Apache Software Foundation//Tomcat 10.1//webapps//Project-4//WEB-INF//lib//data-entry.properties/");
			dataentry.load(filein);
			dataSource = new MysqlDataSource();
		
			dataSource.setURL(dataentry.getProperty("MYSQL_DB_URL"));
			dataSource.setUser(dataentry.getProperty("MYSQL_DB_USERNAME"));
			dataSource.setPassword(dataentry.getProperty("MYSQL_DB_PASSWORD"));
			
			connection = dataSource.getConnection();
			
			String sqlStatement = "insert into shipments values(?,?,?,?)";
			
	
			pstatement = connection.prepareStatement(sqlStatement);
			
			pstatement.setString(1, ship_snum_input);
		    pstatement.setString(2, ship_pnum_input); 
		    pstatement.setString(3, ship_jnum_input); 
		    pstatement.setString(4, quantity_input);
			
			int rowsAffected = pstatement.executeUpdate();
			
			
			if(logic == true)
			{
			
				String logicStatement = "UPDATE suppliers " +
                        "SET status = status + 5 " +
                        "WHERE snum IN (SELECT snum FROM shipments WHERE quantity >= 100)";
				
				 pstatement = connection.prepareStatement(logicStatement);

			     pstatement.executeUpdate();
				
			    message = "<tr bgcolor=\"#0000ff\"><td><font color=\"#000000\"><b>New Supplier Record: (" + ship_snum_input + " , " + ship_pnum_input + " , " + ship_jnum_input + " , " + quantity_input + ") - successfully entered into the database. Business logic was triggered</td></tr>";
				session.setAttribute("message",  message);
				RequestDispatcher dispatcher = request.getRequestDispatcher("dataentryHome.jsp");
				dispatcher.forward(request, response);
						 
				pstatement.close();
			     
			}
			
	
			message = "<tr bgcolor=\"#0000ff\"><td><font color=\"#000000\"><b>New Supplier Record: (" + ship_snum_input + " , " + ship_pnum_input + " , " + ship_jnum_input + " , " + quantity_input + ") - successfully entered into the database.</td></tr>";
			session.setAttribute("message",  message);
			RequestDispatcher dispatcher = request.getRequestDispatcher("dataentryHome.jsp");
			dispatcher.forward(request, response);
				 
			pstatement.close();
			
		}
		catch (IOException | SQLException e) {
			message = "<tr bgcolor=\"#ff0000\"><td><font color=\"#ffffff\"><b>Error executing the SQL statement:</b><br>" + e.getMessage() + "</td></tr>";
			 session.setAttribute("message",  message);
			 RequestDispatcher dispatcher = request.getRequestDispatcher("dataentryHome.jsp");
			 dispatcher.forward(request, response);
			
		}
	}

}
