package by.yaroslavesev.lab2.utills;

import static java.lang.Math.abs;

public class ValidateArea {
    public static boolean checkPoint(double x, int y, double r) {
        return isInsideRectangle(x, y, r) || isInsideTriangle(x, y, r) || isInsideSector(x, y, r);
    }

    private static boolean isInsideTriangle(double x, int y, double r) {
        return ((x <= 0 && y >= 0) && (abs(x) + abs(y) <= r));
    }

    private static boolean isInsideRectangle(double x, int y, double r) {
        return ((x >= 0 && x <= r) && (y >= 0 && y <= r / 2));
    }

    private static boolean isInsideSector(double x, int y, double r) {
        return ((x <= 0 && y <= 0) && (x * x + y * y <= r * r));
    }
}
