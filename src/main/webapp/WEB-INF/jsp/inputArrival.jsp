<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		
		<meta charset="UTF-8">
		<title>部品入庫数入力</title>
		<jsp:include page="/WEB-INF/jsp/header.jsp" />
		
	</head>
	
	<body>
		
		<h1>部品入庫数入力</h1>
		
		<form action="checkArrival" method="post">
			
			<div>
				<label for="arrivalDate">入庫日：</label>
				<input type="date" id="arrivalDate" name="arrivalDate" required value="${arrivalDate}">
			</div>
			
			<div class="section">
				<h3>部品</h3>
				<p><label>部品01：</label><input type="number" name="arrival_part_1" value="${arrivalMap['部品01']}" min="0"></p>
				<p><label>部品02：</label><input type="number" name="arrival_part_2" value="${arrivalMap['部品02']}" min="0"></p>
				<p><label>部品03：</label><input type="number" name="arrival_part_3" value="${arrivalMap['部品03']}" min="0"></p>
				<p><label>部品04：</label><input type="number" name="arrival_part_4" value="${arrivalMap['部品04']}" min="0"></p>
				<p><label>部品05：</label><input type="number" name="arrival_part_5" value="${arrivalMap['部品05']}" min="0"></p>
			</div>
			
			<div class="section">
				<h3>テープ</h3>
				<p><label>テープ01：</label><input type="number" name="arrival_tape_1" value="${arrivalMap['テープ01']}" min="0"></p>
				<p><label>テープ02：</label><input type="number" name="arrival_tape_2" value="${arrivalMap['テープ02']}" min="0"></p>
				<p><label>テープ03：</label><input type="number" name="arrival_tape_3" value="${arrivalMap['テープ03']}" min="0"></p>
				<p><label>テープ04：</label><input type="number" name="arrival_tape_4" value="${arrivalMap['テープ04']}" min="0"></p>
			</div>
			
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