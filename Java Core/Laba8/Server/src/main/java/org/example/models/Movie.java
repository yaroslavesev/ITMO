package org.example.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Movie implements Validatable, Serializable {
    private static final long SerialVersionUID = 123456L;
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private String user_name;
    public Movie() {

    }

    public Movie(Integer id, String name, Coordinates coordinates, Date creationDate, Integer oscarsCount, double budget, String tagline, MovieGenre genre, Person screenwriter) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.oscarsCount = oscarsCount;
        this.budget = budget;
        this.tagline = tagline;
        this.genre = genre;
        this.screenwriter = screenwriter;
    }

    @Override
    public String toString() {
        return  "$" + user_name + "\n" +
                "Movie ID: " + id + "\n" +
                "Name: " + name + "\n" +
                coordinates +
                "Creation Date: " + creationDate + "\n" +
                "Oscars Count: " + oscarsCount + "\n" +
                "Budget: " + budget + "\n" +
                "Tagline: " + tagline + "\n" +
                "Genre: " + genre + "\n" +
                screenwriter + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie movie)) return false;
        return Double.compare(budget, movie.budget) == 0 && Objects.equals(id, movie.id) && Objects.equals(name, movie.name) && Objects.equals(coordinates, movie.coordinates) && Objects.equals(creationDate, movie.creationDate) && Objects.equals(oscarsCount, movie.oscarsCount) && Objects.equals(tagline, movie.tagline) && genre == movie.genre && Objects.equals(screenwriter, movie.screenwriter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, oscarsCount, budget, tagline, genre, screenwriter);
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Integer getOscarsCount() {
        return oscarsCount;
    }

    public double getBudget() {
        return budget;
    }

    public String getTagline() {
        return tagline;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public Person getScreenWriter() {
        return screenwriter;
    }
    private Integer oscarsCount; //Значение поля должно быть больше 0, Поле не может быть null
    private double budget; //Значение поля должно быть больше 0
    private String tagline; //Строка не может быть пустой, Поле не может быть null
    private MovieGenre genre; //Поле не может быть null
    private Person screenwriter;

    public void setUser_name(String user_name){
        this.user_name = user_name;
    }
    public String getUser_name(){
        return user_name;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setOscarsCount(int oscarsCount) {
        this.oscarsCount = oscarsCount;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }
//
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }
    //
    public void setScreenwriter(Person screenwriter) {
        this.screenwriter = screenwriter;
    }

    @Override
    public boolean validate(){
        if (id <= 0) return false;
        if (name.isEmpty()) return false;
        if (coordinates == null || !coordinates.validate()) return false;
        if (creationDate == null) return false;
        if (oscarsCount <= 0 ) return false;
        if (budget <= 0) return false;
        if (tagline.isEmpty()) return false;
        if (genre == null) {
            return false;
        } else {
            boolean genreChecker = false;
            for (MovieGenre genre1 : MovieGenre.values()) {
                if (genre1 == genre) {
                    genreChecker = true;
                    break;
                }
            }
            if (!genreChecker) return false;
        }
        if (!(screenwriter == null)){
            if (!screenwriter.validate()){
                return false;
            }
            return screenwriter.getLocation().validate();
        }
        return true;
    }
}

