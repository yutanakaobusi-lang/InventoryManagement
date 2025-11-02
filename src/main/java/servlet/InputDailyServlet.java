package servlet;

import java.io.IOException;
import java.time.LocalDate;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/inputDaily")
public class InputDailyServlet extends HttpServlet {
	
	//	新規入力用
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		
		 // reportDate が未設定（新規アクセス）の場合のみ、当日の日付をセット
		if (request.getAttribute("reportDate") == null) {
			request.setAttribute("reportDate", LocalDate.now().toString());
		}
		// JSPへのフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/inputDaily.jsp");
		dispatcher.forward(request, response);
	}
	
	//	checkDailyからデータを受け取って戻ってくる用
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		 // 日付データ（null の場合は今日の日付）
		String reportDate = request.getParameter("reportDate");
		if (reportDate == null || reportDate.isEmpty()) {
			reportDate = LocalDate.now().toString();
		}
		request.setAttribute("reportDate", reportDate);
		
		
		String[] goodsList = {"A", "B", "C", "D", "E", "F"};
		for (String g : goodsList) {
			request.setAttribute("prod_" + g, request.getParameter("prod_" + g));
			request.setAttribute("ship_" + g, request.getParameter("ship_" + g));
		}
		
		// 不良品データを再セット（最大4件）
		for (int i = 1; i <= 4; i++) {
			request.setAttribute("defect_part_" + i, request.getParameter("defect_part_" + i));
			request.setAttribute("defect_volume_" + i, request.getParameter("defect_volume_" + i));
		}
		
		// JSPへのフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/inputDaily.jsp");
		dispatcher.forward(request, response);
	}
}
