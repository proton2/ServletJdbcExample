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
<jsp:include page="master.jsp"></jsp:include>
<br>
	<form method="POST" action="WorkTaskController" name="frmAddUser">
	<input type = "hidden" name="action" value="save_user"/>
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

    <c:choose>
    <c:when test="${sessionScope.role == 'admin'}">

	<tr>
    	<td>login : </td>
    	<td>
            <input type = "text" name = "login" value = "<c:out value="${user.login}" />" /><br>
    	</td>
    </tr>

    <tr>
    	<td>password : </td>
    	<td>
            <input type = "password" name = "password" value = "*********" /><br>
    	</td>
    </tr>

    <tr>
        <td>User role</td>
        <td>
            <select name="role">
                <option value = 0 ${user.role == 'admin' ? 'selected' : ''}> admin </option>
                <option value = 1 ${user.role == 'boss' ? 'selected' : ''}> boss </option>
                <option value = 2 ${user.role == 'user' ? 'selected' : ''}> user </option>
            </select>
        </td>
    </tr>

	<tr>
		<td></td>
		<td>
	        <input type = "submit" value = "Save user" name = "button"/>
		</td>
	</tr>

	</c:when>
	<c:otherwise> <tr><td>access denited!</td></tr> </c:otherwise>
	</c:choose>

	</table>
	</form>

	<br>
    User tasks:
    <table border=1>
    		<thead>
    			<tr>
    				<th>Id </th>
    				<th>Caption </th>
    				<th>Task Date </th>
    				<th>Dead Line </th>
    				<th colspan=2>Action</th>
    			</tr>
    		</thead>
    		<tbody>
    			<c:forEach items="${userTasks}" var = "workTask">
    				<tr>
    					<td><c:out value="${workTask.id}"/></td>
    					<td><c:out value="${workTask.caption}"/></td>
    					<td><fmt:formatDate pattern="yyyy-MMM-dd" value="${workTask.taskDate}" /></td>
    					<td><fmt:formatDate pattern="yyyy-MMM-dd" value="${workTask.deadLine}" /></td>
    					<td><a href = "WorkTaskController?action=edit_worktask&id=<c:out value = "${workTask.id}"/>">Update</a></td>
    					<td><a href = "WorkTaskController?action=delete_worktask&id=<c:out value = "${workTask.id}"/>">Delete</a></td>
    				</tr>
    			</c:forEach>
    		</tbody>
    	</table>

	<button type="button" name="back" onclick="history.back()">back</button>
</body>
</html>