<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/WEB-INF/jsp/urls.jspf" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<spring:message var="registerUser" code="newUserRegistration.label.register" />

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><spring:message code="newUserRegistration.pageTitle" /></title>
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
			<li><a href="${usersUrl}"><spring:message code="userList.pageTitle" /></a></li>
		</ul>
		
		<h1><spring:message code="newUserRegistration.pageTitle" /></h1>
		<form:form cssClass="main" modelAttribute="account" metod = "${method}">
			<p> <spring:message code="newUserRegistration.message.allFieldsRequired" /></p>
			
			<div class="panel grid">
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="newUserRegistration.label.username" /></div>
					<div class="yui-u">
						<div><form:input path="username" cssClass="medium" cssErrorClass="medium error" /></div>
						<div class="errorMessage"><form:errors path="username" htmlEscape="false" /></div>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="newUserRegistration.label.password" /></div>
					<div class="yui-u">
						<div><form:password path="password" showPassword="false" cssClass="medium" cssErrorClass="medium error" /></div>
						<div class="errorMessage"><form:errors path="password" htmlEscape="false" /></div>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="newUserRegistration.label.confirmPassword" /></div>
					<div class="yui-u">
						<div><form:password path="confirmPassword" showPassword="false" cssClass="medium" /></div>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="newUserRegistration.label.firstName" /></div>
					<div class="yui-u">
						<div><form:input path="firstName" cssClass="medium" cssErrorClass="medium error" /></div>
						<div class="errorMessage"><form:errors path="firstName" htmlEscape="false" /></div>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="newUserRegistration.label.lastName" /></div>
					<div class="yui-u">
						<div><form:input path="lastName" cssClass="medium" cssErrorClass="medium error" /></div>
						<div class="errorMessage"><form:errors path="lastName" htmlEscape="false" /></div>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="newUserRegistration.label.email" /></div>
					<div class="yui-u">
						<div><form:input path="email" cssClass="medium" cssErrorClass="medium error" /></div>
						<div class="errorMessage"><form:errors path="email" htmlEscape="false" /></div>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="newUserRegistration.label.phone" /></div>
					<div class="yui-u">
						<div><form:input path="phone" cssClass="medium" cssErrorClass="medium error" /></div>
							<div class="errorMessage"><form:errors path="phone" htmlEscape="false" /></div>
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
					<div class="fieldLabel yui-u first"><spring:message code="newUserRegistration.label.roles" /></div>
					<div class="yui-u">
						<div>
						<form:select multiple="true" path="role" cssClass="medium" cssErrorClass="medium error" >
							<c:forEach items="${roleList}" var="roles">
			 				<form:option value="${roles.code}">${roles.code}</form:option>
							</c:forEach>
			 			</form:select>
						</div>
						<div class="errorMessage"><form:errors path="role" htmlEscape="false" /></div>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="yui-u first"></div>
					<div class="yui-u">
						<input type="submit" value="${registerUser}" />
					</div>
				</div>
			</div>
		</form:form>
	</body>
</html>
