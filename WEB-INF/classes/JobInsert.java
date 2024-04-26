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


public class JobInsert extends HttpServlet {
	
	private static FileInputStream filein = null;
	
	private static Properties dataentry = new Properties();
	
	private static MysqlDataSource dataSource = null;
	
	private static Connection connection = null;
	
	private PreparedStatement pstatement = null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String jnumString = request.getParameter("jnum_input");
		String jnameString = request.getParameter("jname_input");
		String numworkersString = request.getParameter("numworkers_input");
		String jcityString = request.getParameter("jcity_input");
	
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
			
			String sqlStatement = "insert into jobs values(?,?,?,?)";
			
	
			pstatement = connection.prepareStatement(sqlStatement);
			
			pstatement.setString(1, jnumString);
		    pstatement.setString(2, jnameString); 
		    pstatement.setString(3, numworkersString); 
		    pstatement.setString(4, jcityString);
			
			int rowsAffected = pstatement.executeUpdate();
			
	
			message = "<tr bgcolor=\"#0000ff\"><td><font color=\"#000000\"><b>New Supplier Record: (" + jnumString + " , " + jnameString + " , " + numworkersString + " , " + jcityString + ") - successfully entered into the database.</td></tr>";
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
