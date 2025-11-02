<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		
		<meta charset="UTF-8">
		<title>在庫・内示表示</title>
		<jsp:include page="/WEB-INF/jsp/header.jsp" />
		
	</head>
	
	<body>
		
		<h1>部品在庫・当月内示残・次月内示</h1>
		
		<h2>製品在庫</h2>
				
		<table>
			<tr>
				<th>機種</th>
				<th>在庫数量</th>
				<th>当月内示残</th>
				<th>次月内示</th>
			</tr>
			<c:forEach var="g" items="${goodsList}">
				<tr>
					<td>${g.m_goods}</td>
					<td>${g.mg_stock}</td>
					<td>${g.c_remain}</td>
					<td>${g.n_month_val}</td>
				</tr>
			</c:forEach>
		</table>
		
		<form action="inputCnMonth" method="get">
			<input type="submit" value="内示登録・修正">
		</form>		
		
		<h2>部品在庫</h2>
		<table>
			<tr>
				<th>部品種類</th>
				<th>在庫数量</th>
			</tr>
			<c:forEach var="p" items="${partsList}">
				<tr>
					<td>${p.name}</td>
					<td>${p.stock}</td>
				</tr>
			</c:forEach>
		</table>
		
		<h2>テープ在庫</h2>
		<table>
			<tr>
				<th>テープ種類</th>
				<th>在庫数量</th>
			</tr>
			<c:forEach var="t" items="${tapeList}">
				<tr>
					<td>${t.name}</td>
					<td>${t.stock}</td>
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
