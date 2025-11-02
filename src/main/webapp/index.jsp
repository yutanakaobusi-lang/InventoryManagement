<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	
	<head>
		<meta charset="UTF-8">
		<title>在庫管理システム</title>
		<jsp:include page="/WEB-INF/jsp/header.jsp" />
	</head>
	
	<body>
		<h1>在庫管理システム</h1>
		
		<h2>メニュー</h2>
		
		<ul>
			<li><a href="inputDaily">日報入力</a></li>
			<li><a href="stock">在庫・当月内示残・次月内示</a></li>
			<li><a href="inputArrival">部品入庫数入力</a></li>
			<li><a href="requestArrival">部品・テープ入庫依頼票</a></li>
		</ul>
		
		<hr>
		
		<a href="dbDisplay">データベース内容確認</a>
		
		
		<!-- 月初リセットボタン -->
		<div style="text-align:center; margin-top:40px;">
			<button id="resetButton" style="padding:10px 20px; background-color:#d9534f; 
				color:white; border:none; border-radius:5px; cursor:pointer;">
				月初リセット
			</button>
		</div>
		
		<!-- 確認ポップアップ -->
		<div id="confirmPopup" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%;
										background:rgba(0,0,0,0.5); justify-content:center; align-items:center;">
			<div style="background:white; padding:20px; border-radius:10px; width:350px; text-align:center;">
				<p>日報データを消去し、次月内示を当月内示に上書きします。<br>よろしいですか？</p>
				<button id="confirmYes" style="margin:10px; padding:5px 20px;">はい</button>
				<button id="confirmCancel" style="margin:10px; padding:5px 20px;">キャンセル</button>
			</div>
		</div>
		
		<!-- 完了ポップアップ -->
		<div id="completePopup" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%;
										background:rgba(0,0,0,0.5); justify-content:center; align-items:center;">
			<div style="background:white; padding:20px; border-radius:10px; width:300px; text-align:center;">
				<p>作業が完了しました。</p>
				<button id="closeComplete" style="margin-top:10px; padding:5px 20px;">閉じる</button>
			</div>
		</div>
		
		<script>
			
			const resetBtn = document.getElementById("resetButton");
			const confirmPopup = document.getElementById("confirmPopup");
			const completePopup = document.getElementById("completePopup");
			const confirmYes = document.getElementById("confirmYes");
			const confirmCancel = document.getElementById("confirmCancel");
			const closeComplete = document.getElementById("closeComplete");
			
			// 「月初リセット」ボタン押下時
			resetBtn.addEventListener(
				"click", () => {
					confirmPopup.style.display = "flex";
				}
			);
			
			// 「キャンセル」押下時 → ポップアップ閉じる
			confirmCancel.addEventListener(
				"click", () => {
					confirmPopup.style.display = "none";
				}
			);
			
			// 「はい」押下時 → サーバーに処理依頼
			confirmYes.addEventListener(
				"click", async () => {
					confirmPopup.style.display = "none";
					try {
						const response = await fetch("monthReset", { method: "POST" });
						if (response.ok) {
							completePopup.style.display = "flex";
						} else {
							alert("サーバーエラーが発生しました。");
						}
					} catch (error) {
						alert("通信エラーが発生しました。");
					}
				}
			);
			
			// 「閉じる」押下時 → 完了ポップアップを閉じる
			closeComplete.addEventListener(
				"click", () => {
					completePopup.style.display = "none";
				}
			);
			
		</script>
	</body>
</html>