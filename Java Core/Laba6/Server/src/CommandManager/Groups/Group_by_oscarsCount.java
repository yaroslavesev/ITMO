package CommandManager.Groups;

import CollectionManager.CollectionManager;
import CollectionManager.GroupManager;
import Models.Movie;

import java.util.HashMap;

public class Group_by_oscarsCount{
    private final CollectionManager collectionManager;
    public Group_by_oscarsCount(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
    /**
     * Выполнение команды
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    public String execution(){
        HashMap<Integer, Integer> oscarsCountGroup = new HashMap<>();
        for(Movie movie: collectionManager.getCollection()){
            if (oscarsCountGroup.containsKey(movie.getOscarsCount())){
                Integer newValue = oscarsCountGroup.get(movie.getOscarsCount()) + 1;
                oscarsCountGroup.replace(movie.getOscarsCount(), newValue);
            } else {
                oscarsCountGroup.put(movie.getOscarsCount(), 1);
            }
        }
        GroupManager<Integer> groupManager = new GroupManager<>(oscarsCountGroup);
        String result = "Всего групп: " + groupManager.groupSize() + "\n";
        result += groupManager.groupTop();
        return result;
    }
}
