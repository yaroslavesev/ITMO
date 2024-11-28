package CollectionManager;
import DumpManager.DumpManager;
import Models.Coordinates;
import Models.Movie;
import Models.MovieGenre;
import Models.Person;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;

import static CollectionManager.IDManager.AddId;
import static CollectionManager.IDManager.RemoveId;

public class CollectionManager{
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final DumpManager dumpManager = new DumpManager(this);
    private final LinkedList<Movie> movies = new LinkedList<>();
    /**
     * @return Последнее время инициализации.
     */
    public Movie MovieCreator(Integer id, String name, Coordinates coordinates, Date creationDate, Integer oscarsCount, double budget, String tagline, MovieGenre genre, Person screenwriter) {
        return new Movie(id, name, coordinates, creationDate, oscarsCount, budget, tagline, genre, screenwriter);
    }
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return Последнее время сохранения.
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    public void setLastInitTime(LocalDateTime lastInitTime) {
        this.lastInitTime = lastInitTime;
    }

    public void setLastSaveTime(LocalDateTime lastSaveTime) {
        this.lastSaveTime = lastSaveTime;
    }

    /**
     * @return коллекция.
     */
    public LinkedList<Movie> getCollection() {
        return movies;
    }

    /**
     * Получить Movie по ID
     */
    public Movie byId(Integer id) {
        for (Movie movie : movies){
            if (id.equals(movie.getId())) return movie;
        }
        return null;
    }

    /**
     * Сохраняет коллекция в указанный файл
     */
    public void saveCollection(String path){
        try {
            dumpManager.Writer(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Содержит ли коллекции Movie
     */
    public boolean isContains(Movie e) {
        return e == null || byId(e.getId()) != null;
    }

    /**
     * Добавляет Movie
     */
    public void add(Movie a) {
        if (isContains(a)) return;
        AddId(a.getId());
        movies.add(a);
    }
    /**
     * Удаляет Movie по ID
     */
    public void remove(Integer id) {
        var a = byId(id);
        if (a == null) return;
        RemoveId(a.getId());
        movies.remove(a);
    }
}
