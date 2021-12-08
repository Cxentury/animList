<!DOCTYPE html>
<html>

<head>
    <%@ page pageEncoding="UTF-8" %>
    <%@ page import="java.sql.*" %>
    <%@ page import="User" %>

    <title> List </title>
    <link href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css' rel='stylesheet'>
    <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css'/>
    <link rel='stylesheet' href='custom.css'/>
    <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>
    <script src='script.js'></script>
</head>

    <body class='' id='bd' onload='restore()'>
    
    <% 
        Connection con=null;
        ResultSet result;

        try {
            Class.forName("org.postgresql.Driver");
            
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/animeList","user","moi");
            String query="";
            HttpSession sessionUser=request.getSession(true);
            PreparedStatement req;

            if(request.getParameter("id")==null){
                query="select * from animes inner join list on list.animeid=animes.animeid and userid=(select id from users where login like ? )";
                req=con.prepareStatement(query);
                User user=(User) sessionUser.getAttribute("user");
                req.setString(1, user.getLogin());

            }
            else{
                query="select * from animes inner join list on list.animeid=animes.animeid and userid=?";
                req=con.prepareStatement(query);
                req.setInt(1, Integer.parseInt(req.getParameter("id"));
            }

            result=req.executeQuery();
    %>
	    
        <div class='container-fluid'>
            <nav class='navbar navbar-expand-lg navbar-dark bg-primary'>
                <div class='container-fluid'>
                    <a class='navbar-brand' href='animList'>AnimeList</a>
                    <button class='navbar-toggler' type='button' data-bs-toggle='collapse' data-bs-target='#navbarSupportedContent' aria-controls='navbarSupportedContent' aria-expanded='false' aria-label='Toggle navigation'>
                        <span class='navbar-toggler-icon'></span>
                    </button>
                    <div class='collapse navbar-collapse' id='navbarSupportedContent'>
                        <ul class='navbar-nav me-auto mb-2 mb-lg-0'>
                        <li class='nav-item'>
                        <a class='nav-link active' aria-current='page' href='#'>Home</a></li>
                        <li class='nav-item'>
                        <a class='nav-link' href='#'>Link</a></li>
                        <form class='d-flex' method='POST' action='Search'>
                            <input class='form-control me-2' type='search' placeholder='Anime/User' aria-label='Search' name='search'>
                            <button class='btn btn-dark' type='submit'>Search</button>
                        </form>
                    </div>
                    <button class='btn' id='btn' onClick='darkMode()'><i class='fa fa-moon-o'></i></button>

                </div>
            </nav>

            <div class='container rounded'>
                <div class='row justify-content-center animate__animated animate__fadeInDown margin border border-primary rounded'>
                    <div class='col' align='center' id='borderImageCol'>
                        <p>Image<p>
                    </div>
                    <div class='col' align='center' id='borderNameCol' >
                        <p>Name<p>
                    </div>

                    <div class='col ' align='center' id='borderLink'>
                        <p>Link to episodes<p>
                    </div>

                    <div class='w-100'></div>
                    <br>

                <% while(result.next()){ %>

                        <div class='col' align='center' id='borderImage'>
                            <img class='image' src=<%=result.getString("imagelink")%> style='max-width:100px;max-height:100px;image-rendering: -webkit-optimize-contrast;' class='rounded'>
                        </div>

                        <div class='col' align='center' id='borderName'>
                            <h5 style='padding-top:10%'><%=result.getString("name")%></h5>
                        </div>

                        <div class='col' align='center'id='borderWatch'>
                            <a class='btn btn-success' href='playVideo?name="+<%=result.getString("name")%>+"&ep=1' role='button'>Watch</a>
                        </div>

                        <div class='w-100'></div>

                        <br>

                <%}%>

                </div>
            </div>

        </div>

        <%
        }catch (Exception e) {%>
            <div class='alert alert-warning' role='alert'>Internal Error</div>
        <%}
        finally
            {
            try{con.close();} catch (Exception e){}
        }%>
            
    </body></html>
