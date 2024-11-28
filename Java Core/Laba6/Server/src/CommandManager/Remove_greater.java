package CommandManager;

import CollectionManager.CollectionManager;
import CollectionManager.SortManager;
import Models.Movie;
import Response.*;
import java.util.Iterator;
import java.util.logging.Logger;

import static CollectionManager.IDManager.GetNewId;

/**
 * Remove_greater - удаляет из коллекции все элементы, превышающие заданный
 */
public class Remove_greater extends Command{
    private final CollectionManager collectionManager;
    private final Logger logger;
    public Remove_greater(CollectionManager collectionManager, CommandManager commandManager, Logger logger){
        super("remove_greater", " удалить из коллекции все элементы, превышающие заданный");
        commandManager.addCommandList(getName(), getDescription());
        this.collectionManager = collectionManager;
        this.logger = logger;
    }
    /**
     * Выполнение команды
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public Response execution(String args, Object object){
        if ((args == null || args.isEmpty())) {
            if (object.equals("")){
                return new Response(STATUS.NEED_OBJECT, "* Создание нового Movie:", GetNewId());
            } else {
                Movie a = (Movie) object;
                if (a.validate()) {
                    collectionManager.add(a);
                    SortManager sortManager = new SortManager();
                    collectionManager.getCollection().sort(sortManager.SortCollection());
                    boolean checker = false;
                    int count = 0;
                    Iterator<Movie> iterator = collectionManager.getCollection().iterator();
                    while (iterator.hasNext()) {
                        Movie b = iterator.next();
                        if (b.equals(a)) {
                            checker = true;
                        }
                        if (checker){
                            iterator.remove();
                            count ++;
                        }
                    }
                    logger.info(super.getName());
                    return new Response(STATUS.OK, "Успешно удалено " + (count - 1) + " элементов");
                } else {
                    logger.warning("Поля movie не валидны! Movie не создан!");
                    return new Response(STATUS.ERROR, "Поля movie не валидны! Movie не создан!");
                }
            }
        }
        else{
            logger.warning("Неправильное количество аргументов!)");
            return new Response(STATUS.ERROR,
                    "Неправильное количество аргументов!)");
        }
    }
}
