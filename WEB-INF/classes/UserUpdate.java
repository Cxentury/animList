import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


import javax.servlet.annotation.*;
import java.sql.*;
import userPackage.User;
//NOTE : peut être retirer le bouton watch et laisser directement cliquer sur les photos
// Aussi rajoute une banniere sur le côté avec le profil de l'utilisateur
//Peut être mettre les animés sur forme de Card

@WebServlet("/userUpdate")
public class UserUpdate extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        Connection con=null;
        try {

            Class.forName("org.postgresql.Driver");

            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/animeList", "user", "moi");
            String query = "";
            HttpSession session = req.getSession(true);
            PreparedStatement request;
            User user = (User) session.getAttribute("user");

            query = "update users set login=?, password=?, username=? where id=?";
            request = con.prepareStatement(query);
            String login=req.getParameter("login");
            String userName=req.getParameter("username");

            request.setString(1, login);
            request.setString(2, req.getParameter("password"));
            request.setString(3, userName);
            request.setInt(4, user.getId());
            request.executeUpdate();
            User newUsr=new User(login, userName, req.getParameter("password"), user.getId());
            session.setAttribute("user", newUsr);
            res.sendRedirect("Profile.jsp?status=success");

        } catch (Exception e) {
            res.sendRedirect("Profile.jsp?status=fail");
        } finally {
            try {
                con.close();
            } catch (Exception e) {}
        }

    }
}
