package org.example.commandManager.groups;

import org.example.collectionManager.CollectionManager;
import org.example.collectionManager.GroupManager;
import org.example.models.Movie;

import java.util.HashMap;

public class Group_by_budget{
    private final CollectionManager collectionManager;
    public Group_by_budget(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
    /**
     * Выполнение команды
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    public String execution(){
        HashMap<Double, Integer> budgetGroup = new HashMap<>();
        for(Movie movie: collectionManager.getCollection()){
            if (budgetGroup.containsKey(movie.getBudget())){
                Integer newValue = budgetGroup.get(movie.getBudget()) + 1;
                budgetGroup.replace(movie.getBudget(), newValue);
            } else {
                budgetGroup.put(movie.getBudget(), 1);
            }
        }
        GroupManager<Double> groupManager = new GroupManager<>(budgetGroup);
        String result = "Всего групп: " + groupManager.groupSize() + "\n";
        result += groupManager.groupTop();
        return result;
    }
}
