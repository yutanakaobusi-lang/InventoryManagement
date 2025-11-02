package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/requestArrival")
public class RequestArrivalServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new ServletException("PostgreSQL JDBCドライバが見つかりません。", e);
		}
		
		String url = System.getenv("DB_URL");
		String user = System.getenv("DB_USER");
		String password = System.getenv("DB_PASSWORD");
		
		Map<String, int[]> requestMap = new LinkedHashMap<>();
		Map<String, Integer> quantityMap = new HashMap<>();
		
		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			
				
			// --- partsテーブルを取得 ---
			Map<String, Integer> stockMap = new HashMap<>();
			Map<String, Integer> qtyMap = new HashMap<>();
			
			String sqlParts = "SELECT parts_tape, pt_stock, quantity FROM parts";
			try (PreparedStatement ps = conn.prepareStatement(sqlParts);
					ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String name = rs.getString("parts_tape");
					stockMap.put(name, rs.getInt("pt_stock"));
					qtyMap.put(name, rs.getInt("quantity"));
				}
			}
			
			
			// --- manu_goodsテーブルを全取得 ---
			String sqlGoods = "SELECT m_goods, parts, tape, mg_stock, c_month, n_month "
											+ "FROM manu_goods ORDER BY parts ASC, tape ASC";
			Map<String, List<Map<String, Integer>>> usageMap = new HashMap<>();
			
			try (PreparedStatement ps = conn.prepareStatement(sqlGoods);
					ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String part = rs.getString("parts");
					String tape = rs.getString("tape");
					
					int mgStock = rs.getInt("mg_stock");
					int cMonth = rs.getInt("c_month");
					int nMonth = rs.getInt("n_month");
					
					usageMap.computeIfAbsent(part, k -> new ArrayList<>())
							.add(Map.of("mg_stock", mgStock, "c_month", cMonth, "n_month", nMonth));
					usageMap.computeIfAbsent(tape, k -> new ArrayList<>())
							.add(Map.of("mg_stock", mgStock, "c_month", cMonth, "n_month", nMonth));
				}
			}
			
			
			// --- 並び順を指定（部品 → テープ） ---
			List<String> sortedKeys = new ArrayList<>(stockMap.keySet());
			sortedKeys.sort(
					(a, b) -> {
					boolean aIsPart = a.startsWith("部品");
					boolean bIsPart = b.startsWith("部品");
					if (aIsPart && !bIsPart) return -1;
					if (!aIsPart && bIsPart) return 1;
					return a.compareTo(b);
					}
			);
			
			
			// --- 計算：（当月内示 + 次月内示 - 在庫数）/ 入り数 （小数点以下切り上げ）、 ---
			// --- 上記の答え / 4 （4週間） （小数点以下切り捨て） 切り捨てた小数点以下 * 4 （小数点以下切り上げ）を2週目に追加 ---
			for (String name : sortedKeys) {
				List<Map<String, Integer>> list = usageMap.get(name);
				if (list == null) {
					requestMap.put(name, new int[]{0, 0, 0, 0});
					quantityMap.put(name, qtyMap.getOrDefault(name, 1));
					continue;
				}
				
				int mgSum = 0, cSum = 0, nSum = 0;
				for (Map<String, Integer> m : list) {
					mgSum += m.get("mg_stock");
					cSum += m.get("c_month");
					nSum += m.get("n_month");
				}
				
				int ptStock = stockMap.getOrDefault(name, 0);
				int quantity = qtyMap.getOrDefault(name, 1);
				quantityMap.put(name, quantity);
				
				int diff = (cSum + nSum - mgSum) - ptStock;
				if (diff < 0) diff = 0;
				int answer = (int) Math.ceil((double) diff / quantity);
				
				int base = answer / 4;
				int rem = answer % 4;
				int[] values = new int[4];
				Arrays.fill(values, base);
				if (answer > 0) values[1] = base + rem;
				requestMap.put(name, values);
			}
			
			
			request.getSession().setAttribute("requestMap", requestMap);
			request.getSession().setAttribute("quantityMap", quantityMap);
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/requestArrival.jsp");
			rd.forward(request, response);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServletException("データベース接続または計算中にエラーが発生しました。", e);
		}
	}
}
