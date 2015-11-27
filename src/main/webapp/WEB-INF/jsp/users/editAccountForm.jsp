<?xml version="1.0" encoding="UTF-8"?>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/WEB-INF/jsp/urls.jspf" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<c:url var="userUrl" value="/users/${account.username}.html" />

<spring:message var="saveLabel" code="editUser.label.save" />

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><spring:message code="editUser.pageTitle" /></title>
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
			<li><a href="${usersUrl}"><spring:message code="userList.pageTitle" /></a></li>
			<li><a href="${userUrl}">${account.username}</a></li>
		</ul>
		
		<h1 class="commentEdit"><spring:message code="editUser.pageTitle" />: ${account.username} </h1>
		
		<c:if test="${param.saved == true}">
			<div class="info alert"><spring:message code="editUser.user.save" /><a href="${userUrl}"><spring:message code="editUser.user.view" /></a></div>
		</c:if>
		
		<form:form cssClass="main" modelAttribute="account" >
			<input type="hidden" name="_method" value="POST" />
			
			<div class="panel grid">
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="editUser.label.name" /></div>
					<div class="yui-u">
						<div><form:input path="firstName" cssClass="medium" cssErrorClass="medium error" /></div>
						<div class="errorMessage"><form:errors path="firstName" htmlEscape="false" /></div>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="editUser.label.lastName" /></div>
					<div class="yui-u">
						<div><form:input path="lastName" cssClass="medium" cssErrorClass="medium error" /></div>
						<div class="errorMessage"><form:errors path="lastName" htmlEscape="false" /></div>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="editUser.label.password" /></div>
					<div class="yui-u">
						<div><form:password path="password" showPassword="false" cssClass="medium" cssErrorClass="medium error" /></div>
						<div class="errorMessage"><form:errors path="password" htmlEscape="false" /></div>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="editUser.label.confirmPassword" /></div>
					<div class="yui-u">
						<div><form:password path="confirmPassword" showPassword="false" cssClass="medium" /></div>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="editUser.label.email" /></div>
					<div class="yui-u">
						<div><form:input path="email" cssClass="medium" cssErrorClass="medium error" /></div>
						<div class="errorMessage"><form:errors path="email" htmlEscape="false" /></div>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="editUser.label.phone" /></div>
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
					<div class="fieldLabel yui-u first"><spring:message code="editUser.label.roles" /></div>
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
					<div class="fieldLabel yui-u first"><spring:message code="editUser.label.date" /></div>
					<div class="yui-u">
						<span class="date icon"><fmt:formatDate type="both" value="${originalAccount.dateCreated}" /></span>
					</div>
				</div>
				<div class="gridRow yui-gf">
					<div class="yui-u first"></div>
					<div class="yui-u">
						<input type="submit" value="${saveLabel}" />
					</div>
				</div>
			</div>
		</form:form>
	</body>
</html>
