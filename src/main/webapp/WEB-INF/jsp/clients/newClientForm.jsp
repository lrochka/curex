<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/WEB-INF/jsp/urls.jspf" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<spring:message var="saveClient" code="newClientRegistration.label.register" />

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><spring:message code="newClientRegistration.pageTitle" /></title>
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
		</ul>
		
		<h1><spring:message code="newClientRegistration.pageTitle" /></h1>
		
		<form:form cssClass="main" modelAttribute="client" metod = "${method}">
			<p><spring:message code="newClientRegistration.message.allFieldsRequired" /></p>
			
			<div class="panel grid">
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="newClientRegistration.label.phone" /></div>
					<div class="yui-u">
						<div><form:input path="phone" cssClass="medium" cssErrorClass="medium error" /></div>
						<div class="errorMessage"><form:errors path="phone" htmlEscape="false" /></div>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="newClientRegistration.label.name" /></div>
					<div class="yui-u">
						<div><form:input path="name" cssClass="medium" cssErrorClass="medium error" /></div>
						<div class="errorMessage"><form:errors path="name" htmlEscape="false" /></div>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="company.label.name" />:</div>
					<div class="yui-u">
						<div>
							<div>
							<form:select path="company" cssClass="medium" cssErrorClass="medium error">
								<c:forEach items="${companyList}" var="company">
								<form:option value="${company.id}">${company.code} ${company.name}</form:option>
								</c:forEach>
							</form:select> 
							</div>
							<div class="errorMessage"><form:errors path="company" htmlEscape="false" /></div>
						</div>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="newClientRegistration.label.addInfo" /></div>
					<div class="yui-u">
						<div>
						<form:textarea id="textArea" path="addContact" cssClass="resizable" cssErrorClass="resizable error" />
						</div>
						<div class="errorMessage"><form:errors path="addContact" htmlEscape="false" /></div>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="yui-u first"></div>
					<div class="yui-u"><input type="submit" value="${saveClient}" /></div>
				</div>
			</div>
		</form:form>
	</body>
</html>
