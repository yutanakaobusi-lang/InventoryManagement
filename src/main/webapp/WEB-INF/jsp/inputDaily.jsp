<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDate" %>
<!DOCTYPE html>
<html>
	<head>
		
		<meta charset="UTF-8">
		<title>日報入力</title>
		<jsp:include page="/WEB-INF/jsp/header.jsp" />
		
	</head>
	
	<body>
		
		<h1>日報入力</h1>
		
		<form action="checkDaily" method="post">
			
			<div>
				<label for="reportDate">日付：</label>
				<input type="date" id="reportDate" name="reportDate" required value="${reportDate}">
			</div>
			
			
			<div>
				<h3>生産数・不良発生数</h3>
				<label>製品A生産数<input type="number" name="prod_A" min="0" value="${prod_A}"></label><br><br>
				<label>製品B生産数<input type="number" name="prod_B" min="0" value="${prod_B}"></label><br><br>
				<label>製品C生産数<input type="number" name="prod_C" min="0" value="${prod_C}"></label><br><br>
				<label>製品D生産数<input type="number" name="prod_D" min="0" value="${prod_D}"></label><br><br>
				<label>製品E生産数<input type="number" name="prod_E" min="0" value="${prod_E}"></label><br><br>
				<label>製品F生産数<input type="number" name="prod_F" min="0" value="${prod_F}"></label><br><br>
				
				<h4>不良発生数</h4>
				
				<p>
					部品を選択：
					<select name="defect_part_1">
					<option value="">選択してください</option>
					<option value="部品01" ${defect_part_1 == '部品01' ? 'selected' : ''}>部品01</option>
					<option value="部品02" ${defect_part_1 == '部品02' ? 'selected' : ''}>部品02</option>
					<option value="部品03" ${defect_part_1 == '部品03' ? 'selected' : ''}>部品03</option>
					<option value="部品04" ${defect_part_1 == '部品04' ? 'selected' : ''}>部品04</option>
					<option value="部品05" ${defect_part_1 == '部品05' ? 'selected' : ''}>部品05</option>
					<option value="テープ01" ${defect_part_1 == 'テープ01' ? 'selected' : ''}>テープ01</option>
					<option value="テープ02" ${defect_part_1 == 'テープ02' ? 'selected' : ''}>テープ02</option>
					<option value="テープ03" ${defect_part_1 == 'テープ03' ? 'selected' : ''}>テープ03</option>
					<option value="テープ04" ${defect_part_1 == 'テープ04' ? 'selected' : ''}>テープ04</option>
					</select>
					不良数：<input type="number" name="defect_volume_1" min="0" value="${defect_volume_1}">
				</p>
				
				<p>
					部品を選択：
					<select name="defect_part_2">
					<option value="">選択してください</option>
					<option value="部品01" ${defect_part_2 == '部品01' ? 'selected' : ''}>部品01</option>
					<option value="部品02" ${defect_part_2 == '部品02' ? 'selected' : ''}>部品02</option>
					<option value="部品03" ${defect_part_2 == '部品03' ? 'selected' : ''}>部品03</option>
					<option value="部品04" ${defect_part_2 == '部品04' ? 'selected' : ''}>部品04</option>
					<option value="部品05" ${defect_part_2 == '部品05' ? 'selected' : ''}>部品05</option>
					<option value="テープ01" ${defect_part_2 == 'テープ01' ? 'selected' : ''}>テープ01</option>
					<option value="テープ02" ${defect_part_2 == 'テープ02' ? 'selected' : ''}>テープ02</option>
					<option value="テープ03" ${defect_part_2 == 'テープ03' ? 'selected' : ''}>テープ03</option>
					<option value="テープ04" ${defect_part_2 == 'テープ04' ? 'selected' : ''}>テープ04</option>
					</select>
					不良数：<input type="number" name="defect_volume_2" min="0" value="${defect_volume_2}">
				</p>
				
				<p>
					部品を選択：
					<select name="defect_part_3">
					<option value="">選択してください</option>
					<option value="部品01" ${defect_part_3 == '部品01' ? 'selected' : ''}>部品01</option>
					<option value="部品02" ${defect_part_3 == '部品02' ? 'selected' : ''}>部品02</option>
					<option value="部品03" ${defect_part_3 == '部品03' ? 'selected' : ''}>部品03</option>
					<option value="部品04" ${defect_part_3 == '部品04' ? 'selected' : ''}>部品04</option>
					<option value="部品05" ${defect_part_3 == '部品05' ? 'selected' : ''}>部品05</option>
					<option value="テープ01" ${defect_part_3 == 'テープ01' ? 'selected' : ''}>テープ01</option>
					<option value="テープ02" ${defect_part_3 == 'テープ02' ? 'selected' : ''}>テープ02</option>
					<option value="テープ03" ${defect_part_3 == 'テープ03' ? 'selected' : ''}>テープ03</option>
					<option value="テープ04" ${defect_part_3 == 'テープ04' ? 'selected' : ''}>テープ04</option>
					</select>
					不良数：<input type="number" name="defect_volume_3" min="0" value="${defect_volume_3}">
				</p>
				
				<p>
					部品を選択：
					<select name="defect_part_4">
					<option value="">選択してください</option>
					<option value="部品01" ${defect_part_4 == '部品01' ? 'selected' : ''}>部品01</option>
					<option value="部品02" ${defect_part_4 == '部品02' ? 'selected' : ''}>部品02</option>
					<option value="部品03" ${defect_part_4 == '部品03' ? 'selected' : ''}>部品03</option>
					<option value="部品04" ${defect_part_4 == '部品04' ? 'selected' : ''}>部品04</option>
					<option value="部品05" ${defect_part_4 == '部品05' ? 'selected' : ''}>部品05</option>
					<option value="テープ01" ${defect_part_4 == 'テープ01' ? 'selected' : ''}>テープ01</option>
					<option value="テープ02" ${defect_part_4 == 'テープ02' ? 'selected' : ''}>テープ02</option>
					<option value="テープ03" ${defect_part_4 == 'テープ03' ? 'selected' : ''}>テープ03</option>
					<option value="テープ04" ${defect_part_4 == 'テープ04' ? 'selected' : ''}>テープ04</option>
					</select>
					不良数：<input type="number" name="defect_volume_4" min="0" value="${defect_volume_4}">
				</p>
			</div>
			
			<div>
				<h3>出荷数</h3>
				<label>製品A出荷数<input type="number" name="ship_A" min="0" value="${ship_A}"></label><br><br>
				<label>製品B出荷数<input type="number" name="ship_B" min="0" value="${ship_B}"></label><br><br>
				<label>製品C出荷数<input type="number" name="ship_C" min="0" value="${ship_C}"></label><br><br>
				<label>製品D出荷数<input type="number" name="ship_D" min="0" value="${ship_D}"></label><br><br>
				<label>製品E出荷数<input type="number" name="ship_E" min="0" value="${ship_E}"></label><br><br>
				<label>製品F出荷数<input type="number" name="ship_F" min="0" value="${ship_F}"></label><br><br>
			</div>
			
			<br>
			
			<div>
				<input type="submit" value="確認画面へ">
			</div>
		</form>
		
		
		<br>
		
		<form action="index.jsp" method="get">
			<input type="submit" value="メニューへ戻る">
		</form>
		
		<br>
		<br>
		
	</body>
</html>