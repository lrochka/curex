<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><spring:message code="error.title" /></title>
	</head>
	<body>
		<h1><spring:message code="error.title" /></h1>
		<p>${error}</p>
		<c:if test="${param.failedconnection == true}"><p><spring:message code="error.dbconnection" /></p></c:if>
		<p><spring:message code="error.contact.admin" /></p>
	</body>
</html>
