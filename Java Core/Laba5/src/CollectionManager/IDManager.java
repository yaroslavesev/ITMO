package CollectionManager;

import java.util.HashSet;
import java.util.Set;

public class IDManager {
    public static Set<Integer> ListID;
    static Integer current_id = 1;
    static {
        ListID = new HashSet<>();
    }

    /**
     * Добавляет в список ID новое ID
     */
    public static void AddId(Integer id){
        ListID.add(id);
    }

    /**
     * Удаляет ID из списка ID
     */
    public static void RemoveId(Integer id){
        ListID.remove(id);
    }
    /**
     * Получить свободный ID
     */
    public static Integer GetNewId(){
        current_id = 1;
        while (ListID.contains(current_id)){
            current_id ++;
        }
        return current_id;
    }
}
