<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>EditUser</title>
</head>
<body>
	<h2>EditUser</h2>
<form action="updateuser" method="post">
	FirstName : <input type="text" name="firstName" value="${user.firstName }" /><br><Br>
	LastName : <input type="text" name="lastName" value="${user.lastName }" /><br><br>
	<input type="hidden" name="userId" value="${user.userId}"/>
		<input type="submit" value="Update"/>
</form>
</body>
</html>