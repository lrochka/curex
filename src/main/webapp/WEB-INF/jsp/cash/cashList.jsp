<?xml version="1.0" encoding="UTF-8"?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="th"  uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/jsp/urls.jspf" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><spring:message code="cash.pageTitle" /></title>
		<link rel="stylesheet" type="text/css" href="${forumsCssUrl}" />
		<link rel="stylesheet" type="text/css" media="screen" href="${pqGridCssUrl}" />
    	<link rel="stylesheet" type="text/css" media="screen" href="${pqGrdCssUrl}" />
		
	</head>
	<body>
		<ul id="breadcrumbs">
			<li><a href="${homeUrl}"><spring:message code="home.pageTitle" /></a></li>
		<c:choose>
			<c:when test="${!empty company}">
				<li><a href="${companiesUrl}"><spring:message code="company.pageTitle" /></a></li>
				<li><a href="/${companiesPath}/${company.id}.html">${company.code} ${company.name}</a></li>
			</c:when>
		</c:choose>
		</ul>
		
		<h1><spring:message code="cash.pageTitle" /></h1>
		
		<div id="grid_local_sorting"  style="margin:5px auto;"></div>
		<!--  table id="grid"></table -->
		<!-- div id="pager"></div-->
		
		<script type="text/javascript" src="${cashJsUrl}"></script>
		<script type="text/javascript" src="${pgGridJsUrl}" ></script>
    	<!--Include Touch Punch file to provide support for touch devices-->
 		<script type="text/javascript" src="${touchPunchJsUrl}" ></script>
		
	</body>
</html>