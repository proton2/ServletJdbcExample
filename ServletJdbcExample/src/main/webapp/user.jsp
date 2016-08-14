<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<link type="text/css" href="css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
	<title>Add new user</title>
</head>

<body>
	<form method="POST" action="UserController" name="frmAddUser">
	<table border=0>
	<tr>
		    <input type="hidden" readonly="readonly" name="id" value="<c:out value="${user.id}" />" />
	</tr>
	<tr>
		<td>First name : </td>
		<td>
		    <input type = "text" name = "firstname" value = "<c:out value="${user.firstName}" />" /><br>
		</td>
	</tr>
	<tr>
		<td>Last name : </td>
		<td>
		    <input type = "text" name = "lastname" value = "<c:out value="${user.lastName}" />" /><br>
		</td>
	</tr>
	<tr>
		<td>Caption : </td>
		<td>
		    <input type = "text" name = "caption" value = "<c:out value="${user.caption}" />" /><br>
		</td>
	</tr>
	<tr>
		<td>Email : </td>
		<td>
		    <input type = "text" name = "email" value = "<c:out value="${user.email}" />" /><br>
		</td>
	</tr>
	<tr>
    	    <td>TextArea</td>
    	    <td>
    	        <textarea name="textarea1" rows="5" cols="27"></textarea>
    	    </td>
    </tr>
	<tr>
		<td></td>
		<td>
		    <input type = "submit" value = "Submit" />
		</td>
	</tr>
	</table>
	</form>

	<p><a href = "UserController?action=list">Return to list users</a></p>
	<button type="button" name="back" onclick="history.back()">back</button>
</body>
</html>