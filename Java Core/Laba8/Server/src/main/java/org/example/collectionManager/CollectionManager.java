package org.example.collectionManager;

import org.example.managers.DumpManager;
import org.example.models.Movie;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;


public class CollectionManager{
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final DumpManager dumpManager;
    private LinkedList<Movie> movies;
    public CollectionManager(DumpManager dumpManager){
        this.dumpManager = dumpManager;
    }

    /**
     * @return Последнее время инициализации.
     */
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
    public void setCollection(LinkedList<Movie> movies){
        this.movies = movies;
        setLastInitTime(LocalDateTime.now());
    }

    /**
     * Получить Movie по ID
     */
    public Movie byId(Integer id) {
        return movies.stream()
                .filter(movie -> id.equals(movie.getId()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Сохраняет коллекция в указанный файл
     */
    public void saveCollection(String user_name){
        dumpManager.saveToDataBase(movies, user_name);
        setLastSaveTime(LocalDateTime.now());
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
        IDManager.AddId(a.getId());
        movies.add(a);
    }
    /**
     * Удаляет Movie по ID
     */
    public void remove(Integer id) {
        var a = byId(id);
        if (a == null) return;
        IDManager.RemoveId(a.getId());
        movies.remove(a);
    }
    public ArrayList<String> getUsers(){
        return dumpManager.getUsers();
    }
}
