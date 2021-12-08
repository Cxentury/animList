<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="userPackage.User" %>

<!DOCTYPE html>
<html lang="fr">

<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Page de login</title>

	<!-- Bootstrap core CSS -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
		integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" />
	<link rel='stylesheet' href='custom.css'/>
	
</head>

<body style='background-color:#181A1B; color:white'>
	<nav class='navbar navbar-expand-lg navbar-dark bg-primary'>
		<div class='container-fluid'>
			<a class='navbar-brand' href='animList'>AnimeList</a>
			<button class='navbar-toggler' type='button' data-bs-toggle='collapse'
				data-bs-target='#navbarSupportedContent' aria-controls='navbarSupportedContent' aria-expanded='false'
				aria-label='Toggle navigation'>
				<span class='navbar-toggler-icon'></span>
			</button>
			<div class='collapse navbar-collapse' id='navbarSupportedContent'>
				<ul class='navbar-nav me-auto mb-2 mb-lg-0'>
					<li class='nav-item'>
						<a class='nav-link active' aria-current='page' href='#'>Home</a>
					</li>
					<li class='nav-item'>
						<a class='nav-link' href='Profile.jsp'>Profile</a>
					</li>
					<form class='d-flex' method='POST' action='Search'>
						<input class='form-control me-2' type='search' placeholder='Anime/User' aria-label='Search'
							name='search'>
						<button class='btn btn-dark' type='submit'>Search</button>
					</form>
			</div>
			<button class='btn' id='btn' onClick='darkMode()'><i class='fa fa-moon-o'></i></button>

		</div>
	</nav>

	<div class="container animate__animated animate__fadeInDown">

		<div class="page-header" style="margin-top: 15px;">
			<h1>Profile</h1>
		</div>

		<%    
		User user=(User) session.getAttribute("user");

		if(request.getParameter("status")!=null){
			if(request.getParameter("status").equals("success")){ %>
				<div class="alert alert-success" role="alert">
					Update successful
				</div>
			<%}
			else{%>
				<div class="alert alert-danger" role="alert">
				Update failed, please try again
				</div>
			<%}
		}%>

		<form id="loginForm" method="POST" action="userUpdate">
			<div class="form-group">
				<label for="login" class="control-label" style="margin-top: 50px;">Login</label>
				<input type="text" class="form-control" id="login" name="login" value=<%=user.getLogin()%>>
			</div>
			<div class="form-group">
				<label for="login" class="control-label">Username</label>
				<input type="text" class="form-control" id="username" name="username" value=<%=user.getUserName()%>>
			</div>
			<div class="form-group">
				<label for="password" class="control-label">Password</label>
				<input type="password" class="form-control" id="password" name="password" value="">
				<small id="passwordlENGTH" class="form-text text-muted">This is not your actual password length, for security purpose</small>
			</div>
			<button type="submit" class="btn btn-success btn-block">Update</button>
		</form>
	</div>
</body>

</html>