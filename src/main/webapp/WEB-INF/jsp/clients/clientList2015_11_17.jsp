<?xml version="1.0" encoding="UTF-8"?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/WEB-INF/jsp/urls.jspf" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<spring:message var="newClient" code="clients.newClient" />

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><spring:message code="clients.pageTitle" /></title>
		<link rel="stylesheet" type="text/css" href="${forumsCssUrl}" />
		<script type="text/javascript">
			$(function() { $("#clientList").tablesorter({ sortList: [ [0, 0] ], textExtraction: "complex" }); });
		</script>
		<script type="text/javascript">
		$(function() {
			var options = {
				valueNames: [ 'phone','name','company','stats']
				};
			var userList = new List("list", options);
		});
		</script>
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
		
		<h1><spring:message code="clients.pageTitle" /></h1>
		
		<c:choose>
			<c:when test="${empty clientList}">
				<p><spring:message code="clients.empty" /></p>
			</c:when>
			<c:otherwise>
			<div id="list">
			<input class="search" placeholder="Поиск" />
				<table id="clientList" class="sortable">
					<thead>
						<tr>
							<th class="string"><spring:message code="clients.client" /></th>
							<th class="string"><spring:message code="clients.name" /></th>
							<th class="string"><spring:message code="company.label.name" /></th>
							<th class="string"><spring:message code="clients.stats" /></th>
						</tr>
					</thead>
					<tbody class="list">
						<c:forEach var="client" items="${clientList}">
							<c:url var="userUrl" value="/clients/${client.phone}.html" />
							<%-- Use timeStyle="short" so jquery.tablesorter can parse column as date --%>
							<fmt:formatDate var="date" type="both" timeStyle="short" value="${u.dateCreated}" />
							<tr id="${client.clnStats.code}">
								<td class="phone"><span class="clnArrow icon" style="white-space:nowrap"><a href="${userUrl}">${client.phone}</a></td>
								<td class="name" > <c:out value="${client.name}" /></td>
								<td class="company"><c:out value="${client.company.code} ${client.company.name}" /></td>
								<td class="stats" > <c:out value="${client.clnStats.name}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			</c:otherwise>
		</c:choose>
		<li class="clnPlus icon"><a href="${newClientUrl}" title="${newClient}"><spring:message code="clients.newClient" /></a></li>
	</body>
</html>
