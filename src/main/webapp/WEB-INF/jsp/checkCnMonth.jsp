<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		
		<meta charset="UTF-8">
		<title>内示登録内容確認</title>
		<jsp:include page="/WEB-INF/jsp/header.jsp" />
		
	</head>
	
	<body>
		
		<h1>内示登録内容確認</h1>
		
		<form action="registerCnMonth" method="post">
			<table>	
				<tr>
					<th>機種</th>
					<th>当月内示</th>
					<th>次月内示</th>
				</tr>
				
				<c:forEach var="entry" items="${monthData}">
					<tr>
						<td>${entry.key}</td>
						<td>${empty entry.value.c_month ? '-' : entry.value.c_month}</td>
						<td>${empty entry.value.n_month ? '-' : entry.value.n_month}</td>
					</tr>
				</c:forEach>
			</table>
			
			<br>
			
			<input type="submit" value="登録">
		</form>
		
		<br>
		
		<form action="backToInputCnMonth" method="post">
			<input type="submit" value="内示登録・修正画面へ戻る">
		</form>
		
		<br>
		
				<br>
		
		<form action="index.jsp" method="get">
			<input type="submit" value="メニューへ戻る">
		</form>
		
		<br>
		<br>
		
	</body>
</html>
