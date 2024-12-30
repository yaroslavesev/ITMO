package by.yaroslavesev.lab2.controllers;

import by.yaroslavesev.lab2.models.Point;
import by.yaroslavesev.lab2.utills.ValidateArea;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Map<Double, List<Point>> radiusPoints = (Map<Double, List<Point>>) session.getAttribute("radiusPoints");
        if (radiusPoints == null) {
            radiusPoints = new HashMap<>();
        }

        List<Point> pointsList = radiusPoints.getOrDefault(r, new ArrayList<>());
        pointsList.add(result);
        radiusPoints.put(r, pointsList);
        session.setAttribute("radiusPoints", radiusPoints);
        
        List<Point> allPoints = (List<Point>) session.getAttribute("allPoints");
        if (allPoints == null) {
            allPoints = new ArrayList<>();
        }
        allPoints.add(result);
        session.setAttribute("allPoints", allPoints);

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
