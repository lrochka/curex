<!DOCTYPE HTML>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/urls.jspf" %>
<html>
	<head>
		<title><decorator:title /> - <spring:message code="app.pageTitle" /></title>
		<link rel="shortcut icon" href="/skin/icons/favicon.ico" />
		<link rel="stylesheet" type="text/css" href="${baseGoogleUrl}/yui/2.9.0/build/reset-fonts-grids/reset-fonts-grids.css" />
		<link rel="stylesheet" type="text/css" href="${baseGoogleUrl}/yui/2.9.0/build/base/base.css" />
		<link rel="stylesheet" type="text/css" href="${baseGoogleUrl}/jqueryui/1.8.14/themes/vader/jquery-ui.css" />
		<link rel="stylesheet" type="text/css" href="${faceCssUrl}" />
		<link rel="stylesheet" type="text/css" href="${currexCssUrl}" />
		<link rel="stylesheet" type="text/css" href="${listCssUrl}" />
		<script type="text/javascript" src="${baseGoogleUrl}/jquery/1.8.3/jquery.min.js"></script>
		<script type="text/javascript" src="${baseGoogleUrl}/jqueryui/1.9.2/jquery-ui.min.js"></script>
		<script type="text/javascript" src="${sipJsUrl}"></script>
		<script type="text/javascript" src="${listJsUrl}"></script>
		<decorator:head />
	</head>
	<body>
		<div id="doc3">
			<div id="outerHdSubhd">
				<div id="innerHdSubhd">
					<div id="hd">
						<div id="hdAppName"><spring:message code="app.pageTitle" /></div>
					</div>
					<%@ include file="/WEB-INF/jsp/skin/subhead.jspf" %>
				</div>
			</div>
			<div id="mainContainer" class="hasLeftCol">
				<div id = "leftCol">
				<div class="homeSideNav">
					<ul>
						<security:authorize url="${servletPath}/${clientsUrl}" method="GET">
							<li ><a href="${clientsUrl}"><span class="cln icon"><spring:message code="clients.pageTitle" /></a></li>
						</security:authorize>
						<security:authorize url="${servletPath}/${usersUrl}" method="GET">
							<li ><a href="${usersUrl}"><span class="user icon"><spring:message code="userList.pageTitle" /></a></li>
						</security:authorize>
						<security:authorize url="${servletPath}/${companiesUrl}" method="GET">
							<li ><a href="${companiesUrl}"><span class="building icon"><spring:message code="company.pageTitle" /></a></li>
						</security:authorize>
						<security:authorize url="${servletPath}/${dealUrl}" method="GET">
							<li ><a href="${dealUrl}"><span class="handShake icon"><spring:message code="deals.pageTitle" /></a></li>
						</security:authorize>
						<security:authorize url="${servletPath}/${docUrl}" method="GET">
							<li ><a href="${docUrl}"><span class="document icon"><spring:message code="documents.pageTitle" /></a></li>
						</security:authorize>
						<security:authorize url="${servletPath}/${cashUrl}" method="GET">
							<li ><a href="${cashUrl}"><span class="moneyCoin icon"><spring:message code="cash.pageTitle" /></a></li>
						</security:authorize>
						<security:authorize url="${servletPath}/${remainsUrl}" method="GET">
							<li ><a href="${remainsUrl}"><span class="moneyBag icon"><spring:message code="remains.pageTitle" /></a></li>
						</security:authorize>
						<security:authorize url="${servletPath}/${usersUrl}" method="GET">
							<li ><a href="${homeUrl}"><span class="moneyPlus icon"><spring:message code="crossDeals.pageTitle" /></a></li>
						</security:authorize>
					</ul>
				</div>
				</div>
				<div id ="contentCol">
					<div id = "system-main" class="regionInner">
						<decorator:body />
					</div>
				</div>
			</div>
			<div id="ft">
				<div class="regionInner">
					<div id="legal">
						Copyright &copy; 2015
					</div>
				</div>
			</div>
		</div>
	
	</body>
</html>
