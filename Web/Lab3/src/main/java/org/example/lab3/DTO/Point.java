package org.example.lab3.DTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Point {
    private double x;
    private double y;
    private double r;
    private boolean result;
    private String time;

    public Point(double x, double y, double r, boolean result) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.time = LocalDateTime.now().format(formatter);
    }

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

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getDateTime() {return time; }

    public void setDateTime(String time) {this.time = time;}
}
