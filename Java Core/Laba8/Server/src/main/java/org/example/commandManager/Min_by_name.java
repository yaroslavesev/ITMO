package org.example.commandManager;
import org.example.collectionManager.CollectionManager;
import org.example.collectionManager.SortManager;
import org.example.managers.UserStatusManager;
import org.example.response.Response;
import org.example.response.STATUS;

import java.util.logging.Logger;

/**
 * Min_by_name - вывод элемент, поля name которого минимально
 */
public class Min_by_name extends Command{
    private final CollectionManager collectionManager;
    private final Logger logger;
    public Min_by_name(CollectionManager collectionManager, CommandManager commandManager, Logger logger){
        super("min_by_name", "вывести любой объект из коллекции, значение поля name которого является минимальным");
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
        if ((args == null || args.isEmpty())){
            if (!collectionManager.getCollection().isEmpty()) {
                SortManager sortManager = new SortManager();
                collectionManager.getCollection().sort(sortManager.SortCollectionByName());
                String stringBuilder = "Минимальный по имени элемент коллекции" + "\n" +
                        collectionManager.getCollection().getFirst();
                collectionManager.getCollection().sort(sortManager.SortCollection());
                logger.info(userStatusManager.getUser_name() + " -> " + super.getName());
                return new Response(STATUS.OK, stringBuilder);
            }
            else {
                logger.info(userStatusManager.getUser_name() + " -> " + super.getName());
                return new Response(STATUS.OK, "Коллекция пустая(");
            }
        }
        else {
            logger.warning(userStatusManager.getUser_name() + " -> " + "Неправильное количество аргументов!)");
            return new Response(STATUS.ERROR,
                    "Неправильное количество аргументов!)");
        }
    }
}
