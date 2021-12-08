import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/Search")
public class Search extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        ResultSet result;
        Connection con = null;
        PrintWriter out = res.getWriter();
        res.setContentType("text/html");

        /* NE PAS MODIFIER (Sauf indication) */
        out.println("<!DOCTYPE html><html lang='fr'>");
        out.println(
                "<head><meta charset='utf-8'><meta http-equiv='X-UA-Compatible' content='IE=edge'><meta name='viewport' content='width=device-width, initial-scale=1'>");
        out.println("<link href='custom.css' rel='stylesheet'>");
        out.println("<link href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css' rel='stylesheet'>");
        out.print("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>");
        out.print("<script src='script.js'></script>");
        out.println("<title>Search</title></head>");

        try {

            Class.forName("org.postgresql.Driver");

            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/animeList", "user", "moi");

            String users = "select username,userid,count(userid) as entry from list inner join users on users.id=list.userid where userid=(select id from users where lower(username) like lower(?)) group by userid,username";
            String animes = "select * from animes where lower(name) like lower(?)";

            PreparedStatement requestUser = con.prepareStatement(users);
            PreparedStatement requestAnime = con.prepareStatement(animes);

            requestUser.setString(1, "%" + req.getParameter("search") + "%");
            requestAnime.setString(1, "%" + req.getParameter("search") + "%");
            result = requestUser.executeQuery();
            ResultSet animeListing = requestAnime.executeQuery();

            out.println("<body id='bd' onload='restore()'>");
            out.print("<div class='container-fluid'>");
            out.println("<nav class='navbar navbar-expand-lg navbar-dark bg-primary'>");
            out.println("<div class='container-fluid'>");
            out.println("<a class='navbar-brand' href='animList'>AnimeList</a>");
            out.println(
                    "<button class='navbar-toggler' type='button' data-bs-toggle='collapse' data-bs-target='#navbarSupportedContent' aria-controls='navbarSupportedContent' aria-expanded='false' aria-label='Toggle navigation'>");
            out.println("<span class='navbar-toggler-icon'></span>");
            out.println("</button>");
            out.println("<div class='collapse navbar-collapse' id='navbarSupportedContent'>");
            out.println("<ul class='navbar-nav me-auto mb-2 mb-lg-0'>");
            out.println("<li class='nav-item'>");
            out.println("<a class='nav-link active' aria-current='page' href='#'>Home</a></li>");
            out.println("<li class='nav-item'>");
            out.println("<a class='nav-link' href='Profile.jsp'>Profile</a></li>");
            out.println("<form class='d-flex' method='POST' action='Search'>");
            out.println(
                    "<input class='form-control me-2' type='search' placeholder='Anime/User' aria-label='Search' name='search'>");
            out.println("<button class='btn btn-dark' type='submit'>Search</button>");
            out.println("</form>");
            out.println("</div>");
            out.println("<button class='btn' id='btn' onClick='darkMode()'><i class='fa fa-moon-o'></i></button>");


            out.println("</div>");
            out.println("</nav>");
			if(req.getParameter("status")!=null){
				if(req.getParameter("status").equals("success")){
					out.print("<div class='alert alert-success' role='alert'>Anime successfuly added to your list</div>");
				}
				else{
					out.print("<div class='alert alert-danger' role='alert'>This anime is already in your list</div>");
				}
			}

            out.print("<div class='container rounded'>");

            printUsers(out, result);

            printAnimes(out, animeListing);

            out.println("</div>");

            out.println("</div>");

        } catch (Exception e) {
            out.println("<div class='alert alert-warning' role='alert'>Internal Error</div>");
        } finally {
            try {
                con.close();
            } catch (Exception e) {
            }
        }

        out.println("</body></html>");
    }

    private void printUsers(PrintWriter out, ResultSet result) {

        try {
            if (!result.isBeforeFirst())
                return;
        } catch (SQLException e1) {
        }

        out.print("<div class='row justify-content-center animate__animated animate__fadeInDown margin border border-primary rounded'>");

        out.print("<div class='col-md-12 border border-primary' align='center'>");
        out.print("<h4>Users</h4>");
        out.print("</div>");

        out.print("<div class='col' align='center'>");
        out.print("<p>Username<p>");
        out.print("</div>");
        out.print("<div class='col' align='center'>");
        out.print("<p>Entries<p>");
        out.print("</div>");
        out.print("<div class='w-100'></div>");

        try {
            while (result.next()) {
                out.print("<div class='col' align='center'>");
                out.print("<a href='animList?id=" + result.getString("userid") + "' style='no-decoration'>"
                        + result.getString("username") + "</a>");
                out.print("</div>");

                out.print("<div class='col' align='center'>");
                out.print("<p>" + result.getInt("entry") + "</p>");
                out.print("</div>");
                out.print("<div class='w-100'></div>");
            }
        } catch (SQLException e) {
        }
        out.print("<hr/>");
        out.print("</div>");

    }

    private void printAnimes(PrintWriter out, ResultSet animeListing) {

        try {
            if (!animeListing.isBeforeFirst())
                return;
        } catch (SQLException e1) {
        }

        out.print("<div class='row justify-content-center animate__animated animate__fadeInDown margin border border-primary rounded'>");

        out.print("<div class='col-md-12 border border-primary' align='center'>");
        out.print("<h4>Animes</h4>");
        out.print("</div>");

        out.print("<div class='col' align='center'>");
        out.print("<p>Image<p>");
        out.print("</div>");
        out.print("<div class='col' align='center'>");
        out.print("<p>Name<p>");
        out.print("</div>");
        out.print("<div class='col' align='center'>");
        out.print("<p>Option<p>");
        out.print("</div>");

        out.print("<div class='w-100'></div>");

        try {
            while (animeListing.next()) {
                out.print("<div class='col' align='center'>");
                out.print("<img src='" + animeListing.getString("imagelink")
                        + "' style='max-width:100px;max-height:100px;image-rendering: -webkit-optimize-contrast;' class='rounded'>");
                out.print("</div>");

                out.print("<div class='col' align='center'>");
                out.print("<a href='#' style='no-decoration'>" + animeListing.getString("name") + "</a>");
                out.print("</div>");

                out.print("<div class='col' align='center' style='height:60px;width:100%'>");
                out.print("<form method='POST' action='addToList?id="+animeListing.getInt("animeid")+"&name="+animeListing.getString("name")+"'>");
                out.print("<button type='submit' class='btn btn-primary'>Add to list</button>");
                out.print("</form>");
                out.print("</div>");

                out.print("<div class='w-100'></div>");
            }
        } catch (SQLException e) {
        }

        out.println("</div>");
    }
}
