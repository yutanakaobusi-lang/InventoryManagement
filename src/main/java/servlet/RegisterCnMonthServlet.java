package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/registerCnMonth")
public class RegisterCnMonthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			// DB接続情報
			String url = System.getenv("DB_URL");
			String user = System.getenv("DB_USER");
			String password = System.getenv("DB_PASSWORD");
			
			// JDBCドライバのロード（PostgreSQL用）
			Class.forName("org.postgresql.Driver");
			
			// 接続開始
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false); // トランザクション開始
			
			// セッションから更新データ取得
			HttpSession session = request.getSession(false);
			if (session == null) {
				throw new ServletException("セッションが無効です。");
			}
			
			@SuppressWarnings("unchecked")
			Map<String, Map<String, Integer>> monthData =
			(Map<String, Map<String, Integer>>) session.getAttribute("monthData");
			
			if (monthData == null) {
				throw new ServletException("更新データが存在しません。");
			}
			
			// SQL文
			String sql = "UPDATE manu_goods SET c_month = ?, n_month = ? WHERE m_goods = ?";
			pstmt = conn.prepareStatement(sql);
			
			// 各製品ごとに更新
			for (String goods : monthData.keySet()) {
				Map<String, Integer> values = monthData.get(goods);
				Integer cMonth = values.get("c_month");
				Integer nMonth = values.get("n_month");
				    
				// nullの場合は setNull、それ以外は setInt
				if (cMonth == null) {
					pstmt.setNull(1, Types.INTEGER);
				} else {
					pstmt.setInt(1, cMonth);
				}
				
				if (nMonth == null) {
					pstmt.setNull(2, Types.INTEGER);
				} else {
					pstmt.setInt(2, nMonth);
				}
				
				pstmt.setString(3, goods);
				pstmt.executeUpdate();
				
			}
			
			// コミット
			conn.commit();
			
			// セッション内の monthData を削除（安全措置）
			session.removeAttribute("monthData");
			
			// PRGパターンでリダイレクト
			response.sendRedirect(request.getContextPath() + "/complete");
			
		} catch (Exception e) {
			// エラー時はロールバック
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			e.printStackTrace();
			throw new ServletException("データベース更新中にエラーが発生しました: " + e.getMessage());
			
		} finally {
			// 後処理
			if (pstmt != null) try { pstmt.close(); } catch (SQLException ignore) {}
			if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
		}
	}
}
