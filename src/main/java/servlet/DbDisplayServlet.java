package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/dbDisplay")
public class DbDisplayServlet extends HttpServlet {
	
	private static final String JDBC_URL = System.getenv("DB_URL");
	private static final String DB_USER = System.getenv("DB_USER");
	private static final String DB_PASS = System.getenv("DB_PASSWORD");
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new ServletException("PostgreSQLドライバが見つかりません: " + e.getMessage());
		}
		
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			
			// da_report のデータを取得
			List<Map<String, Object>> daReportList = new ArrayList<>();
			List<String> daReportCols = new ArrayList<>();
			String sql1 = "SELECT * FROM da_report ORDER BY report_id DESC";
			try (PreparedStatement ps = conn.prepareStatement(sql1);
						ResultSet rs = ps.executeQuery()) {
				ResultSetMetaData meta = rs.getMetaData();
				int colCount = meta.getColumnCount();
				for (int i = 1; i <= colCount; i++) {
					daReportCols.add(meta.getColumnName(i));
				}
				while (rs.next()) {
					Map<String, Object> row = new LinkedHashMap<>();
					for (String col : daReportCols) {
						row.put(col, rs.getObject(col));
					}
					daReportList.add(row);
				}
			}
			
			// manu_goods のデータを取得
			List<Map<String, Object>> manuGoodsList = new ArrayList<>();
			List<String> manuGoodsCols = new ArrayList<>();
			
			String sql2 = "SELECT * FROM manu_goods ORDER BY m_goods" ;
			try (PreparedStatement ps = conn.prepareStatement(sql2);
							ResultSet rs = ps.executeQuery()) {
				ResultSetMetaData meta = rs.getMetaData();
				int colCount = meta.getColumnCount();
				for (int i = 1; i <= colCount; i++) {
					manuGoodsCols.add(meta.getColumnName(i));
				}
				while (rs.next()) {
					Map<String, Object> row = new LinkedHashMap<>();
					for (String col : manuGoodsCols) {
						row.put(col, rs.getObject(col));
					}
					manuGoodsList.add(row);
				}
			}
			
			// parts のデータを取得
			List<Map<String, Object>> partsList = new ArrayList<>();
			List<String> partsCols = new ArrayList<>();
			
			String sql3 = "SELECT * FROM parts ORDER BY parts_tape";
			try (PreparedStatement ps = conn.prepareStatement(sql3);
						ResultSet rs = ps.executeQuery()) {
				ResultSetMetaData meta = rs.getMetaData();
				int colCount = meta.getColumnCount();
				for (int i = 1; i <= colCount; i++) {
					partsCols.add(meta.getColumnName(i));
				}
				while (rs.next()) {
					Map<String, Object> row = new LinkedHashMap<>();
					for (String col : partsCols) {
						row.put(col, rs.getObject(col));
					}
					partsList.add(row);
				}
			}
			
			 // JSPへ渡す
			request.setAttribute("daReportCols", daReportCols);
			request.setAttribute("daReportList", daReportList);
			request.setAttribute("manuGoodsCols", manuGoodsCols);
			request.setAttribute("manuGoodsList", manuGoodsList);
			request.setAttribute("partsCols", partsCols);
			request.setAttribute("partsList", partsList);
			
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/dbDisplay.jsp");
			dispatcher.forward(request, response);
			
		} catch (SQLException e) {
			throw new ServletException("DB読み込み時にエラーが発生しました: " + e.getMessage(), e);
		}
	}
}
