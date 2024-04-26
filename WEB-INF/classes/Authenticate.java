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

@SuppressWarnings("serial")
public class Authenticate extends HttpServlet{
	
	
	private static Properties system = new Properties();
	
	private static FileInputStream filein = null;
	
	static boolean userCredentials = false;
	
	 public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String credentialsQuery = "select * from usercredentials where login_username = ? and login_password = ?";
		
		try {
				filein = new FileInputStream("C://Program Files//Apache Software Foundation//Tomcat 10.1//webapps//Project-4//WEB-INF//lib//system.properties/");
				system.load(filein);
				
				MysqlDataSource dataSource = new MysqlDataSource();
				dataSource.setURL(system.getProperty("MYSQL_DB_URL"));
				dataSource.setUser(system.getProperty("MYSQL_DB_USERNAME"));
				dataSource.setPassword(system.getProperty("MYSQL_DB_PASSWORD"));
				
				Connection connection = dataSource.getConnection();
				PreparedStatement pstatement = connection.prepareStatement(credentialsQuery);
				
				pstatement.setString(1,username);
				pstatement.setString(2,password);
				
				ResultSet lookupResults = pstatement.executeQuery();
				
				
				if(lookupResults.next())
				{
					userCredentials = true;
				}
								
				
				if(userCredentials == true)
				{
					if(username.equals("root"))
					{
						response.sendRedirect("rootHome.jsp");
					}
					else if(username.equals("client"))
					{
						response.sendRedirect("clientHome.jsp");
					}
					else if(username.equals("dataentryuser"))
					{
						response.sendRedirect("dataentryHome.jsp");
					}
					else if(username.equals("theaccountant"))
					{
						response.sendRedirect("accountantHome.jsp");
					}
				}
			
				else {
					response.sendRedirect("authenticationError.html");
				}

			}
		 catch (IOException | SQLException e) {
	            response.sendRedirect("authenticationError.html"); // Redirect to an error page if there's an exception
	        }
		
			
	}	
}
