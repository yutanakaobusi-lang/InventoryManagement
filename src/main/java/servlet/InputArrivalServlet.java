package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/inputArrival")
public class InputArrivalServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// checkArrival.jspから戻る場合、パラメータに値が含まれているならそれを使用
		String arrivalDate = request.getParameter("arrivalDate");
			if (arrivalDate == null || arrivalDate.isEmpty()) {
			// 新規アクセス時のみ当日の日付をセット
			arrivalDate = LocalDate.now().toString();
		}
			request.setAttribute("arrivalDate", arrivalDate);
		
		// checkArrival.jsp から戻る場合の入力データを再構築
		Map<String, String> arrivalMap = new LinkedHashMap<>();
		
		String[] partNames = {"部品01", "部品02", "部品03", "部品04", "部品05",
		  "テープ01", "テープ02", "テープ03", "テープ04"};
		
		boolean hasData = false;
		for (int i = 0; i < partNames.length; i++) {
			String paramName;
			if (i < 5) {
				paramName = "arrival_part_" + (i + 1);
			} else {
				paramName = "arrival_tape_" + (i - 4);
			}
			String value = request.getParameter(paramName);
			if (value != null && !value.isEmpty()) {
				hasData = true;
			}
			arrivalMap.put(partNames[i], value != null ? value : "");
		}
		
		// 新規アクセス時は空欄を作成
		if (!hasData) {
			for (String key : arrivalMap.keySet()) {
				arrivalMap.put(key, "");
			}
		}
		
		request.setAttribute("arrivalMap", arrivalMap);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/inputArrival.jsp");
		dispatcher.forward(request, response);
	}
	
	 @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		// 日付データ（null の場合は今日の日付）
		String arrivalDate = request.getParameter("arrivalDate");
		if (arrivalDate == null || arrivalDate.isEmpty()) {
			arrivalDate = LocalDate.now().toString();
		}
		request.setAttribute("arrivalDate", arrivalDate);
		
		request.getRequestDispatcher("/WEB-INF/jsp/inputArrival.jsp").forward(request, response);
	}
}
