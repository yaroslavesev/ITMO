package by.yaroslavesev.backend.utils;

import static java.lang.Math.abs;

public class PointUtils {
    public static boolean checkPoint(double x, double y, double r) {
        if ((x <= 0 && y >= 0) && (abs(x) + abs(y) <= r)){
            return true;
        } else if ((x >= 0 && x <= r) && (y >= 0 && y <= r / 2)){
            return true;
        } else {
            return (x <= 0 && y <= 0) && (x * x + y * y <= r * r);
        }
    }
}
