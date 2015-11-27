<?xml version="1.0" encoding="UTF-8"?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/WEB-INF/jsp/urls.jspf" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<spring:message var="newCurrency" code="currencies.newCurrency" />

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><spring:message code="currencies.pageTitle" /></title>
		<link rel="stylesheet" type="text/css" href="${forumsCssUrl}" />
		<script type="text/javascript">
			$(function() { $("#currenciesList").tablesorter({ sortList: [ [0, 0] ], textExtraction: "complex" }); });
		</script>
		<script type="text/javascript">
		$(function() {
			var options = {
				valueNames: [ 'alpha','numeric','country_rus', 'currency_rus']
				};
			var userList = new List("list", options);
		});
		</script>
	</head>
	<body>
		<ul id="breadcrumbs">
			<li><a href="${homeUrl}"><spring:message code="home.pageTitle" /></a></li>
		</ul>
		
		<h1><spring:message code="currencies.pageTitle" /></h1>
		<c:choose>
			<c:when test="${empty currenciesList}">
				<p><spring:message code="currencies.empty" /></p>
			</c:when>
			<c:otherwise>
			<div id="list" >
			<input class="search" placeholder="Поиск" style="display: block;"/>
				<table id="currenciesList" class="sortable" style="font-size: 80%; text-overflow: ellipsis;">
					<thead style="display: block; width=100%">
						<tr style="text-overflow: ellipsis;">
							<th class="string" ><spring:message code="currencies.img" /></th>
							<th class="string" ><spring:message code="currencies.numeric" /></th>
							<th class="string" ><spring:message code="currencies.alpha" /></th>
							<th class="string" ><spring:message
									code="currencies.country_rus" /></th>
							<th class="string" ><spring:message
									code="currencies.currency_rus" /></th>
						</tr>
					</thead>
					<tbody class="list" style="height: 200px; overflow-y: auto; overflow-x: auto; display: block; width=100%; text-overflow: ellipsis;">
						<c:forEach var="currency" items="${currenciesList}">
							<c:url var="currencyUrl"
								value="/currencies/${currency.numeric}.html" />
							<%-- Use timeStyle="short" so jquery.tablesorter can parse column as date --%>
							<%-- >fmt:formatDate var="date" type="both" timeStyle="short" value="${u.dateCreated}" --%>
							<tr style="padding: 0px; ">
								<td style="padding: 0px; padding-left: 10px;"><img src="/currencies/image.html?alpha=${currency.alpha}" /></td>
								<td class="numeric" style="padding: 0px;  padding-right: 10px; "><a
									href="${currencyUrl}">${currency.numeric}</a></td>
								<td class="alpha" style="padding: 0px;  padding-left: 10px; "><c:out value="${currency.alpha}" /></td>
								<td class="country_rus" style="padding: 0px;  padding-left: 10px; "><c:out
										value="${currency.country_rus}" /></td>
								<td class="currency_rus" style="padding: 0px;  padding-left: 10px; "><c:out
										value="${currency.currency_rus}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			</c:otherwise>
		</c:choose>
		<li class="clnPlus icon"><a href="" title="${newCurrency}"><spring:message code="currencies.newCurrency" /></a></li>
	</body>
</html>
