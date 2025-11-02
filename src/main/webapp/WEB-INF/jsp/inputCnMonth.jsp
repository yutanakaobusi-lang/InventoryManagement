<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		
		<meta charset="UTF-8">
		<title>内示登録・修正</title>
		<jsp:include page="/WEB-INF/jsp/header.jsp" />
		
	</head>
	
	<body>
		
		<h1>内示登録・修正</h1>
		
		<form action="checkCnMonth" method="post">
			<table>
				<tr>
					<th>機種</th>
					<th>当月内示</th>
					<th>次月内示</th>
				</tr>
				<c:choose>
					<c:when test="${not empty monthData}">
						<c:forEach var="entry" items="${monthData}">
							<tr>
								<td>${entry.key}</td>
								<td><input type="number" name="c_month_${entry.key}" 
										value="${entry.value.c_month}" required></td>
								<td><input type="number" name="n_month_${entry.key}" 
										value="${entry.value.n_month != null ? entry.value.n_month : ''}"></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:forEach var="item" items="${goodsList}">
							<tr>
								<td>${item.m_goods}</td>
								<td><input type="number" name="c_month_${item.m_goods}" 
										value="${item.c_month}" required></td>
								<td><input type="number" name="n_month_${item.m_goods}" 
										value="${item.n_month == null ? '' : item.n_month}"></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</table>
			
			<br>
			<input type="submit" value="確認画面へ">
		</form>
		    
		    
		<br>
		<form action="index.jsp" method="get">
			<input type="submit" value="メニューへ戻る">
		</form>
		<br>
		<br>
		
	</body>
</html>