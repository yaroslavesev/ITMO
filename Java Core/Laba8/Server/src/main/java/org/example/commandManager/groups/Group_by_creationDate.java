package org.example.commandManager.groups;

import org.example.collectionManager.CollectionManager;
import org.example.collectionManager.GroupManager;
import org.example.models.Movie;

import java.util.Date;
import java.util.HashMap;

public class Group_by_creationDate{
    private final CollectionManager collectionManager;
    public Group_by_creationDate(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
    /**
     * Выполнение команды
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    public String execution(){
        HashMap<Date, Integer> creationDateGroup = new HashMap<>();
        for(Movie movie: collectionManager.getCollection()){
            if (creationDateGroup.containsKey(movie.getCreationDate())){
                Integer newValue = creationDateGroup.get(movie.getCreationDate()) + 1;
                creationDateGroup.replace(movie.getCreationDate(), newValue);
            } else {
                creationDateGroup.put(movie.getCreationDate(), 1);
            }
        }
        GroupManager<Date> groupManager = new GroupManager<>(creationDateGroup);
        String result = "Всего групп: " + groupManager.groupSize() + "\n";
        result += groupManager.groupTop();
        return result;
    }
}
