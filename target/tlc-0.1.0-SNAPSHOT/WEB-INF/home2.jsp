<%@page import="istic.project.model.Criteria"%>
<%@page import="istic.project.model.Advertisement"%>
<%@page import="istic.project.services.impl.AdvertisementService"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<html>
<head>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
</head>
<body>
	<%
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
	%>
	<%
		AdvertisementService advService = AdvertisementService.getInstance();
	%>
	<%
		ObjectMapper mapper = new ObjectMapper();
	%>
	<%
		String searchByTitle = null;
	%>
	<%
		Long searchByPriceMin = null;
	%>
	<%
		Long searchByPriceMax = null;
	%>
	<%
		Date searchByDateMin = null;
	%>
	<%
		Date searchByDateMax = null;
	%>


	<%
		searchByTitle = request.getParameter("searchByTitle");
		pageContext.setAttribute("searchByTitle", searchByTitle);
	%>
	<%
		String param = request.getParameter("searchByPriceMin");
		if (param != null && !param.isEmpty()) {
			searchByPriceMin = Long.decode(param);
			pageContext.setAttribute("searchByPriceMin", searchByPriceMin);
		}
	%>
	<%
		param = request.getParameter("searchByPriceMax");
		if (param != null && !param.isEmpty()) {
			searchByPriceMax = Long.decode(param);
			pageContext.setAttribute("searchByPriceMax", searchByPriceMax);
		}
	%>
	<%
		param = request.getParameter("searchByDateMin");
		if (param != null && !param.isEmpty()) {
			searchByDateMin = dateFormater.parse(param);
			pageContext.setAttribute("searchByDateMin", searchByDateMin);
		}
	%>
	<%
		param = request.getParameter("searchByDateMax");
		if (param != null && !param.isEmpty()) {
			searchByDateMax = dateFormater.parse(param);
			pageContext.setAttribute("searchByDateMax", searchByDateMax);
		}
	%>


	<%
	Criteria criteria = new Criteria(new Double(searchByPriceMin), new Double(searchByPriceMax), searchByTitle,searchByDateMin, searchByDateMax);
		List<Advertisement> advertisements = advService.getByCriteria(criteria);
		pageContext.setAttribute("json_advs", mapper.writeValueAsString(advertisements));
	%>

	<!-- Content de la page -->
	<div>Advertisement list</div>
	<div>
		<%
			if (!advertisements.isEmpty()) {
		%>
		<ul>
			<%
				for (Advertisement adv : advertisements) {
			%>
			<%
				pageContext.setAttribute("adv_resume", adv.toString());
			%>
			<li>${fn:escapeXml(adv_resume)}</li>
			<%
				}
			%>
		</ul>
		<%
			}
		%>
	</div>

	<!-- Action sur la page -->
	<form action="/home.jsp" method="get">
		<div>
			<div>
				<label>recherche par label :</label>
			</div>
			<div>
				<input type="text" name="searchByTitle"
					value="${fn:escapeXml(searchByTitle)}" />
			</div>

		</div>
		<div>
			<div>
				<label>recherche par prix minimum</label>
			</div>
			<div>
				<input type="number" name="searchByPriceMin"
					value="${fn:escapeXml(searchByPriceMin)}" />
			</div>

		</div>
		<div>
			<div>
				<label>recherche par prix maximum</label>
			</div>
			<div>
				<input type="number" name="searchByPriceMax"
					value="${fn:escapeXml(searchByPriceMax)}" />
			</div>
		</div>

		<div>
			<div>
				<label>recherche par date minimum (YYYY-MM-DD)</label>
			</div>
			<div>
				<input type="date" name="searchByDateMin"
					value="${fn:escapeXml(searchByDateMin)}" />
			</div>

		</div>
		<div>
			<div>
				<label>recherche par date maximum (YYYY-MM-DD)</label>
			</div>
			<div>
				<input type="date" name="searchByDateMax"
					value="${fn:escapeXml(searchByDateMax)}" />
			</div>

		</div>
		<div>
			<input type="submit" value="Search Advertisement" />
		</div>
	</form>

	<form action="/api/advertisement" method="post">
		<div>

			<div>
				<label>Data in JSON : [{ "title":"data", "price":00}]</label>
			</div>
			<div>
				<textarea name="content" cols=40 rows=6></textarea>
			</div>

		</div>
		<div>
			<input type="submit" value="Add" />
		</div>
	</form>

	<form action="/api/advertisement" method="get">
		<div>
			<input type="hidden" name="content"
				value="${fn:escapeXml(json_advs)}" />
		</div>
		<div>
			<input type="submit" value="delete" />
		</div>
</form>
</body>
