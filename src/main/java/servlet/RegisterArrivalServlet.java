package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/registerArrival")
public class RegisterArrivalServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new ServletException("PostgreSQL JDBC ドライバが見つかりません", e);
		}
		
		
		String arrivalDateStr = request.getParameter("arrivalDate");
		Date arrivalDate = Date.valueOf(arrivalDateStr);
		
		// JSP側のフォームに合わせてパラメータ名を定義
		String[] partNames = request.getParameterValues("partName");
		String[] arrivalVolumes = request.getParameterValues("a_volume");
		
		// nullチェック
		if (partNames == null || arrivalVolumes == null) {
			response.sendRedirect("error.jsp");
			return;
		}
		
		// DB接続情報
		String url = System.getenv("DB_URL");
		String user = System.getenv("DB_USER");
		String password = System.getenv("DB_PASSWORD");
		
		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			conn.setAutoCommit(false); // トランザクション開始
			
			String insertSql = "INSERT INTO da_report (parts, a_volume, report_date) VALUES (?, ?, ?)";
			String updateSql = "UPDATE parts SET pt_stock = pt_stock + ? WHERE parts_tape = ?";
			
			try (PreparedStatement insertPs = conn.prepareStatement(insertSql);
				 PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
				
				for (int i = 0; i < partNames.length; i++) {
					String part = partNames[i];
					String volumeStr = arrivalVolumes[i];
					
					if (volumeStr == null || volumeStr.isEmpty()) {
						continue; // 入力なしはスキップ
					}
					
					int volume = Integer.parseInt(volumeStr);
					
					// ===== da_report にINSERT =====
					insertPs.setString(1, part);
					insertPs.setInt(2, volume);
					insertPs.setDate(3, arrivalDate);
					insertPs.executeUpdate();
					
					// ===== parts.pt_stock に加算 =====
					updatePs.setInt(1, volume);
					updatePs.setString(2, part);
					updatePs.executeUpdate();
				}
				
				conn.commit(); // 成功したら確定
			} catch (Exception e) {
				conn.rollback(); // エラー時はロールバック
				e.printStackTrace();
				throw new ServletException("入庫登録中にエラーが発生しました", e);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServletException("DB接続に失敗しました", e);
		}
		
		// PRGパターンでリダイレクト
		response.sendRedirect(request.getContextPath() + "/complete");
		
	}
}
