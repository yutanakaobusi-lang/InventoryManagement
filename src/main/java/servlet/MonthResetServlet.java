package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/monthReset")
public class MonthResetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		
		String url = System.getenv("DB_URL");
		String user = System.getenv("DB_USER");
		String password = System.getenv("DB_PASSWORD");
		
		Connection conn = null;
		PreparedStatement psDelete = null;
		PreparedStatement psUpdate = null;
		PreparedStatement psClear = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false); // トランザクション開始
			
			// ① da_report テーブル全削除
			String sqlDelete = "DELETE FROM da_report";
			psDelete = conn.prepareStatement(sqlDelete);
			psDelete.executeUpdate();
			
			// ② manu_goods の c_month を n_month で上書き
			String sqlUpdate = "UPDATE manu_goods SET c_month = n_month";
			psUpdate = conn.prepareStatement(sqlUpdate);
			psUpdate.executeUpdate();
			
			// ③ manu_goods の n_month を NULL にする
			String sqlClear = "UPDATE manu_goods SET n_month = NULL";
			psClear = conn.prepareStatement(sqlClear);
			psClear.executeUpdate();
			
			// コミット
			conn.commit();
			
			response.setStatus(HttpServletResponse.SC_OK); // 成功を返す
			
		} catch (Exception e) {
			if (conn != null) try { conn.rollback(); } catch (SQLException ignore) {}
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} finally {
			if (psDelete != null) try { psDelete.close(); } catch (SQLException ignore) {}
			if (psUpdate != null) try { psUpdate.close(); } catch (SQLException ignore) {}
			if (psClear != null) try { psClear.close(); } catch (SQLException ignore) {}
			if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
		}
	}
}
