package Models;

import java.io.Serializable;
import java.util.Objects;

public class Coordinates implements Validatable, Comparable<Coordinates>, Serializable {
    private long x;
    private int y; //Максимальное значение поля: 628
    private static final long SerialVersionUID = 12345678L;
    public Coordinates(long x, int y) {
        this.x = x;
        this.y = y;
    }
    public  Coordinates(){}
    public void setX(long x) {
        this.x = x;
    }
    public long getX(){
        return x;
    }

    @Override
    public String toString() {
        return "     Coordinates X: " + getX() + "\n" +
                "     Coordinates Y: " + getY() + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates that)) return false;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public long getY(){
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    @Override
    public boolean validate(){
        return y <= 628;
    }
    @Override
    public int compareTo(Coordinates other) {
        Long x1 = this.x;
        Integer y1 = this.y;
        Long x2 = other.x;
        Integer y2 = other.y;
        int result = x1.compareTo(x2);
        if (result == 0){
            result = y1.compareTo(y2);
        }
        return result;
    }
}
