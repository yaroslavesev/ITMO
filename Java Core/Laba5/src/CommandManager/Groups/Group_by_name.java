package CommandManager.Groups;

import CollectionManager.CollectionManager;
import CollectionManager.GroupManager;
import Models.Movie;

import java.util.HashMap;

public class Group_by_name{
    private final CollectionManager collectionManager;
    public Group_by_name(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
    /**
     * Выполнение команды
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    public String execution(){
        HashMap<String, Integer> nameGroup = new HashMap<>();
        for(Movie movie: collectionManager.getCollection()){
            if (nameGroup.containsKey(movie.getName())){
                Integer newValue = nameGroup.get(movie.getName()) + 1;
                nameGroup.replace(movie.getName(), newValue);
            } else {
                nameGroup.put(movie.getName(), 1);
            }
        }
        GroupManager<String> groupManager = new GroupManager<>(nameGroup);
        String result = "Всего групп: " + groupManager.groupSize() + "\n";
        result += groupManager.groupTop();
        return result;
    }
}
