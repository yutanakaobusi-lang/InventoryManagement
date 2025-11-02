package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/stock")
public class StockServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		
		String url = System.getenv("DB_URL");
		String user = System.getenv("DB_USER");
		String password = System.getenv("DB_PASSWORD");
		
		List<Map<String, Object>> goodsList = new ArrayList<>();
		List<Map<String, Object>> partsList = new ArrayList<>();
		List<Map<String, Object>> tapeList = new ArrayList<>();
		
		try {
			Class.forName("org.postgresql.Driver"); 
		} catch (ClassNotFoundException e) {
			throw new ServletException("PostgreSQL JDBCドライバが見つかりません。", e);
		}
		
		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			
			// manu_goodsテーブルから取得
			String sqlGoods = "SELECT m_goods, mg_stock, c_month, n_month FROM manu_goods ORDER BY m_goods";
			try (PreparedStatement ps = conn.prepareStatement(sqlGoods);
						ResultSet rs = ps.executeQuery()) {
				
				while (rs.next()) {
					String m_goods = rs.getString("m_goods");
					int stock = rs.getInt("mg_stock");
					int c_month = rs.getInt("c_month");
					int n_month = rs.getInt("n_month");
					boolean nMonthIsNull = rs.wasNull();
					
					Map<String, Object> map = new HashMap<>();
					map.put("m_goods", m_goods);
					map.put("mg_stock", stock);
					
					int remain = c_month - stock;
					if (remain <= 0) {
						map.put("c_remain", "充足しています");
					} else {
						map.put("c_remain", remain);
					}
					
					if (nMonthIsNull) {
						map.put("n_month_val", "-");
					} else {
						int nextMonth = (remain < 0) ? n_month + remain : n_month;
						if (nextMonth <= 0) {
						map.put("n_month_val", "充足しています");
						} else {
						map.put("n_month_val", nextMonth);
						}
					}
					
					goodsList.add(map);
				}
			}
			
			// partsテーブルから部品とテープを取得
			String sqlParts = "SELECT parts_tape, pt_stock FROM parts ORDER BY parts_tape";
			try (PreparedStatement ps = conn.prepareStatement(sqlParts);
						ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String name = rs.getString("parts_tape");
					int stock = rs.getInt("pt_stock");
					
					Map<String, Object> map = new HashMap<>();
					map.put("name", name);
					map.put("stock", stock);
					
					if (name.startsWith("部品")) {
						partsList.add(map);
					} else if (name.startsWith("テープ")) {
						tapeList.add(map);
					}
				}
			}
			
			request.setAttribute("goodsList", goodsList);
			request.setAttribute("partsList", partsList);
			request.setAttribute("tapeList", tapeList);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServletException("データベース接続または取得に失敗しました。", e);
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/stock.jsp");
		dispatcher.forward(request, response);
	}
}
