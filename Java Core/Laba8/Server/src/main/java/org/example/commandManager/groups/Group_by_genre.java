package org.example.commandManager.groups;

import org.example.collectionManager.CollectionManager;
import org.example.collectionManager.GroupManager;
import org.example.models.Movie;

import java.util.HashMap;

public class Group_by_genre{
    private final CollectionManager collectionManager;
    public Group_by_genre(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
    /**
     * Выполнение команды
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    public String execution(){
        HashMap<Enum, Integer> genreGroup = new HashMap<>();
        for(Movie movie: collectionManager.getCollection()){
            if (genreGroup.containsKey(movie.getGenre())){
                Integer newValue = genreGroup.get(movie.getGenre()) + 1;
                genreGroup.replace(movie.getGenre(), newValue);
            } else {
                genreGroup.put(movie.getGenre(), 1);
            }
        }
        GroupManager<Enum> groupManager = new GroupManager<>(genreGroup);
        String result = "Всего групп: " + groupManager.groupSize() + "\n";
        result += groupManager.groupTop();
        return result;
    }
}
