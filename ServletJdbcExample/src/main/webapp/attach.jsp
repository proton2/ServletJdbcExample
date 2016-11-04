<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<title>Add new user</title>
</head>

<body>
<br>
	<form method="POST" action="AttachController" name="frmAddAttach" enctype="multipart/form-data">
	<table border=0>
	<tr>
		    <input type="hidden" readonly="readonly" name="id" value="<c:out value="${attach.id}" />" />
	</tr>
	<tr>
	        <td>Worktask id : </td>
    		<td><input type="text" readonly="readonly" name="worktask_id" value="<c:out value="${wt_id}" />" /></td>
    </tr>
	<tr>
		<td>Caption : </td>
		<td>
		    <input type = "text" name = "caption" value = "<c:out value="${attach.caption}" />" /><br>
		</td>
	</tr>
	<tr>
		<td>File name : </td>
		<td>
		    <input type = "text" readonly="readonly" name = "fName" value = "<c:out value="${attach.fileName}" />" /><br>
		</td>
	</tr>
	<tr>
	    <td>Select File to Upload:</td>
	    <td><input type="file" name="attachFile"></td>
	</tr>
	<tr>
	    <td><a href = "WorkTaskController?action=edit&id=<c:out value = "${wt_id}"/>">Return to worktask</a></td>
	    <td><input type = "submit" value = "Save" name = "button"/></td>
	</tr>
	</table>
	</form>
	<br>

</body>

</html>