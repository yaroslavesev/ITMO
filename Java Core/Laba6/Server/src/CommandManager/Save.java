package CommandManager;

import CollectionManager.CollectionManager;
import Response.*;

import java.util.logging.Logger;

/**
 * Save - сохраняет коллекцию
 */
public class Save extends Command{
    private final CollectionManager collectionManager;
    private final String path;
    private final Logger logger;
    public Save(CollectionManager collectionManager, CommandManager commandManager, String path, Logger logger){
        super("save", "сохранить коллекцию");
        commandManager.addCommandList(getName(), getDescription());
        this.collectionManager = collectionManager;
        this.path = path;
        this.logger = logger;
    }
    /**
     * Выполнение команды
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public Response execution(String args, Object object){
        if ((args == null || args.isEmpty())){
            collectionManager.saveCollection(path);
            logger.info(super.getName());
            return new Response(STATUS.OK,"Коллекция успешно сохранена");
        }
        else {
            logger.warning("Ошибка сохранения");
            return new Response(STATUS.ERROR,
                    "Неправильное количество аргументов!)");
        }
    }
}
