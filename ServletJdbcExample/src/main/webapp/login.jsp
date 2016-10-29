<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <form name="loginForm" method="POST" action="LoginServlet">
        Login:<br/>
        <input type="text" name="login" value=""/>
        <br/>Password:<br/>
        <input type="password" name="password" value=""/>
        <br/>
        <br/>
        <input type="submit" value="Log in"/>
    </form><hr/>
</body>
</html>