<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
	<head>
	
	<meta charset="UTF-8">
	<title>日報入力確認</title>
	<jsp:include page="/WEB-INF/jsp/header.jsp" />
	
	
	</head>
	
	<body>
		
		<h2>日報入力確認</h2>
		<p>日付：${reportDate}</p>
		
		
		<div>
			<h3>生産数・不良数</h3>
			<p>製品A生産数：${prod_A}</p>
			<p>製品B生産数：${prod_B}</p>
			<p>製品C生産数：${prod_C}</p>
			<p>製品D生産数：${prod_D}</p>
			<p>製品E生産数：${prod_E}</p>
			<p>製品F生産数：${prod_F}</p>
			         
			<h4>不良発生数</h4>
			<ul>
				<c:forEach var="d" items="${defectList}" varStatus="status">
					<li>${defectPartList[status.index]} 数量: ${d}</li>
				</c:forEach>
			</ul>
		</div>
		
		<div>
			<h3>出荷数</h3>
			<p>製品A出荷数：${ship_A}</p>
			<p>製品B出荷数：${ship_B}</p>
			<p>製品C出荷数：${ship_C}</p>
			<p>製品D出荷数：${ship_D}</p>
			<p>製品E出荷数：${ship_E}</p>
			<p>製品F出荷数：${ship_F}</p>
		</div>
		
		<br>
		
		<!-- ===== 登録ボタン + hidden送信データ ===== -->
		<form action="registerDaily" method="post">
			<!-- 日付 -->
			<input type="hidden" name="reportDate" value="${reportDate}">
			
			<!-- 生産数 -->
			<input type="hidden" name="prod_A" value="${prod_A}">
			<input type="hidden" name="prod_B" value="${prod_B}">
			<input type="hidden" name="prod_C" value="${prod_C}">
			<input type="hidden" name="prod_D" value="${prod_D}">
			<input type="hidden" name="prod_E" value="${prod_E}">
			<input type="hidden" name="prod_F" value="${prod_F}">
			
			<!-- 出荷数 -->
			<input type="hidden" name="ship_A" value="${ship_A}">
			<input type="hidden" name="ship_B" value="${ship_B}">
			<input type="hidden" name="ship_C" value="${ship_C}">
			<input type="hidden" name="ship_D" value="${ship_D}">
			<input type="hidden" name="ship_E" value="${ship_E}">
			<input type="hidden" name="ship_F" value="${ship_F}">
			
			<!-- 不良発生データ（複数） -->
			<c:forEach var="d" items="${defectList}" varStatus="status">
				<input type="hidden" name="defect_part_${status.index + 1}" value="${defectPartList[status.index]}">
				<input type="hidden" name="defect_volume_${status.index + 1}" value="${d}">
			</c:forEach>
			
			<input type="submit" value="登録">
		</form>
		
		<br>
		
		<form action="inputDaily" method="post">
		<input type="hidden" name="reportDate" value="${reportDate}">
		
		<!-- 生産数 -->
		<input type="hidden" name="prod_A" value="${prod_A}">
		<input type="hidden" name="prod_B" value="${prod_B}">
		<input type="hidden" name="prod_C" value="${prod_C}">
		<input type="hidden" name="prod_D" value="${prod_D}">
		<input type="hidden" name="prod_E" value="${prod_E}">
		<input type="hidden" name="prod_F" value="${prod_F}">
		
		<!-- 出荷数 -->
		<input type="hidden" name="ship_A" value="${ship_A}">
		<input type="hidden" name="ship_B" value="${ship_B}">
		<input type="hidden" name="ship_C" value="${ship_C}">
		<input type="hidden" name="ship_D" value="${ship_D}">
		<input type="hidden" name="ship_E" value="${ship_E}">
		<input type="hidden" name="ship_F" value="${ship_F}">
		
		<!-- 不良品（最大4件） -->
		<c:forEach var="d" items="${defectList}" varStatus="status">
		<input type="hidden" name="defect_part_${status.index + 1}" value="${defectPartList[status.index]}">
		<input type="hidden" name="defect_volume_${status.index + 1}" value="${d}">
		</c:forEach>
		
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
