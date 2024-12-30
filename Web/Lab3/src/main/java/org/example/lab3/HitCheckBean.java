package org.example.lab3;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.example.lab3.DTO.Point;
import org.example.lab3.services.HistoryService;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.lang.Math.abs;

@Named("hitCheckBean")
@SessionScoped
public class HitCheckBean implements Serializable {
    @Inject
    private HistoryService historyService;

    public HitCheckBean(String serverId) {
        this.serverId = serverId;
        System.out.println("org.example.lab3.HitCheckBean initialized");
    }

    public HitCheckBean() {
        // Генерируем уникальный идентификатор при старте сервера/приложения
        this.serverId = UUID.randomUUID().toString();
    }


    private double x;
    private double y;
    private double r;
    private final String serverId;

    private List<Point> results = new ArrayList<>();

    // Getters и Setters
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public String getServerId() {
        return serverId;
    }

    public List<Point> getResults() {
        return results;
    }

    public void button() {
        double xSave = x;
        double ySave = y;
//        x = x * r;
//        y = y * r;
        boolean result = checkPoint(x, y, r);
        Point pointResult = new Point(x, y, r, result);
        results.add(0, pointResult);
        historyService.addHistoryRecord(x, y, r, result);

        for (Point point : results) {
            System.out.println(point.getX() + " " + point.getY() + " " + point.getR() + " " +
                    point.getResult() + " " + point.getDateTime());
        }

        System.out.println("Coordinates: X=" + x + ", Y=" + y + ", R=" + r);
        System.out.println("Result: " + result);

        PrimeFaces.current().ajax().addCallbackParam("result", result);
        x = xSave;
        y = ySave;
    }

    public void process() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        double x = Double.parseDouble(params.get("x"));
        double y = Double.parseDouble(params.get("y"));
        double r = Double.parseDouble(params.get("r"));

        boolean result = checkPoint(x, y, r);
        Point pointResult = new Point(x, y, r, result);
        results.add(0, pointResult);
        historyService.addHistoryRecord(x, y, r, result);

        for (Point point : results) {
            System.out.println(point.getX() + " " + point.getY() + " " + point.getR() + " " +
                    point.getResult() + " " + point.getDateTime());
        }

        System.out.println("Coordinates: X=" + x + ", Y=" + y + ", R=" + r);
        System.out.println("Result: " + result);

        PrimeFaces.current().ajax().addCallbackParam("result", result);
    }

    public boolean checkPoint(double x, double y, double r) {
        if (x >= 0 && y <= 0 && x * x + y * y <= 0.25 * r * r) {
            return true;
        } else if (x >= 0 && y >= 0 && 0.5 * abs(x) + abs(y) <= r/2) {
            return true;
        } else {
            return x <= 0 && y >= 0 && x >= -r && y <= r;
        }
    }
}