<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ page import="java.sql.Connection"%>
<%@ page import="javax.naming.Context"%>
<%@ page import="javax.naming.InitialContext"%>
<%@ page import="javax.sql.DataSource"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<c:url var="postLoginUrl" value="/j_spring_security_check" />

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><spring:message code="login.please.comein" /></title>
	</head>
	<body>
	<%
		InitialContext initialContext = new InitialContext();
    	Context context = (Context) initialContext.lookup("java:comp/env");
		DataSource ds = (DataSource) context.lookup("jdbc/currencyexchange");
    	
		try{
		Connection conn = ds.getConnection();
		}catch(Exception ex)
     	{
		
   		response.sendRedirect("/error.html?failedconnection=true");
   	 	}
	%>
		<h1><spring:message code="login.please.comein" /></h1>
		
		<form class="main" action="${postLoginUrl}" method="post" >
			<c:if test="${param.failed == true}">
				<div class="warning alert"><spring:message code="login.warning.alert" /></div>
			</c:if>
			<div class="panel grid" style="width:420px">
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="login.login" /></div>
					<div class="yui-u"><input type="text" name="j_username" class="short" /></div>
				</div>
				<div class="gridRow yui-gf">
					<div class="fieldLabel yui-u first"><spring:message code="login.password" /></div>
					<div class="yui-u"><input type="password" name="j_password" class="short" /></div>
				</div>
				<div class="gridRow yui-gf">
					<div class="yui-u first"></div>
					<div class="yui-u"><input type="checkbox" name="_spring_security_remember_me" /><spring:message code="login.remember.me" /></div>
				</div>
				<div class="gridRow yui-gf">
					<div class="yui-u first"></div>
					<div class="yui-u"><input type="submit" value="<spring:message code="login.comein" />" /></div>
				</div>
			</div>
		
	</form>
	</body>

</html>
