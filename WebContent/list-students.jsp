<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*,com.trycoding.web.model.*"%> 

<!DOCTYPE html>
<html>
<head>
	<title>Student Tracker App</title>
	
	<link type="text/css"  rel="stylesheet" href="css/style.css">
</head>

<%

	List<Student> theStudents = (List<Student>) request.getAttribute("STUDENT_LIST");

%>

<body>
	
	<div id="wrapper">
	
		<div id ="header">
				<h2>FooBar University</h2>
		</div>
	
	</div>
	<div id ="container">
	
		<div id="content">
		
			<input type="button" value="Add Student"
				onclick="window.location.href='add-student-form.jsp'; return false;"
				class="add-student-button"/>
			<table>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Action</th>
				</tr>
				
				
				
			<c:forEach var="tempStudent" items="${STUDENT_LIST}">
			
				<c:url var="tempLink" value="StudentControllerServlet">
					<c:param name="command" value="LOAD"/>
					<c:param name="studentId" value="${tempStudent.id}"></c:param>	
				</c:url>
				
				<c:url var="deleteLink" value="StudentControllerServlet">
					<c:param name="command" value="DELETE"/>
					<c:param name="studentId" value="${tempStudent.id}"></c:param>	
				</c:url>
				<tr>
					<td>${tempStudent.firstName}</td>
					<td> ${tempStudent.lastName}</td>
					<td> ${tempStudent.email}</td>
					<td><a href="${tempLink}">Update</a></td>
					<td><a href="${deleteLink}"
							onclick="if(!(confirm('are you sure delete'))) return false">Delete</a></td>
				</tr>
			</c:forEach>

<%-- 					<% for(Student temp :theStudents){%> --%>
<!-- 					<tr> -->
<%-- 						<td><%=temp.getFirstName()%></td> --%>
<%-- 						<td><%=temp.getLastName()%></td> --%>
<%-- 						<td><%=temp.getEmail()%></td> --%>
<!-- 					</tr> -->
					
<%-- 					<%} %> --%>
			
			</table>
		
		</div>
		
	
	</div>

</body>
</html>