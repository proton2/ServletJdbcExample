<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<link type="text/css"
        href="css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
    <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.8.18.custom.min.js"></script>

	<title>Add new user</title>
</head>

<body>

    <script>
        $(function() {
            $('input[name=taskDate]').datepicker();
        });
    </script>

    <form method="POST" action="WorkTaskController" name="frmAddWokrTask">
    	<table border=0>
    	<tr>
    	    <input type="hidden" readonly="readonly" name="id" value="<c:out value="${workTask.id}" />" />
    	</tr>

    	<tr>
    	    <td>User id: </td>
    	    <td><input type="text" readonly="readonly" name="taskuser_id" value="<c:out value="${taskuser.id}" />"/></td>
        </tr>
    	<tr>
    		<td><a href = "WorkTaskController?action=select_user_list">Task user </a></td>
    		<td>
    		    <a href = "UserController?action=edit&id=<c:out value = "${taskuser.id}"/>">${taskuser}</a>
    		</td>
    	</tr>

    	<tr>
    		<td>Caption: </td>
    		<td>
    		    <input type = "text" name = "caption" value = "<c:out value="${workTask.caption}" />" /><br>
    		</td>
    	</tr>
    	<tr>
    		<td>Task date: </td>
    		<td>
    		    <input type="text" name="taskDate"
    		        value="<fmt:formatDate pattern="dd/MM/yyyy" value="${workTask.taskDate}"/>" />
    		</td>
    	</tr>
    	<tr>
            <td>Dead line: </td>
            <td>
                <input type="text" name="deadLine"
                    		        value="<fmt:formatDate pattern="dd/MM/yyyy" value="${workTask.deadLine}"/>" />
            </td>
        </tr>
    	<tr>
    	    <td>Task context</td>
    	    <td>
    	        <textarea name="textarea1" rows="5" cols="27"><c:out value="${workTask.taskContext}"/> </textarea>
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

    <p><a href = "WorkTaskController?action=list">Return to list tasks</a></p>
</body>
</html>