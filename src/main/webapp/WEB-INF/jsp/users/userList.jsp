<?xml version="1.0" encoding="UTF-8"?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/WEB-INF/jsp/urls.jspf" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<spring:message var="search" code="search" />
<spring:message var="newUser" code="userList.newUser" />

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><spring:message code="userList.pageTitle" /></title>
		<link rel="stylesheet" type="text/css" href="${forumsCssUrl}" />
		<script type="text/javascript" src="${jqueryTablesorterJsUrl}"></script>
		<script type="text/javascript">
			$(function() { $("#userList").tablesorter({ sortList: [ [0, 0] ], textExtraction: "complex" }); });
		</script>
		<script type="text/javascript">
		$(function() {
			var options = {
				valueNames: [ 'username','company','firstName','lastName', 'phone']
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
		
		<h1><spring:message code="userList.pageTitle" /></h1>
		
		<c:choose>
			<c:when test="${empty userList}">
				<p><spring:message code="userList.empty" /></p>
			</c:when>
			<c:otherwise>
			<div id="list">
			<input class="search" placeholder="${search}" />
				<table id="userList" class="sortable">
					<thead>
						<tr>
							<th class="string"><spring:message code="userList.table.user" /></th>
							<th class="string"><spring:message code="company.label.name" /></th>
							<th class="string"><spring:message code="userList.table.name" /></th>
							<th class="string"><spring:message code="userList.table.lastName" /></th>
							<th class="string"><spring:message code="userList.table.phone" /></th>
						</tr>
					</thead>
					<tbody class="list">
						<c:forEach var="user" items="${userList}">
							<c:url var="userUrl" value="/users/${user.username}.html" />
							<%-- Use timeStyle="short" so jquery.tablesorter can parse column as date --%>
							<fmt:formatDate var="date" type="both" timeStyle="short" value="${u.dateCreated}" />
							<tr>
								<td class="username"><span class="user icon" style="white-space:nowrap"><a href="${userUrl}"><c:out value="${user.username}" /></a></td>
								<td class="company"><c:out value="${user.company.code} ${user.company.name}" /></td>
								<td class="firstName"><c:out value="${user.firstName}" /></td>
								<td class="lastName"><c:out value="${user.lastName}" /></td>
								<td class="phone"><c:out value="${user.phone}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			</c:otherwise>
		</c:choose>
		
		<li class="userAdd icon" path="" ><a href="${newUserUrl}" title="${newUser}"><spring:message code="userList.newUser" /></a></li>
	</body>
</html>
