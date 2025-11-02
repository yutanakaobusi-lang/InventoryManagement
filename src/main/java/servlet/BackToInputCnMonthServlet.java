package servlet;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/backToInputCnMonth")
public class BackToInputCnMonthServlet extends HttpServlet {
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	
	throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		Map<String, Map<String, Integer>> monthData =
				(Map<String, Map<String, Integer>>) session.getAttribute("monthData");
		
		request.setAttribute("monthData", monthData);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/inputCnMonth.jsp");
		rd.forward(request, response);
	}
}
