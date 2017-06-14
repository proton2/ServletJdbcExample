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
    				<td><a href = "WorkTaskController?action=edit_worktask&id=<c:out value = "${workTask.id}"/>">Update</a></td>
    				<td><a href = "WorkTaskController?action=delete_worktask&id=<c:out value = "${workTask.id}"/>">Delete</a></td>
    			</tr>
    		</c:forEach>
    	</tbody>
    </table>

    <jsp:include page="pagesWorkTask.jsp"></jsp:include>
    <p><a href = "WorkTaskController?action=insert_worktask">Add workTask</a></p>

    <form name="frmImportWorkNote" method="POST" action="WorkTaskController" enctype="multipart/form-data">
    <input type = "hidden" name="action" value="import"/>
    <input type="file" name="excelFile"/>
    <input type = "submit" value = "Import from excel" name = "button"/>
    </form>

</body>
</html>