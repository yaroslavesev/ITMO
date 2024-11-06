package by.yaroslavesev.lab2.utills;

public class ValidateData {
    public static boolean checkPoint(String x, String y, String r) {
        return checkX(x) && checkY(y) && checkR(r);
    }

    public static boolean checkX(String xValue) {
        if (xValue == null || xValue.isEmpty()) {
            return false;
        }

        try {
            double numX = Double.parseDouble(xValue);
            if (numX < -3 || numX > 5) {
                return false;
            }
            if (numX == -3 || numX == 5) {
                String regex = "^[+-]?\\d+(\\.0*)?$";
                return xValue.matches(regex);
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean checkY(String yValue) {
        if (yValue == null || yValue.isEmpty()) {
            return false;
        }

        try {
            double numY = Double.parseDouble(yValue);
            return numY >= -3 && numY <= 5;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean checkR(String rValue) {
        if (rValue == null || rValue.isEmpty()) {
            return false;
        }

        try {
            double numR = Double.parseDouble(rValue);
            return numR >= 1 && numR <= 3;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}