package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/printRequestArrival")
public class PrintRequestArrivalServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 入力された4つの日付を取得し、曜日付き文字列に変換
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd");
        String[] dateLabels = new String[4];
        String[] dateValues = new String[4];        
        for (int i = 1; i <= 4; i++) {
            String dateStr = request.getParameter("date" + i);
            dateValues[i - 1] = dateStr; 
            
            if (dateStr != null && !dateStr.isEmpty()) {
                LocalDate date = LocalDate.parse(dateStr);
                String dayOfWeek = date.getDayOfWeek()
                        .getDisplayName(TextStyle.SHORT, Locale.JAPANESE);
                dateLabels[i - 1] = date.format(formatter) + "(" + dayOfWeek + ")";
            } else {
                dateLabels[i - 1] = "-";
            }
        }
        @SuppressWarnings("unchecked")
		Map<String, int[]> requestMap = (Map<String, int[]>) request.getSession().getAttribute("requestMap");
        @SuppressWarnings("unchecked")
		Map<String, Integer> quantityMap = (Map<String, Integer>) request.getSession().getAttribute("quantityMap");

        request.getSession().setAttribute("dateValues", dateValues);
        request.setAttribute("dateLabels", dateLabels);
        request.setAttribute("requestMap", requestMap);
        request.setAttribute("quantityMap", quantityMap);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/printRequestArrival.jsp");
        rd.forward(request, response);
    }
}
