<?xml version="1.0" encoding="UTF-8"?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/WEB-INF/jsp/urls.jspf" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<spring:message var="newCurrency" code="currencies.newCurrency" />
<spring:message var="addLabel" code="currencies.label.addCurrency" />

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><spring:message code="currencies.pageTitle" /></title>
		<link rel="stylesheet" type="text/css" href="${forumsCssUrl}" />
		<script type="text/javascript" src="${jqueryTablesorterJsUrl}"></script>
		<script  type="text/javascript">
		$(function(){
			$('form').submit(function() {
				$(':submit',this).attr('disabled', 'disabled');
			});
		})
		</script>
		<script type="text/javascript">
			$(function() { $("#table").tablesorter({ sortList: [ [0, 0] ], textExtraction: "complex" }); });
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
			<c:choose>
			<c:when test="${!empty company}">
				<li><a href="${companiesUrl}"><spring:message code="company.pageTitle" /></a></li>
				<li><a href="/${companiesPath}/${company.id}.html">${company.code} ${company.name}</a></li>
			</c:when>
			</c:choose>
		</ul>
		
		<h1><spring:message code="currencies.pageTitle" /></h1>
		
		<c:if test="${param.saved == true}">
			<div class="info alert"><spring:message code="currencies.add" /></div>
		</c:if>
		
		<c:choose>
			<c:when test="${empty currenciesList}">
				<p><spring:message code="currencies.empty" /></p>
			</c:when>
			<c:otherwise>
			<div id="list" >
			<input class="search" placeholder="Поиск" />
				<table id="table" class="sortable" style="font-size: 80%;">
					<thead >
						<tr>
							<th class="string" ><spring:message code="currencies.img" /></th>
							<th class="string" ><spring:message code="currencies.numeric" /></th>
							<th class="string" ><spring:message code="currencies.alpha" /></th>
							<th class="string" ><spring:message
									code="currencies.country_rus" /></th>
							<th class="string" ><spring:message
									code="currencies.currency_rus" /></th>
							<th style="padding: 0px;">-</th>
						</tr>
					</thead>
					<tbody class="list">
						<c:forEach var="currency" items="${currenciesList}">
							<%-- Use timeStyle="short" so jquery.tablesorter can parse column as date --%>
							<%-- >fmt:formatDate var="date" type="both" timeStyle="short" value="${u.dateCreated}" --%>
							<tr style="padding: 0px; " id="${currency.alpha}">
								<td style="padding: 0px; padding-left: 10px;" id="${currency.alpha}"><img src="/currencies/image.html?alpha=${currency.alpha}" /></td>
								<td class="numeric" style="padding: 0px;  padding-right: 10px; ">${currency.numeric}</td>
								<td class="alpha" style="padding: 0px;  padding-left: 10px; "><c:out value="${currency.alpha}" /></td>
								<td class="country_rus" style="padding: 0px;  padding-left: 10px; "><c:out
										value="${currency.country_rus}" /></td>
								<td class="currency_rus" style="padding: 0px;  padding-left: 10px; "><c:out
										value="${currency.currency_rus}" /></td>
								<td style="padding: 0px;"><input type="checkbox"/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
			</div>
			</c:otherwise>
		</c:choose>
		
		<form:form cssClass="main" modelAttribute="currency" >
			<input type="hidden" name="_method" value="POST" />
			<div class="panel grid">
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="currencies.currency_rus" /></div>
					<div class="yui-u">
						<div>
						<form:select path="cur" cssClass="medium" cssErrorClass="medium error" >
							<c:forEach items="${currenciesListAdd}" var="curs">
			 					<form:option value="${curs.alpha}">${curs.alpha} ${curs.currency_rus}</form:option>
							</c:forEach>
			 			</form:select>
						</div>
						<div class="errorMessage"><form:errors path="cur" htmlEscape="false" /></div>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="yui-u first"></div>
					<div class="yui-u">
						<input type="submit" value="${addLabel}" />
					</div>
				</div>
			</div>
		</form:form>
		
	</body>
</html>
