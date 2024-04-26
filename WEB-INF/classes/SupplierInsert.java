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


public class SupplierInsert extends HttpServlet {
	
	private static FileInputStream filein = null;
	
	private static Properties dataentry = new Properties();
	
	private static MysqlDataSource dataSource = null;
	
	private static Connection connection = null;
	
	private PreparedStatement pstatement = null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String snumString = request.getParameter("snum_input");
		String snameString = request.getParameter("sname_input");
		String statusString = request.getParameter("status_input");
		String scityString = request.getParameter("scity_input");
	
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
			
			String sqlStatement = "insert into suppliers values(?,?,?,?)";
			
	
			pstatement = connection.prepareStatement(sqlStatement);
			
			pstatement.setString(1, snumString);
		    pstatement.setString(2, snameString); 
		    pstatement.setString(3, statusString); 
		    pstatement.setString(4, scityString);
			
			int rowsAffected = pstatement.executeUpdate();
			
	
			message = "<tr bgcolor=\"#0000ff\"><td><font color=\"#000000\"><b>New Supplier Record: (" + snumString + " , " + snameString + " , " + statusString + " , " + scityString + ") - successfully entered into the database.</td></tr>";
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
