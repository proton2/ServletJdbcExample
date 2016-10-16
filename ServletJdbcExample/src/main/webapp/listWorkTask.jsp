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
    				<th>Id </th>
    				<th>User </th>
    				<th>Caption </th>
    				<th>Task Date </th>
    				<th>Dead Line </th>
    				<th>Task status </th>
    				<th colspan=2>Action</th>
    			</tr>
    		</thead>
    		<tbody>
    			<c:forEach items="${workTasks}" var = "workTask">
    				<tr>
    					<td><c:out value="${workTask.id}"/></td>
    					<td><c:out value="${workTask.taskUser}"/></td>
    					<td><c:out value="${workTask.caption}"/></td>
    					<td><fmt:formatDate pattern="yyyy-MMM-dd" value="${workTask.taskDate}" /></td>
    					<td><fmt:formatDate pattern="yyyy-MMM-dd" value="${workTask.deadLine}" /></td>
    					<td><c:out value="${workTask.taskStatus}"/></td>
    					<td><a href = "WorkTaskController?action=edit&id=<c:out value = "${workTask.id}"/>">Update</a></td>
    					<td><a href = "WorkTaskController?action=delete&id=<c:out value = "${workTask.id}"/>">Delete</a></td>
    				</tr>
    			</c:forEach>
    		</tbody>
    	</table>

    <p><a href = "WorkTaskController?action=">Add workTask</a></p>
</body>

</html>