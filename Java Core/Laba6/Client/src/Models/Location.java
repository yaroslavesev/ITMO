package Models;

import java.io.Serializable;
import java.util.Objects;

public class Location implements Validatable, Serializable {
    private float x;
    private static final long SerialVersionUID = 1234567L;
    public Location(float x, Float y, float z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }
    public Location(){}
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "         Name: " + getName() + "\n" +
                "         Location X: " + getX() + "\n" +
                "         Location Y: " + getY() + "\n" +
                "         Location Z: " + getZ() + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location location)) return false;
        return y.equals(location.y) && Objects.equals(x, location.x) && Objects.equals(z, location.z) && Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, name);
    }

    public float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }//Поле не может быть null

    public float getZ() {
        return z;
    }

    public String getName() {
        return name;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Float y;//Поле не может быть null
    private float z;
    private String name; //Поле не может быть null
    @Override
    public boolean validate(){
        return true;
    }
}
