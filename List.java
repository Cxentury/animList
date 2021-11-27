import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

//NOTE : peut être retirer le bouton watch et laisser directement cliquer sur les photos
// Aussi rajoute une banniere sur le côté avec le profil de l'utilisateur
//Peut être mettre les animés sur forme de Card

@WebServlet("/animList")
public class List extends HttpServlet{
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        ResultSet result;
        
        PrintWriter out = res.getWriter();
        res.setContentType("text/html");
        
        /* NE PAS MODIFIER (Sauf indication)*/
        out.println("<!DOCTYPE html><html lang='fr'>");
        out.println("<head><meta charset='utf-8'><meta http-equiv='X-UA-Compatible' content='IE=edge'><meta name='viewport' content='width=device-width, initial-scale=1'>");
        
            /* Titre de la page HTML */
        out.println("<title>Page de login</title>");
            /* **************** */
        
        out.println("<link href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css'/>");
        out.println("<link rel='stylesheet' href='custom.css'/>");
        out.print("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>");
        out.print("<script src='script.js'></script>");
        out.println("</head>");
        out.println("<body class='' id='bd' onload='restore()'>");
    
        Connection con=null;

        try {
            
            // enregistrement du driver
            Class.forName("org.postgresql.Driver");
            
            // connexion a la base
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/animeList","user","moi");
            String query="";
            HttpSession session=req.getSession(true);
            PreparedStatement request;

            if(req.getParameter("id")==null){
                query="select * from animes inner join list on list.animeid=animes.animeid and userid=(select id from users where login like ? )";
                request=con.prepareStatement(query);
                User user=(User) session.getAttribute("user");
                request.setString(1, user.getLogin());

            }
            else{
                query="select * from animes inner join list on list.animeid=animes.animeid and userid=?";
                request=con.prepareStatement(query);
                request.setInt(1, Integer.parseInt(req.getParameter("id")));
            }

            result=request.executeQuery();
	    
        out.print("<div class='container-fluid'>");
            out.println("<nav class='navbar navbar-expand-lg navbar-dark bg-primary'>");
                out.println("<div class='container-fluid'>");
                    out.println("<a class='navbar-brand' href='animList'>AnimeList</a>");
                    out.println("<button class='navbar-toggler' type='button' data-bs-toggle='collapse' data-bs-target='#navbarSupportedContent' aria-controls='navbarSupportedContent' aria-expanded='false' aria-label='Toggle navigation'>");
                        out.println("<span class='navbar-toggler-icon'></span>");
                    out.println("</button>");
                    out.println("<div class='collapse navbar-collapse' id='navbarSupportedContent'>");
                        out.println("<ul class='navbar-nav me-auto mb-2 mb-lg-0'>");
                        out.println("<li class='nav-item'>");
                        out.println("<a class='nav-link active' aria-current='page' href='#'>Home</a></li>");
                        out.println("<li class='nav-item'>");
                        out.println("<a class='nav-link' href='#'>Link</a></li>");
                        out.println("<form class='d-flex' method='POST' action='Search'>");
                            out.println("<input class='form-control me-2' type='search' placeholder='Anime/User' aria-label='Search' name='search'>");
                            out.println("<button class='btn btn-dark' type='submit'>Search</button>");
                        out.println("</form>");
                    out.println("</div>");
                    out.println("<button class='btn' id='btn' onClick='darkMode()'><i class='fa fa-moon-o'></i></button>");
                    // out.print("<button type='button' class='btn btn-dark' onclick='changeBorders()'>Change Layout</button>");

                out.println("</div>");
            out.println("</nav>");

            out.print("<div class='container rounded'>");
                out.print("<div class='row justify-content-center animate__animated animate__fadeInDown margin border border-primary rounded'>");
                    out.print("<div class='col' align='center' id='borderImageCol'>");
                        out.print("<p>Image<p>");
                    out.print("</div>");
                    out.print("<div class='col' align='center' id='borderNameCol' >");
                        out.print("<p>Name<p>");
                    out.print("</div>");

                    out.print("<div class='col ' align='center' id='borderLink'>");
                        out.print("<p>Link to episodes<p>");
                    out.print("</div>");

                    out.print("<div class='w-100'></div>");
                    out.print("<br>");

                    while(result.next())
                    {
                        out.print("<div class='col' align='center' id='borderImage'>");
                            out.print("<img class='image' src='"+result.getString("imagelink")+"' style='max-width:100px;max-height:100px;image-rendering: -webkit-optimize-contrast;' class='rounded'>");
                        out.print("</div>");

                        out.print("<div class='col' align='center' id='borderName'>");
                            out.print("<h5 style='padding-top:10%'>"+result.getString("name")+"</h5>");
                        out.print("</div>");

                        out.print("<div class='col' align='center'id='borderWatch'>");
                            out.print("<a class='btn btn-success' href='playVideo?name="+result.getString("name")+"&ep=1' role='button'>Watch</a>");
                        out.print("</div>");

                        out.print("<div class='w-100'></div>");

                        out.print("<br>");

                    }

                out.print("</div>");
            out.print("</div>");

        out.print("</div>");

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