package by.yaroslavesev.lab2;

import com.google.gson.Gson;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mes = "Какой-то из параметров не введен или введен некорректно!";
        Map<String, Object> jsonResponse = new HashMap<>();
        try {
            String x = request.getParameter("x");
            String y = request.getParameter("y");
            String r = request.getParameter("r");
            if (x == null || y == null || r == null || x.isEmpty() || y.isEmpty() || r.isEmpty()) {
                makeMessage(response, mes);
                return;
            }
            float xValue = Float.parseFloat(x);
            float yValue = Float.parseFloat(y);
            float rValue = Float.parseFloat(r);
            jsonResponse.put("x", xValue);
            jsonResponse.put("y", yValue);
            jsonResponse.put("r", rValue);
            jsonResponse.put("message", "Параметры получены успешно!");
            request.getRequestDispatcher("/checkArea?" + request.getQueryString()).forward(request, response);
        } catch (Exception e) {
            makeMessage(response, e.toString());
        }
    }

    public void makeMessage(HttpServletResponse response, String message) throws IOException {
        Gson json = new Gson();
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("error", message);
        jsonResponse.put("status", "UNPROCESSABLE_ENTITY");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toJson(jsonResponse));
        response.setStatus(422);
    }
}