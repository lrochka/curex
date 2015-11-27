<?xml version="1.0" encoding="UTF-8"?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/WEB-INF/jsp/urls.jspf" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<c:url var="clientUrl" value="/clients/${client.phone}.html" />

<spring:message var="saveClient" code="editClient.label.save" />

<html>
	<head>
		<title><spring:message code="editClient.pageTitle" /></title>
		<link rel="stylesheet" type="text/css" href="${forumsCssUrl}" />
		<script  type="text/javascript">
		$(function(){
			$('form').submit(function() {
				$(':submit',this).attr('disabled', 'disabled');
			});
		})
		</script>
	</head>
	<body>
		<ul id="breadcrumbs">
			<li><a href="${homeUrl}"><spring:message code="home.pageTitle" /></a></li>
			<li><a href="${clientsUrl}"><spring:message code="clients.pageTitle" /></a></li>
			<li><a href="${clientUrl}">${client.phone}</a></li>
		</ul>
		
		<h1 class="commentEdit"><spring:message code="editClient.pageTitle" />: ${client.phone} </h1>
		
		<c:if test="${param.saved == true}">
			<div class="info alert"><spring:message code="editClient.client.save" />! <a href="${clientUrl}"><spring:message code="editClient.client.view" /></a></div>
		</c:if>

	<form:form cssClass="main" modelAttribute="client" >
		<input type="hidden" name="_method" value="POST" />

		<div class="panel grid">
			<div class="gridRow yui-gf">
				<div class="fieldLabel yui-u first">
					<spring:message code="editClient.label.name" />
				</div>
				<div class="yui-u">
					<div>
						<form:input path="name" cssClass="medium"
							cssErrorClass="medium error" />
					</div>
					<div class="errorMessage">
						<form:errors path="name" htmlEscape="false" />
					</div>
				</div>
			</div>
			<div class="gridRow yui-gf">
				<div class="fieldLabel yui-u first">
					<spring:message code="company.label.name" />
					:
				</div>
				<div class="yui-u">
					<div>
						<div>
							<form:select path="company" cssClass="medium"
								cssErrorClass="medium error">
								<c:forEach items="${companyList}" var="company">
									<form:option value="${company.id}">${company.code} ${company.name}</form:option>
								</c:forEach>
							</form:select>
						</div>
						<div class="errorMessage">
							<form:errors path="company" htmlEscape="false" />
						</div>
					</div>
				</div>
			</div>
			<div class="gridRow yui-gf">
				<div class="fieldLabel yui-u first">
					<spring:message code="editClient.addInfo" />
				</div>
				<div class="yui-u">
					<div>
						<form:textarea id="textArea" path="addContact"
							cssClass="resizable" cssErrorClass="resizable error" />
					</div>
					<div class="errorMessage">
						<form:errors path="addContact" htmlEscape="false" />
					</div>
				</div>
			</div>
			<div class="gridRow yui-gf">
				<div class="fieldLabel yui-u first">
					<spring:message code="editClient.label.date" />
				</div>
				<div class="yui-u">
					<span class="date icon"><fmt:formatDate type="both"
							value="${originalClient.dateCreated}" /></span>
				</div>
			</div>
			<div class="gridRow yui-gf">
				<div class="yui-u first"></div>
				<div class="yui-u">
					<input type="submit" value="${saveClient}" />
				</div>
			</div>
		</div>
	</form:form>
</body>
</html>
