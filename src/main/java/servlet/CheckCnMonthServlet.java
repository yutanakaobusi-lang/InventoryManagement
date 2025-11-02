package servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/checkCnMonth")
public class CheckCnMonthServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		Map<String, Map<String, Integer>> monthData = new LinkedHashMap<>();
		
		// 「c_month_製品名」「n_month_製品名」で受け取る
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String name = params.nextElement();
			if (name.startsWith("c_month_")) {
				String goods = name.substring("c_month_".length());
			    
				 // 値を取得（空欄・nullに対応）
				String cStr = request.getParameter(name);
				String nStr = request.getParameter("n_month_" + goods);
				
				Integer cValue = parseNullableInt(cStr);
				Integer nValue = parseNullableInt(nStr);
				
				Map<String, Integer> data = new HashMap<>();
				data.put("c_month", cValue);
				data.put("n_month", nValue);
				
				monthData.put(goods, data);
			}
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("monthData", monthData);
		
		request.setAttribute("monthData", monthData);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/checkCnMonth.jsp");
		rd.forward(request, response);
	}
	
	/** 空文字・nullなら null、数値文字列なら Integer に変換 */
	private Integer parseNullableInt(String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}
		
		try {
			return Integer.parseInt(value.trim());
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
