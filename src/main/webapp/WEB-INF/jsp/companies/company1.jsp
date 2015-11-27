<?xml version="1.0" encoding="UTF-8"?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/WEB-INF/jsp/urls.jspf" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="companyPath" value="/companies/${company.id}" />
<c:url var="companyUrl" value="/${companyPath}" />
<c:url var="editCompanyUrl" value="${companyPath}/edit.html" />
<c:set var="clientsPath" value="${companyPath}/clients.html" />
<c:set var="usersPath" value="${companyPath}/users.html" />
<c:set var="currenciesPath" value="${companyPath}/currencies.html" />
<spring:message var="companyEdit" code="company.edit" />
<html>
	<head>
		<title><c:out value="${company.code}: ${company.name}" /></title>
		<link rel="stylesheet" type="text/css" href="${forumsCssUrl}" />
		<link rel="stylesheet" href="/skin/jquery-ui.css" />
		<link href="http://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" async="" src="http://www.google-analytics.com/ga.js"></script>
 		<script src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.8.4/moment.min.js"></script>
		<script src="/skin/scripts/table-edits.min.js"></script>
		<script>
		 $(function() {
		      $( "#tabs" ).tabs();
		 });
		</script>
		<script type="text/javascript">
	      var idCount = 1;
	      $(function() {
	    	  $('#addButton').click(function(){
	              var rowItem = $('<tr data-id='+(idCount++)+' style="cursor: pointer;">')
	                .addClass('rowItem')
	                .appendTo('#tbody_remn')
	              var colItem1 = $('<td class="currency" data-field="currency">').appendTo(rowItem);
	              var colItem2 = $('<td data-field="sum">').appendTo(rowItem);
	              var colItem3 = $('<td>').appendTo(rowItem);
	              var aCl = $('<a title='+'Edit'+'>').addClass('button button-small edit').appendTo(colItem3);
	              var iCl = $('<i>').addClass('fa fa-pencil').appendTo(aCl);
	            });
	            $('#addButton').click();
	      });
		</script>
		<script>
		$(function(){
	        $('table tr').each(function() { $('table tr').live({click: function(){ 
	        	var currency = [];
				$("select.firm_currencies option").each(function() { 
					var txt=$(this).text(); 
					if( $("table tr > td.currency:contains("+txt+")").length<1 )
					{currency.push($(this).text()); }
					});
	        	$('table tr').editable({
		        dropdowns: {
		          currency: currency
		        },
		        edit: function(values) {
		          $(".edit i", this)
		            .removeClass('fa-pencil')
		            .addClass('fa-save')
		            .attr('title', 'Save');
		        },
		        save: function(values) {
		          $(".edit i", this)
		            .removeClass('fa-save')
		            .addClass('fa-pencil')
		            .attr('title', 'Edit');
		        },
		        cancel: function(values) {
		          $(".edit i", this)
		            .removeClass('fa-save')
		            .addClass('fa-pencil')
		            .attr('title', 'Edit');
		        }
		      });
		      }});});
		    });
		</script>
 	</head>
	<body>
		<div id="hidden">
		<select class="firm_currencies">
		<c:forEach items="${currenciesList}" var="curs">
			<option value="${curs.alpha}">${curs.alpha}</option>
		</c:forEach>
		</select>
		</div>
		<ul id="breadcrumbs">
			<li><a href="${homeUrl}"><spring:message code="home.pageTitle" /></a></li>
			<li><a href="${companiesUrl}"><spring:message code="company.pageTitle" /></a></li>
		</ul>
		<h1>${company.code}: ${company.name} - ${company.city}</h1>
		<p><a href="${clientsPath}"><span class="cln icon" >(${countClients})</span></a>
        <a href="${currenciesPath}"><span class="currency icon" >(${countCurrencies})</span></a>
        <a href="${usersPath}"><span class="user icon">(${countUsers})</span></a></p>
		<c:if test="${param.saved == true}">
			<div class="info alert"><spring:message code="company.save" /></div>
		</c:if>
		<div id="tabs">
            <ul>
              <li><a href="#remn" >Остатки</a></li>
              <li><a href="#cash" >Касса</a></li>
              <li><a href="#deals" >Сделки</a></li>
            </ul>
             <div id="remn" >
         		<table class="u-full-width demo">
          			<thead>
            			<tr>
           					<th><spring:message code="company.remn.currency" /></th>
           					<th><spring:message code="company.remn.sum" /></th>
           					<th><spring:message code="company.edit" /></th>
           				</tr>
          			</thead>
          			<tbody id="tbody_remn">
          			</tbody>
        		</table>
			<div class="buttonBar">
            	<button type="button" id="addButton"><spring:message code="company.add" /></button>
            	<!-- button type="submit" id="applyFilterButton"><spring:message code="company.save" /></button-->
          </div>
			</div>
            <div id="cash">
                <p>CashBook</p>
            </div>
            <div id="deals">
                <p>Deals</p>
            </div>
       </div>
	</body>
	
</html>