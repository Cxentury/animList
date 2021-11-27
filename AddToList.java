import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.sql.*;

@WebServlet("/addToList")
public class AddToList extends HttpServlet{
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        Connection con=null;        
        PrintWriter out = res.getWriter();

        try {
            
            res.setContentType("text/html");
        
            out.println("<!DOCTYPE html><html lang='fr'>");
            out.println("<head><meta charset='utf-8'><meta http-equiv='X-UA-Compatible' content='IE=edge'><meta name='viewport' content='width=device-width, initial-scale=1'>");        
            out.println("<title>Result</title>");
            out.println("<link href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css' rel='stylesheet'>");
            out.println("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css'/>");
            out.print("<script src='script.js'></script>");
            out.println("</head>");
            out.println("<body onload='restore()'>");

            Class.forName("org.postgresql.Driver");
                
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/animeList","user","moi");
            
            HttpSession session=req.getSession();
            User user=(User) session.getAttribute("user");

            //That's quite the long request
            String alreadyExists="select * from list where animeid=? and userid=?";
            String insert="insert into list values(?,?)";

            PreparedStatement check=con.prepareStatement(alreadyExists);
            check.setInt(1,Integer.parseInt(req.getParameter("id")));
            check.setInt(2,user.getId());
            ResultSet result=check.executeQuery();

            if(result.isBeforeFirst()){
                out.print("<div class='alert alert-danger' role='alert'>This anime is already in your list! <a href='animList' class='alert-link'>Return to menu</a></div>");
                return;
            }

            PreparedStatement request=con.prepareStatement(insert);
            request.setInt(1,Integer.parseInt(req.getParameter("id")));
            request.setInt(2,user.getId());

            request.executeUpdate();

            out.print("<div class='alert alert-success' role='alert'>Anime successfully added to list! <a href='animList' class='alert-link'>Return to menu</a></div>");

        }catch (Exception e) {}
        finally
        {
        try{con.close();} catch (Exception e){}
        }

    }
}