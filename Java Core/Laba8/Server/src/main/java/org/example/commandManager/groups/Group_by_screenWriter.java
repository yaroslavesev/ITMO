package org.example.commandManager.groups;

import org.example.collectionManager.CollectionManager;
import org.example.collectionManager.GroupManager;
import org.example.models.Movie;
import org.example.models.Person;

import java.util.HashMap;

public class Group_by_screenWriter{
    private final CollectionManager collectionManager;
    public Group_by_screenWriter(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
    /**
     * Выполнение команды
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    public String execution(){
        HashMap<Person, Integer> screenWriterGroup = new HashMap<>();
        for(Movie movie: collectionManager.getCollection()){
            if (screenWriterGroup.containsKey(movie.getScreenWriter())){
                Integer newValue = screenWriterGroup.get(movie.getScreenWriter()) + 1;
                screenWriterGroup.replace(movie.getScreenWriter(), newValue);
            } else {
                screenWriterGroup.put(movie.getScreenWriter(), 1);
            }
        }
        GroupManager<Person> groupManager = new GroupManager<>(screenWriterGroup);
        String result = "Всего групп: " + groupManager.groupSize() + "\n";
        result += groupManager.groupTop();
        return result;
    }
}
