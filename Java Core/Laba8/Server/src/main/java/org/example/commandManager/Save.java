package org.example.commandManager;

import org.example.collectionManager.CollectionManager;
import org.example.managers.UserStatusManager;
import org.example.response.Response;
import org.example.response.STATUS;

import java.util.logging.Logger;

/**
 * Save - сохраняет коллекцию
 */
public class Save extends Command{
    private final CollectionManager collectionManager;
    private final Logger logger;
    public Save(CollectionManager collectionManager, CommandManager commandManager, Logger logger){
        super("save", "сохранить коллекцию");
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
            if (!userStatusManager.getStatus()){
                return new Response(STATUS.ERROR, "Вы не вошли в аккаунт");
            }
            collectionManager.saveCollection(userStatusManager.getUser_name());
            logger.info(super.getName());
            return new Response(STATUS.OK,"Коллекция успешно сохранена");
        } else {
            logger.warning("Ошибка сохранения");
            return new Response(STATUS.ERROR, "Неправильное количество аргументов!)");
        }
    }
}
