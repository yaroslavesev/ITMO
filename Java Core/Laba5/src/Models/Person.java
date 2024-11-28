package Models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Person implements Validatable{
    private String name; //Поле не может быть null, Строка не может быть пустой
    private LocalDate birthday; //Поле не может быть null

    public Person(){}

    public Person(String name, LocalDateTime birthday, Location location) {
        this.name = name;
        this.birthday = LocalDate.from(birthday);
        this.location = location;
    }
    @Override
    public String toString() {
        return "     Name: " + getName() + "\n" +
                "     Birthday: " + getBirthday() + "\n" +
                getLocation() + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return Objects.equals(name, person.name) && Objects.equals(birthday, person.birthday) && Objects.equals(location, person.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthday, location);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    private Location location; //Поле может быть null

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    @Override
    public boolean validate(){
        if (name.isEmpty()) return false;
        if (birthday == null) return false;
        return location != null && location.validate();
    }
    public LocalDate getBirthday(){
        return birthday;
    }

    public int compareTo(Person other) {
        int result = this.birthday.compareTo(other.birthday);
        if (result == 0){
            result = this.name.compareTo(other.name);
        }
        return result;
    }
}
