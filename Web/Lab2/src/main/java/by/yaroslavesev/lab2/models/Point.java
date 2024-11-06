package by.yaroslavesev.lab2.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Point {
    private double x;
    private double y;
    private double r;
    private boolean isInside;
    public boolean isInside() { return isInside; }
}
