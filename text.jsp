<!DOCTYPE html>
<html>

<head>
    <%@ page pageEncoding="UTF-8" %>
        <%@ page import="java.sql.*" %>

            <title> MaPage </title>
</head>

<body>
    <% 
    Connection con=null;
    try { 
        Class.forName("org.postgresql.Driver");
        con=DriverManager.getConnection("jdbc:postgresql://psqlserv/fi2?allowMultiQueries=true","victormougeletu","moi");
        Statement stmt=con.createStatement(); 
        String query="select * from users" ; 
        ResultSet rs=stmt.executeQuery(query);%>
        <table class='table table-bordered table-striped'>
            <thead>
                <tr>
                    <th>login</th>
                    <th>mdp</th>
                    <th>nom</th>
                    <th>prenom</th>
                    <th>role</th>
                    <th>adresee</th>
                </tr>
            </thead>
            <tbody>
                <% while(rs.next()){ %>
                    <tr>
                        <td>
                            <%=rs.getString("login")%>
                        </td>
                        <td>
                            <%=rs.getString("mdp")%>
                        </td>
                        <td>
                            <%=rs.getString("nom")%>
                        </td>
                        <td>
                            <%=rs.getString("prenom")%>
                        </td>
                        <td>
                            <%=rs.getString("adresse")%>
                        </td>
                    </tr>
                    <% } %>

            </tbody>
        </table>
        <%}catch(Exception e){} finally { try{con.close();} catch (Exception e){} } %>
</body>

</html>