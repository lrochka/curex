<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><spring:message code="accessdenied.title" /></title>
	</head>
	<body>
		<h1><spring:message code="accessdenied.title" /></h1>
		<p><spring:message code="accessdenied.sorry" /></p>
		<p>${error}</p>
	</body>
</html>
