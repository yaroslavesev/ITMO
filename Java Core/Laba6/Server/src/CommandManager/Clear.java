package CommandManager;
import CollectionManager.CollectionManager;
import Response.*;

import java.util.logging.Logger;

/**
 * Clear - очищает коллекцию
 */
public class Clear extends Command{
    private final CollectionManager collectionManager;
    private final Logger logger;
    public Clear(CollectionManager collectionManager, CommandManager commandManager, Logger logger){
        super("clear", "очистить коллекцию");
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
        if ((args == null || args.isEmpty())){
            logger.info(super.getName());
            if (!collectionManager.getCollection().isEmpty()) {
                    collectionManager.getCollection().clear();
                return new Response(STATUS.OK,"Коллекция успешно очищена");
            }
            else {return new Response(STATUS.OK, "Коллекция уже очищена(");}
        }
        else {
            return new Response(STATUS.ERROR,
                    "Неправильное количество аргументов!)");
        }
    }
}
