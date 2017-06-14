<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <form name="loginForm" method="POST" action="WorkTaskController">
    <input type = "hidden" name="action" value="login"/>
        Login:<br/>
        <input type="text" name="login" value=""/>
        <br/>Password:<br/>
        <input type="password" name="password" value=""/>
        <br/>
        <br/>
        <input type="submit" value="Log in"/>
    </form><hr/>
    Press <font color=red>root/root</font> for enter.
    <br>
    <br>Small Web application - work tasks and deadlines. Like a little Jira.<br><br><br>
    I create this training project for learning the basics of Java EE - servlet engine and JDBC (no more frameworks).<br>
    The purpose of the project - to understand how the servlets and JDBC working.<br>
    <br>
    - Only Java Servlets API and Java JDBC without any frameworks (to study how Servlets and jdbc working). Using jsp;<br>
    - Dao factory template to easy working with many dao;<br>
    - Use caching (use ehcache provider);<br>
    - Separation reading light objects for list froms and reading usual entities. Differents DAO;<br>
    - Load SQL querries for dao from external xml file (to avoid changes in the source code if necessary change SQL querry);<br>
    - authorization by password and different access rights depending on the user role;<br>
    - pagination (portion objects loading per each page);<br>
    - import list of main objects from excel format to database;<br>
    - jQuerry elements in interface (use tabs);<br>
    - mechanism for loading and storage attach files to tasks;<br>
    - Comments for tasks;<br>
    - Logging into separatly log files;<br>
    <br>
</body>
</html>