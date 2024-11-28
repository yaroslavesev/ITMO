package CollectionManager;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;
import static CollectionManager.KeyToValue.getKeyFromValue;
public class GroupManager<T>{
    private final HashMap <T, Integer> collection;
    public GroupManager (HashMap <T, Integer> collection){
        this.collection = collection;
    }
    public int groupSize(){
        return this.collection.size();
    }
    public String groupTop(){
        Vector<Integer> valuesList = new Vector<>(collection.values());
        valuesList.sort(Collections.reverseOrder());
        String result = "Топ-1 группа: " + valuesList.get(0) + "\n" +  getKeyFromValue(collection, valuesList.get(0));
        if (valuesList.size() >= 3 && getKeyFromValue(collection, valuesList.get(1)) != getKeyFromValue(collection, valuesList.get(2))) {
            result += "\n" + "Топ-2 группа: " + valuesList.get(1) + "\n" + getKeyFromValue(collection, valuesList.get(1)) + "\n" + "Топ-3 группа: " + valuesList.get(2) + "\n" + getKeyFromValue(collection, valuesList.get(2));
        }
        return result;
    }
}
