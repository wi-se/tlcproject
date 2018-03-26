

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
</head>
<body>
	<!-- Content de la page -->
	<p>
		<u>Advertisement list</u>
	</p>
	<table>
		<thead>
			<tr>
				<td>title</td>
				<td>price</td>
				<td>creation date</td>
			</tr>
		</thead>
		<tbody>

			<c:forEach items="${advertisements}" var="adv">
				<tr>
					<td>${adv.title}</td>
					<td>${adv.price}</td>
					<td>${adv.createdAt}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- Action sur la page -->
	<form action="/advertisements" method="GET">
		<div>
			<div>
				<label>Search by title:</label>
			</div>
			<div>
				<input type="text" name="title" value="${fn:escapeXml(title)}" />
			</div>

		</div>
		<div>
			<div>
				<label>Search by min price</label>
			</div>
			<div>
				<input type="number" name="minPrice"
					value="${fn:escapeXml(minPrice)}" />
			</div>

		</div>
		<div>
			<div>
				<label>Search by max price</label>
			</div>
			<div>
				<input type="number" name="maxPrice"
					value="${fn:escapeXml(maxPrice)}" />
			</div>
		</div>

		<div>
			<div>
				<label>search by date (min creation date YYYY-MM-DD)</label>
			</div>
			<div>
				<input type="date" name="minDate" value="${fn:escapeXml(minDate)}" />
			</div>

		</div>
		<div>
			<div>
				<label>search by date (min creation date YYYY-MM-DD)</label>
			</div>
			<div>
				<input type="date" name="maxDate" value="${fn:escapeXml(maxDate)}" />
			</div>

		</div>
		<div>
			<input type="submit" value="Search" />
		</div>
	</form>

	<form action="/advertisements" method="POST">
		<div>

			<div>
				<label>Data in JSON : [{ "title":"data", "price":00}]</label>
			</div>
			<div>
				<textarea id="textarea" name="content" cols=40 rows=6></textarea>
			</div>

		</div>
		<div>
			<input type="submit" value="Add" />
		</div>
	</form>

	<form action="/advertisements" method="get">
		<div>
			<div>
				<textarea id="textarea" name="content" cols=40 rows=6></textarea>
			</div>
			<input type="hidden" name="delete" value="true"/>
		</div> 
		<div>
			<input type="submit" value="delete" />
		</div>
	</form>
</body>
</html>