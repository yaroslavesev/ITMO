package org.example.commandManager.groups;

import org.example.collectionManager.CollectionManager;
import org.example.collectionManager.GroupManager;
import org.example.models.Coordinates;
import org.example.models.Movie;

import java.util.HashMap;

public class Group_by_coordinates{
    private final CollectionManager collectionManager;
    public Group_by_coordinates(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
    /**
     * Выполнение команды
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    public String execution(){
        HashMap<Coordinates, Integer> coordinatesGroup = new HashMap<>();
        for(Movie movie: collectionManager.getCollection()){
            if (coordinatesGroup.containsKey(movie.getCoordinates())){
                Integer newValue = coordinatesGroup.get(movie.getCoordinates()) + 1;
                coordinatesGroup.replace(movie.getCoordinates(), newValue);
            } else {
                coordinatesGroup.put(movie.getCoordinates(), 1);
            }
        }
        GroupManager<Coordinates> groupManager = new GroupManager<>(coordinatesGroup);
        String result = "Всего групп: " + groupManager.groupSize() + "\n";
        result += groupManager.groupTop();
        return result;
    }
}
