package org.example.commandManager;

import org.example.collectionManager.CollectionManager;
import org.example.collectionManager.SortManager;
import org.example.managers.UserStatusManager;
import org.example.models.Movie;
import org.example.response.Response;
import org.example.response.STATUS;

import java.util.Iterator;
import java.util.logging.Logger;

import static org.example.collectionManager.IDManager.GetNewId;

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
    public Response execution(String args, Object object, UserStatusManager userStatusManager){
        if (!userStatusManager.getStatus()){
            return new Response(STATUS.OK, "Войдите в аккаунт!");
        }
        if ((args == null || args.isEmpty())) {
            if (object.equals("")){
                logger.info(userStatusManager.getUser_name() + " -> " + "Отправка запроса на создание объекта");
                return new Response(STATUS.NEED_OBJECT, "* Создание нового Movie:", GetNewId());
            } else {
                Movie a = (Movie) object;
                if (a.validate()) {
                    a.setUser_name(userStatusManager.getUser_name());
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
                            if (b.getUser_name().equals(userStatusManager.getUser_name())){
                                iterator.remove();
                                count ++;
                            }
                        }
                    }
                    logger.info(userStatusManager.getUser_name() + " -> " + super.getName());
                    return new Response(STATUS.OK, "Успешно удалено " + (count - 1) + " элементов");
                } else {
                    logger.warning(userStatusManager.getUser_name() + " -> " + "Поля movie не валидны! Movie не создан!");
                    return new Response(STATUS.ERROR, "Поля movie не валидны! Movie не создан!");
                }
            }
        }
        else{
            logger.warning(userStatusManager.getUser_name() + " -> " + "Неправильное количество аргументов!)");
            return new Response(STATUS.ERROR,
                    "Неправильное количество аргументов!)");
        }
    }
}
