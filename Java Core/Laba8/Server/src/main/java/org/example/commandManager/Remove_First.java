package org.example.commandManager;

import org.example.collectionManager.CollectionManager;
import org.example.managers.UserStatusManager;
import org.example.response.Response;
import org.example.response.STATUS;

import java.util.logging.Logger;

/**
 * Remove_First - удаляет первый элемент из коллекции
 */
public class Remove_First extends Command{
    private final CollectionManager collectionManager;
    private final Logger logger;
    public Remove_First(CollectionManager collectionManager, CommandManager commandManager, Logger logger){
        super("remove_first", "удалить первый элемент из коллекции");
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
        if ((args == null || args.isEmpty())){
            logger.info(userStatusManager.getUser_name() + " -> " + super.getName());
            if (!collectionManager.getCollection().isEmpty()) {
                if (collectionManager.getCollection().getFirst().getUser_name().equals(userStatusManager.getUser_name())){
                    collectionManager.remove(collectionManager.getCollection().getFirst().getId());
                    return new Response(STATUS.OK, "Первый элемент коллекции успешно удалён");
                } else {
                    return new Response(STATUS.OK, "У вас нет доступа к элементу этой коллекции");
                }
            }
            else {
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
