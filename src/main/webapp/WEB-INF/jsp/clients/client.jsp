<?xml version="1.0" encoding="UTF-8"?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/WEB-INF/jsp/urls.jspf" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<c:set var="clientPath" value="/clients/${client.phone}" />
<c:url var="clientUrl" value="/${clientPath}" />

<c:url var="editClientUrl" value="${clientPath}/edit.html" />

<spring:message var="clientEdit" code="client.edit" />

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><c:out value="${client.phone}" /></title>
		<link rel="stylesheet" type="text/css" href="${forumsCssUrl}" />
	</head>
	<body>
	
		<ul id="breadcrumbs">
			<li><a href="${homeUrl}"><spring:message code="home.pageTitle" /></a></li>
			<li><a href="${clientsUrl}"><spring:message code="clients.pageTitle" /></a></li>
		</ul>
		
		<c:if test="${param.saved == true}">
			<div class="info alert"><spring:message code="client.save" /></div>
		</c:if>
		
		<h1>${client.phone}</h1>
		
		<div>
			<div class="pane grid">
				<div class="gridRow yui-gf">
					<div class="yui-u first"><spring:message code="client.name" /></div>
					<div class="yui-u">${client.name}</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="yui-u first"><spring:message code="company.label.name" />:</div>
					<div class="yui-u">${client.company.code} ${client.company.name}</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="yui-u first"><spring:message code="client.phone" /></div>
					<div class="yui-u">
						<span class="mobilePhone icon">${client.phone}</span>
					</div>
				</div>
			</div>
			<div class="pane grid">
				<div class="gridRow yui-gf">
					<div class="yui-u first"><spring:message code="client.stats" /></div>
					<div class="yui-u">${client.clnStats.name}</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="yui-u first"><spring:message code="client.src" /></div>
					<div class="yui-u">${client.clnSrc.name}</div>
				</div>
			</div>
			<div class="pane">
			<c:out value="${client.addContact}" escapeXml="false" />
			</div>
		</div>
		<li class="clnPencil icon"><a href="${editClientUrl}" title="${clientEdit}"><spring:message code="client.edit" /></a></li>
	</body>
</html>
