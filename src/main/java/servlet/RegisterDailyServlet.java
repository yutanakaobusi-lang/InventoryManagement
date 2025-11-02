package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/registerDaily")
public class RegisterDailyServlet extends HttpServlet {
	
	private static final String JDBC_URL = System.getenv("DB_URL");
	private static final String DB_USER = System.getenv("DB_USER");
	private static final String DB_PASS = System.getenv("DB_PASSWORD");
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new ServletException("PostgreSQLドライバが見つかりません", e);
		}
		
		String reportDateStr = request.getParameter("reportDate");
		java.sql.Date reportDate = java.sql.Date.valueOf(reportDateStr);
		
		String[] goodsList = {"A", "B", "C", "D", "E", "F"};
		
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
				
			conn.setAutoCommit(false); // トランザクション開始
				
			// 製品別 生産・出荷登録
			for (String g : goodsList) {
				String goodsName = "製品" + g;
				String prodStr = request.getParameter("prod_" + g);
				String shipStr = request.getParameter("ship_" + g);
				
				boolean hasProd = prodStr != null && !prodStr.isEmpty();
				boolean hasShip = shipStr != null && !shipStr.isEmpty();
				
				// 生産または出荷がある場合のみ登録
				if (hasProd || hasShip) {
					int pVol = hasProd ? Integer.parseInt(prodStr) : 0;
					int sVol = hasShip ? Integer.parseInt(shipStr) : 0;
					
					// da_report に登録
					String sql = "INSERT INTO da_report (m_goods, p_volume, s_volume, report_date) VALUES (?, ?, ?, ?)";
						try (PreparedStatement ps = conn.prepareStatement(sql)) {
							ps.setString(1, goodsName);
							ps.setInt(2, pVol);
							ps.setInt(3, sVol);
							ps.setDate(4, reportDate);
							ps.executeUpdate();
						}
					
					// manu_goods の mg_stock 更新
					if (hasProd) {
						// mg_stock を加算
						try (PreparedStatement ps = conn.prepareStatement(
									"UPDATE manu_goods SET mg_stock = mg_stock + ? WHERE m_goods = ?")) {
							ps.setInt(1, pVol);
							ps.setString(2, goodsName);
							ps.executeUpdate();
						}
						
						// 対応する部品とテープを取得
						String part = null, tape = null;
						try (PreparedStatement ps = conn.prepareStatement(
									"SELECT parts, tape FROM manu_goods WHERE m_goods = ?")) {
							ps.setString(1, goodsName);
							try (ResultSet rs = ps.executeQuery()) {
								if (rs.next()) {
									part = rs.getString("parts");
									tape = rs.getString("tape");
								}
							}
						}
						
						// partsテーブルから在庫を減算（部品・テープ）
						if (part != null) {
							try (PreparedStatement ps = conn.prepareStatement(
										"UPDATE parts SET pt_stock = pt_stock - ? WHERE parts_tape = ?")) {
								ps.setInt(1, pVol);
								ps.setString(2, part);
								ps.executeUpdate();
							}
						}
						
						if (tape != null) {
							try (PreparedStatement ps = conn.prepareStatement(
										"UPDATE parts SET pt_stock = pt_stock - ? WHERE parts_tape = ?")) {
								ps.setInt(1, pVol);
								ps.setString(2, tape);
								ps.executeUpdate();
							}
						}
					}
					
					if (hasShip) {
					// mg_stock から出荷分を減算
						try (PreparedStatement ps = conn.prepareStatement(
									"UPDATE manu_goods SET mg_stock = mg_stock - ? WHERE m_goods = ?")) {
							ps.setInt(1, sVol);
							ps.setString(2, goodsName);
							ps.executeUpdate();
						}
					}
				}
			}
			
			 // 不良品登録（d_volume）
			for (int i = 1; i <= 4; i++) {
				String part = request.getParameter("defect_part_" + i);	
				String volume = request.getParameter("defect_volume_" + i);
				
				if (part != null && !part.isEmpty() && volume != null && !volume.isEmpty()) {
					int dVol = Integer.parseInt(volume);
					
					// da_report に登録
					String defectSql = "INSERT INTO da_report (parts, d_volume, report_date) VALUES (?, ?, ?)";
					try (PreparedStatement ps = conn.prepareStatement(defectSql)) {
						ps.setString(1, part);
						ps.setInt(2, dVol);
						ps.setDate(3, reportDate);
						ps.executeUpdate();
					}
					
					// parts テーブル更新処理 
					// 「部品」で始まるか「テープ」で始まるかを判定
					if (part.startsWith("部品")) {
						// 部品指定 → 部品だけ減算
						try (PreparedStatement ps = conn.prepareStatement(
									"UPDATE parts SET pt_stock = pt_stock - ? WHERE parts_tape = ?")) {
							ps.setInt(1, dVol);
							ps.setString(2, part);
							ps.executeUpdate();
						}
						
					} else if (part.startsWith("テープ")) {
						// テープ指定 → テープだけ減算
						try (PreparedStatement ps = conn.prepareStatement(
									"UPDATE parts SET pt_stock = pt_stock - ? WHERE parts_tape = ?")) {
							ps.setInt(1, dVol);
							ps.setString(2, part);
							ps.executeUpdate();
						}
					} else {
						// どちらでもない場合はエラーログ出力（DBは更新しない）
						System.err.println("不良品指定が部品／テープのいずれでもありません: " + part);
					}
				}
			}
			
			// a_volume（追加）登録 
			for (int i = 1; i <= 4; i++) {
				String part = request.getParameter("add_part_" + i);
				String volume = request.getParameter("add_volume_" + i);
				
				if (part != null && !part.isEmpty() && volume != null && !volume.isEmpty()) {
					int aVol = Integer.parseInt(volume);
					
					// da_report に登録
					String addSql = "INSERT INTO da_report (parts, a_volume, report_date) VALUES (?, ?, ?)";
					try (PreparedStatement ps = conn.prepareStatement(addSql)) {
						ps.setString(1, part);
						ps.setInt(2, aVol);
						ps.setDate(3, reportDate);
						ps.executeUpdate();
					}
					
					// partsテーブルに在庫を加算（部品と対応テープ）
					String tape = null;
					try (PreparedStatement ps = conn.prepareStatement(
								"SELECT tape FROM manu_goods WHERE parts = ?")) {
						ps.setString(1, part);
						try (ResultSet rs = ps.executeQuery()) {
							if (rs.next()) tape = rs.getString("tape");
						}
					}
					
					try (PreparedStatement ps = conn.prepareStatement(
									"UPDATE parts SET pt_stock = pt_stock + ? WHERE parts_tape = ?")) {
						ps.setInt(1, aVol);
						ps.setString(2, part);
						ps.executeUpdate();
					}
					if (tape != null) {
						try (PreparedStatement ps = conn.prepareStatement(
									"UPDATE parts SET pt_stock = pt_stock + ? WHERE parts_tape = ?")) {
							ps.setInt(1, aVol);
							ps.setString(2, tape);
							ps.executeUpdate();
						}
					}
				}
			}
			
			conn.commit(); // 全処理が成功したら確定
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServletException("DB登録または在庫更新時にエラーが発生しました: " + e.getMessage(), e);
		}
		
		 // PRGパターンでリダイレクト
		response.sendRedirect(request.getContextPath() + "/complete");
		
	}
}
