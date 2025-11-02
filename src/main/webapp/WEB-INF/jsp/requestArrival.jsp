<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    // セッションから日付を取得
    String[] dateValues = (String[]) session.getAttribute("dateValues");
%>

<!DOCTYPE html>
<html>
	<head>
		
		<meta charset="UTF-8">
		<title>部品・テープ入庫依頼票</title>
		<jsp:include page="/WEB-INF/jsp/header.jsp" />
		
	</head>
	
	<body>
		
		<h1>部品・テープ入庫依頼票</h1>
		
		<form action="printRequestArrival" method="post">
			
			
			<table>
				<tr>
					<th>部品名</th>
				
					<th><input type="date" name="date1" 
							value="<%= (dateValues != null && dateValues.length > 0) ? dateValues[0] : "" %>" 
							required></th>
					<th><input type="date" name="date2" 
							value="<%= (dateValues != null && dateValues.length > 1) ? dateValues[1] : "" %>" 
							required></th>
					<th><input type="date" name="date3" 
							value="<%= (dateValues != null && dateValues.length > 2) ? dateValues[2] : "" %>" 
							required></th>
					<th><input type="date" name="date4" 
							value="<%= (dateValues != null && dateValues.length > 3) ? dateValues[3] : "" %>" 
							required></th>
				
				</tr>
				
				
				
				<c:forEach var="entry" items="${requestMap}">
					<c:set var="name" value="${entry.key}" />
					<c:set var="quantity" value="${quantityMap[entry.key]}" />
					<tr>
						<td>
							<c:choose>
								<c:when test="${fn:startsWith(name, '部品')}">
									${name}（${quantity}個/ケース）
								</c:when>
								<c:otherwise>
									${name}（${quantity}個/束）
								</c:otherwise>
							</c:choose>
						</td>
						<c:forEach var="val" items="${entry.value}">
							<td>
								<c:choose>
									<c:when test="${fn:startsWith(name, '部品')}">
										${val}ケース
									</c:when>
									<c:otherwise>
										${val}束
									</c:otherwise>
								</c:choose>
							</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</table>
			
			<br>
			
			<div style="text-align:center;">
				<input type="submit" value="印刷ページの表示">
			</div>
		</form>
		
		<form action="index.jsp" method="get">
			<input type="submit" value="メニューへ戻る">
		</form>
		
		<br>
		<br>
		
	</body>
</html>
