<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Show All Users</title>
</head>

<body>
<jsp:include page="master.jsp"></jsp:include>
<br>
	<table border=1>
		<thead>
			<tr>
				<th>User id </th>
				<th>First name </th>
				<th>Last name </th>
				<th>caption </th>
				<th colspan=2>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${users}" var = "user">
				<tr>
					<td><c:out value="${user.id}"/></td>
					<td><c:out value="${user.firstName}"/></td>
					<td><c:out value="${user.lastName}"/></td>
					<td><c:out value="${user.caption}"/></td>
					<td><a href = "WorkTaskController?action=select_user&id=<c:out value = "${user.id}"/>">Select</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<button type="button" name="back" onclick="history.back()">back</button>
	<p><a href = "UserController?action=">Add user</a></p>
</body>
</html>