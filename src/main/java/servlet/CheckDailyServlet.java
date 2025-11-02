package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/checkDaily")
public class CheckDailyServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		String reportDate = request.getParameter("reportDate");
		request.setAttribute("reportDate", reportDate);
		
		
		// 製品ごとの生産数・出荷数を受け取る
		String prod_A = request.getParameter("prod_A");
		String prod_B = request.getParameter("prod_B");
		String prod_C = request.getParameter("prod_C");
		String prod_D = request.getParameter("prod_D");
		String prod_E = request.getParameter("prod_E");
		String prod_F = request.getParameter("prod_F");
		
		
		// 不良発生数（4件固定） 
		List<String> defectParts = new ArrayList<>();
		List<String> defectVolumes = new ArrayList<>();
		
		for (int i = 1; i <= 4; i++) {
			String part = request.getParameter("defect_part_" + i);
			String volume = request.getParameter("defect_volume_" + i);
			
			// 空欄を除外して登録
			if (part != null && !part.isEmpty() && volume != null && !volume.isEmpty()) {
				defectParts.add(part);
				defectVolumes.add(volume);
			}
		}
		
		
		
		
		// 出荷数
		String ship_A = request.getParameter("ship_A");
		String ship_B = request.getParameter("ship_B");
		String ship_C = request.getParameter("ship_C");
		String ship_D = request.getParameter("ship_D");
		String ship_E = request.getParameter("ship_E");
		String ship_F = request.getParameter("ship_F");
		
		// JSPに渡す
		request.setAttribute("prod_A", prod_A);
		request.setAttribute("prod_B", prod_B);
		request.setAttribute("prod_C", prod_C);
		request.setAttribute("prod_D", prod_D);
		request.setAttribute("prod_E", prod_E);
		request.setAttribute("prod_F", prod_F);
		
		
		request.setAttribute("defectPartList", defectParts);
		request.setAttribute("defectList", defectVolumes);
		
		
		request.setAttribute("ship_A", ship_A);
		request.setAttribute("ship_B", ship_B);
		request.setAttribute("ship_C", ship_C);
		request.setAttribute("ship_D", ship_D);
		request.setAttribute("ship_E", ship_E);
		request.setAttribute("ship_F", ship_F);
		
		// 確認画面へ
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/checkDaily.jsp");
		dispatcher.forward(request, response);
		
		
	}
}
