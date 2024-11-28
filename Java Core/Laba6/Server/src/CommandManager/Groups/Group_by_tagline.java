package CommandManager.Groups;

import CollectionManager.CollectionManager;
import CollectionManager.GroupManager;
import Models.Movie;

import java.util.HashMap;

public class Group_by_tagline{
    private final CollectionManager collectionManager;
    public Group_by_tagline(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
    /**
     * Выполнение команды
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    public String execution(){
        HashMap<String, Integer> taglineGroup = new HashMap<>();
        for(Movie movie: collectionManager.getCollection()){
            if (taglineGroup.containsKey(movie.getTagline())){
                Integer newValue = taglineGroup.get(movie.getTagline()) + 1;
                taglineGroup.replace(movie.getTagline(), newValue);
            } else {
                taglineGroup.put(movie.getTagline(), 1);
            }
        }
        GroupManager<String> groupManager = new GroupManager<>(taglineGroup);
        String result = "Всего групп: " + groupManager.groupSize() + "\n";
        result += groupManager.groupTop();
        return result;
    }
}
