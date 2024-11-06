package by.yaroslavesev.lab2.controllers;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import by.yaroslavesev.lab2.models.Point;
import by.yaroslavesev.lab2.utills.ValidateArea;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/check")
public class AreaCheckServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long startTime = System.nanoTime();
        String xParam = request.getParameter("x");
        String yParam = request.getParameter("y");
        String rParam = request.getParameter("r");
        double x = Double.parseDouble(xParam);
        double y = Double.parseDouble(yParam);
        double r = Double.parseDouble(rParam);
        boolean isInside = ValidateArea.checkPoint(x, y, r);
        Point result = new Point(x, y, r, isInside);
        long endTime = System.nanoTime();
        long executionTime = (endTime - startTime) / 1000000;
        HttpSession session = request.getSession();
        session.setAttribute("lastX", xParam);
        session.setAttribute("lastY", yParam);
        session.setAttribute("lastR", rParam);
        List<Point> results = (List<Point>) session.getAttribute("results");
        if (results == null) {
            results = new ArrayList<>();
        }
        results.add(result);
        session.setAttribute("results", results);
        String isAjax = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(isAjax)) {
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print("{\"answer\": " + isInside + ", \"executionTime\": " + executionTime + "}");
            out.flush();
        } else {
            request.setAttribute("currentResult", result);
            getServletContext().getRequestDispatcher("/result.jsp").forward(request, response);
        }
    }
}