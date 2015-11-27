<?xml version="1.0" encoding="UTF-8"?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/WEB-INF/jsp/urls.jspf" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="companyPath" value="/companies/${company.id}" />
<c:url var="companyUrl" value="/${companyPath}" />
<c:url var="editCompanyUrl" value="${companyPath}/edit.html" />
<c:set var="clientsPath" value="${companyPath}/clients.html" />
<c:set var="usersPath" value="${companyPath}/users.html" />
<c:set var="currenciesPath" value="${companyPath}/currencies.html" />
<spring:message var="companyEdit" code="company.edit" />
<html>
	<head>
		<title><c:out value="${company.code}: ${company.name}" /></title>
		<link rel="stylesheet" type="text/css" href="${forumsCssUrl}" />
		<link href="http://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" async="" src="http://www.google-analytics.com/ga.js"></script>
 		<script src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.8.4/moment.min.js"></script>
 	</head>
	<body>
		<div id="hidden">
		<select class="firm_currencies">
		<c:forEach items="${currenciesList}" var="curs">
			<option value="${curs.alpha}">${curs.alpha}</option>
		</c:forEach>
		</select>
		</div>
		<ul id="breadcrumbs">
			<li><a href="${homeUrl}"><spring:message code="home.pageTitle" /></a></li>
			<li><a href="${companiesUrl}"><spring:message code="company.pageTitle" /></a></li>
		</ul>
		<h1>${company.code}: ${company.name} - ${company.city}</h1>
		<p><a href="${clientsPath}"><span class="cln icon" >(${countClients})</span></a>
        <a href="${currenciesPath}"><span class="currency icon" >(${countCurrencies})</span></a>
        <a href="${usersPath}"><span class="user icon">(${countUsers})</span></a></p>
		<c:if test="${param.saved == true}">
			<div class="info alert"><spring:message code="company.save" /></div>
		</c:if>
	</body>
	
</html>