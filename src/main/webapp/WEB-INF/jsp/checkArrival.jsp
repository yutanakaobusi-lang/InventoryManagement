<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		
		<meta charset="UTF-8">
		<title>入庫数確認</title>
		<jsp:include page="/WEB-INF/jsp/header.jsp" />
		
	</head>
	
	<body>
		
		<h2>入庫数確認</h2>
		
		<%
		String arrivalDate = (String) session.getAttribute("arrivalDate");
		Map<String, String> arrivalMap = (Map<String, String>) session.getAttribute("arrivalMap");
		%>
		
		<!-- 入庫日表示 -->
		<p><strong>入庫日：</strong> <%= arrivalDate %></p>
		
		<table border="1" cellpadding="3">
			<tr>
				<th>部品 / テープ</th>
				<th>数量</th>
			</tr>
			<c:forEach var="entry" items="${arrivalMap}">
				<tr>
					<td>${entry.key}</td>
					<td>${entry.value}</td>
				</tr>
			</c:forEach>
		</table>
		
		<br>
		
		<!-- 登録ボタン -->
		<form action="registerArrival" method="post">
			<input type="hidden" name="arrivalDate" value="<%= arrivalDate %>">
			<c:forEach var="entry" items="${arrivalMap}">
				<input type="hidden" name="partName" value="${entry.key}">
				<input type="hidden" name="a_volume" value="${entry.value}">
			</c:forEach>
			<input type="submit" value="登録">
		</form>
		
		<!-- 戻るボタン -->
		<form action="inputArrival" method="get">
			<input type="hidden" name="arrivalDate" value="<%= arrivalDate %>">
			
			<!-- 部品 -->
			<input type="hidden" name="arrival_part_1" value="${arrivalMap['部品01']}">
			<input type="hidden" name="arrival_part_2" value="${arrivalMap['部品02']}">
			<input type="hidden" name="arrival_part_3" value="${arrivalMap['部品03']}">
			<input type="hidden" name="arrival_part_4" value="${arrivalMap['部品04']}">
			<input type="hidden" name="arrival_part_5" value="${arrivalMap['部品05']}">
			
			<!-- テープ -->
			<input type="hidden" name="arrival_tape_1" value="${arrivalMap['テープ01']}">
			<input type="hidden" name="arrival_tape_2" value="${arrivalMap['テープ02']}">
			<input type="hidden" name="arrival_tape_3" value="${arrivalMap['テープ03']}">
			<input type="hidden" name="arrival_tape_4" value="${arrivalMap['テープ04']}">
			
			<br>
			
			<input type="submit" value="入力画面へ戻る">
		</form>
		
		<br>
		
		<form action="index.jsp" method="get">
			<input type="submit" value="メニューへ戻る">
		</form>
		
		<br>
		<br>
		
	</body>
</html>
