<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
	
		<meta charset="UTF-8">
		<title>データベース内容表示</title>
		<jsp:include page="/WEB-INF/jsp/header.jsp" />
		
	</head>
	
	<body>
		
		<h2>データベース内容表示</h2>
		
		<form action="index.jsp" method="get">
			<input type="submit" value="メニューへ戻る">
		</form>
		
		<h3>manu_goods テーブル</h3>
		
		<table>
			<tr>
				<c:forEach var="col" items="${manuGoodsCols}">
					<th>${col}</th>
				</c:forEach>
			</tr>
			<c:forEach var="row" items="${manuGoodsList}">
				<tr>
					<c:forEach var="col" items="${manuGoodsCols}">
						<td>${row[col]}</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</table>
		
		<h3>parts テーブル</h3>
		
		<table>
			<tr>
				<c:forEach var="col" items="${partsCols}">
					<th>${col}</th>
				</c:forEach>
			</tr>
			<c:forEach var="row" items="${partsList}">
				<tr>
					<c:forEach var="col" items="${partsCols}">
						<td>${row[col]}</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</table>
		
		<form action="index.jsp" method="get">
			<input type="submit" value="メニューへ戻る">
		</form>
		
		<h3>da_report テーブル</h3>
		
		<table>
			<tr>
				<c:forEach var="col" items="${daReportCols}">
					<th>${col}</th>
				</c:forEach>
			</tr>
			<c:forEach var="row" items="${daReportList}">
				<tr>
					<c:forEach var="col" items="${daReportCols}">
						<td>${row[col]}</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</table>
		
		<br>
		
		<form action="index.jsp" method="get">
			<input type="submit" value="メニューへ戻る">
		</form>
		
		<br>
		<br>
		
	</body>
</html>
