package servlet;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/checkArrival")
public class CheckArrivalServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		String arrivalDate = request.getParameter("arrivalDate");
		
		Map<String, String> arrivalMap = new LinkedHashMap<>();
		
		// 部品01〜05
		for (int i = 1; i <= 5; i++) {
			String qty = request.getParameter("arrival_part_" + i);
			if (qty != null && !qty.isEmpty()) {
			arrivalMap.put("部品0" + i, qty);
			}
		}
		
		// テープ01〜04
		for (int i = 1; i <= 4; i++) {
			String qty = request.getParameter("arrival_tape_" + i);
			if (qty != null && !qty.isEmpty()) {
			arrivalMap.put("テープ0" + i, qty);
			}
		}
		
		 // セッションに保存（戻る時の再表示用）
		HttpSession session = request.getSession();
		session.setAttribute("arrivalDate", arrivalDate);
		session.setAttribute("arrivalMap", arrivalMap);
		
		request.setAttribute("arrivalMap", arrivalMap);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/checkArrival.jsp");
		dispatcher.forward(request, response);
		
	}
}
