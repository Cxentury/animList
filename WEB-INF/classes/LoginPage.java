import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import userPackage.User;

import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/LoginPage")
public class LoginPage extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	PrintWriter out = res.getWriter();
	res.setContentType("text/html");
	
	/* NE PAS MODIFIER (Sauf indication)*/
	out.println("<!DOCTYPE html><html lang='fr'>");
	out.println("<head><meta charesultet='utf-8'><meta http-equiv='X-UA-Compatible' content='IE=edge'><meta name='viewport' content='width=device-width, initial-scale=1'>");
	
		/* Titre de la page HTML */
	out.println("<title>Login</title>");
		/* **************** */
	
	out.println("<link href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css' rel='stylesheet'>");
	out.print("<script src='script.js'></script>");

	out.println("</head>");
	out.println("<body id='bd' onload='restore()'>");

	Connection con=null;
	try {
	    
	    // enregistrement du driver
	    Class.forName("org.postgresql.Driver");
	    
	    // connexion a la base
	    con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/animeList","user","moi");
        String query="select * from users where login=? and password=?";
        PreparedStatement request=con.prepareStatement(query);
        request.setString(1, req.getParameter("login"));
        request.setString(2, req.getParameter("password"));
        ResultSet result=request.executeQuery();
        
	    
	    if(result.next()) {
			HttpSession session = req.getSession(true);
			User p = new User(result.getString("login"),result.getString("username"),result.getString("password"),result.getInt("id"));
			session.setAttribute("user", p);
			con.close();
			res.sendRedirect("animList");

	    } else { 
	    	out.println("<div class='container'>");
		    	out.println("<div class='page-header'>");
					out.println("<h1>Authentification</h1>");
				out.println("</div>");
		    	
				out.println("<div class='row'>");
					out.println("<div class='col-xs-12'>");
						out.println("<div class='alert alert-danger' role='alert'>Wrong login or password</div>");
					    con.close();
					    out.println("<a href='login.html'><button type='button' class='btn btn-default btn-lg'>Home</button></a>");
				    out.println("</div>");
			    out.println("</div>");
		    out.println("</div>");
	    }
	    
	    out.println("</center>");
	}
	catch (Exception e) {
		out.println("<div class='alert alert-warning' role='alert'>Internal Error</div>");
	}
	finally
	    {
		try{con.close();} catch (Exception e){}
	    }
	
	out.println("</body></html>");
    }
}
