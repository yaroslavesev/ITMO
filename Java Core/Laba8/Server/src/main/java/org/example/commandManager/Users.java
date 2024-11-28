package org.example.commandManager;

import org.example.collectionManager.CollectionManager;
import org.example.managers.UserStatusManager;
import org.example.response.Response;
import org.example.response.STATUS;

import java.util.logging.Logger;

/**
 * Users - выводит всех пользователей
 */
public class Users extends Command{
    private final CollectionManager collectionManager;
    private final Logger logger;
    public Users(CollectionManager collectionManager, CommandManager commandManager, Logger logger){
        super("users", "вывести всех пользователей");
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
        StringBuilder stringBuilder = new StringBuilder();
        if ((args == null || args.isEmpty())){
            logger.info(super.getName());
            if (collectionManager.getUsers().isEmpty()) return new Response(STATUS.OK, "There are no users yet");
            for (String userName: collectionManager.getUsers()){
                stringBuilder.append(userName).append("\n");
            }
            return new Response(STATUS.OK, "Users:\n", stringBuilder);
        }
        else {
            logger.warning("Неправильное количество аргументов!)");
            return new Response(STATUS.ERROR, "Неправильное количество аргументов!)");
        }
    }
}
