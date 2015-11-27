<?xml version="1.0" encoding="UTF-8"?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/WEB-INF/jsp/urls.jspf" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<spring:message var="newCompany" code="company.newCompany" />

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><spring:message code="company.pageTitle" /></title>
		<link rel="stylesheet" type="text/css" href="${forumsCssUrl}" />
		<script type="text/javascript" src="${jqueryTablesorterJsUrl}"></script>
		<script type="text/javascript">
			$(function() { $("#companyList").tablesorter({ sortList: [ [0, 0] ], textExtraction: "complex" }); });
		</script>
		<script type="text/javascript">
		$(function() {
			var options = {
				valueNames: [ 'code','name','city']
				};
			var userList = new List("list", options);
		});
		</script>
	</head>
	<body>
		<ul id="breadcrumbs">
			<li><a href="${homeUrl}"><spring:message code="home.pageTitle" /></a></li>
		</ul>
		
		<h1><spring:message code="company.pageTitle" /></h1>
		
		<c:choose>
			<c:when test="${empty companyList}">
				<p><spring:message code="company.empty" /></p>
			</c:when>
			<c:otherwise>
			<div id="list">
			<input class="search" placeholder="Поиск" />
				<table id="companyList" class="sortable">
					<thead>
						<tr>
							<th class="string"><spring:message code="company.code" /></th>
							<th class="string"><spring:message code="company.name" /></th>
							<th class="string"><spring:message code="company.city" /></th>
						</tr>
					</thead>
					<tbody class="list">
						<c:forEach var="company" items="${companyList}">
							<c:url var="companyUrl" value="/companies/${company.id}.html" />
							<tr>
								<td class="code"><span class="buildingArrow icon" style="white-space:nowrap"><a href="${companyUrl}">${company.code}</a></td>
								<td class="name" contenteditable> <c:out value="${company.name}" /></td>
								<td class="city"><c:out value="${client.company.code} ${company.city}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			</c:otherwise>
		</c:choose>
		<li class="buildingPlus icon"><a href="${newCompanyUrl}" title="${newCompany}"><spring:message code="company.newCompany" /></a></li>
	</body>
</html>
