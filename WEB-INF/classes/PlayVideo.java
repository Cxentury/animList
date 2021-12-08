import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


@WebServlet("/playVideo")
public class PlayVideo extends HttpServlet{

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        PrintWriter out = res.getWriter();
        res.setContentType("text/html");
        
        /* NE PAS MODIFIER (Sauf indication)*/
        out.println("<!DOCTYPE html><html lang='fr'>");
        out.println("<head><meta charesultet='utf-8'><meta http-equiv='X-UA-Compatible' content='IE=edge'><meta name='viewport' content='width=device-width, initial-scale=1'>");
        
            /* Titre de la page HTML */
        out.println("<title>Page de login</title>");
            /* **************** */
        
        out.println("<link href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='custom.css' rel='stylesheet'>");
        out.print("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>");
        out.print("<script src='script.js'></script>");
        out.println("</head>");

    out.println("<body id='bd' onload='restore()'>");


        out.println("<div class='container-fluid'>");
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
                    out.println("<a class='nav-link' href='Profile.jsp'>Profile</a></li>");
                    out.println("<form class='d-flex' method='POST' action='Search'>");
                        out.println("<input class='form-control me-2' type='search' placeholder='Anime/User' aria-label='Search' name='search'>");
                        out.println("<button class='btn btn-dark' type='submit'>Search</button>");
                    out.println("</form>");
                out.println("</div>");
                out.println("<button class='btn' id='btn' onClick='darkMode()'><i class='fa fa-moon-o'></i></button>");
                // out.print("<button type='button' class='btn btn-dark' onclick='changeBorders()'>Change Layout</button>");

            out.println("</div>");
            out.println("</nav>");

            String name=req.getParameter("name");
            int ep=Integer.parseInt(req.getParameter("ep"));
            String path="http://localhost:8080/animesEps/"+name+"/";

            out.println("<div class='container'>");
                    out.println("<br>");
                    out.println("<video width='90%' height='60% border-radius: 22px;' controls='controls' class='round-borders'>");
                        out.println("<source src='"+path+name+ep+".mp4' type='video/mp4'/>");
                        out.println("<source src='"+path+name+ep+".webm' type='video/webm'/>");
                        out.println("<track label='English' kind='subtitles' srclang='en' src='"+path+"subtitles/"+name+ep+".vtt' default>");
                    out.println("</video>");
            out.println("</div>");

            out.println("<div class='row margin'>");

                out.print("<div class='col-md-3' align='center' style='height:60px;'>");
                    out.print("<a class='btn btn-primary' href='playVideo?name="+name+"&ep="+(ep-1)+"' role='button' style='width:100%'>Previous Episode</a>");
                out.print("</div>");

                out.print("<div class='col-md-6' align='center' style='height:60px;width:100%'>");
                    out.print("<a class='btn btn-primary' href='animList' role='button' style='width:100%'>List of episodes</a>");
                out.print("</div>");

                out.print("<div class='col-md-3' align='center' style='height:60px'>");
                    out.print("<a class='btn btn-primary' href='playVideo?name="+name+"&ep="+(ep+1)+"' role='button' style='width:100%'>Next episode</a>");
                out.print("</div>");

            out.print("</div>");

        out.println("</div>");

    out.println("</body></html>");
}
}
