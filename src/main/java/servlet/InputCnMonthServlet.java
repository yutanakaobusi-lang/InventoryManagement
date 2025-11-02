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
import jakarta.servlet.http.HttpSession;

@WebServlet("/inputCnMonth")
public class InputCnMonthServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		
		// セッションに残っている monthData をクリア（通常遷移ではDB初期値を表示したい）
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute("monthData");
		}
		
		String url = System.getenv("DB_URL");
		String user = System.getenv("DB_USER");
		String password = System.getenv("DB_PASSWORD");
		
		List<Map<String, Object>> goodsList = new ArrayList<>();
		
		try {
			Class.forName("org.postgresql.Driver"); 
		} catch (ClassNotFoundException e) {
			throw new ServletException("PostgreSQL JDBCドライバが見つかりません。", e);
		}
		
		try (Connection conn = DriverManager.getConnection(url, user, password);
					PreparedStatement ps = conn.prepareStatement(
							"SELECT m_goods, c_month, n_month FROM manu_goods ORDER BY m_goods")) {
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				
				// --- n_month ---
				Integer nMonth = rs.getInt("n_month");
				if (rs.wasNull()) nMonth = null;
				
				Map<String, Object> data = new HashMap<>();
				data.put("m_goods", rs.getString("m_goods"));
				data.put("c_month", rs.getInt("c_month"));
				data.put("n_month", nMonth);
				goodsList.add(data);
			}
			
			request.setAttribute("goodsList", goodsList);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServletException("データベース接続または取得に失敗しました。", e);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/inputCnMonth.jsp");
		rd.forward(request, response);
	}
}
