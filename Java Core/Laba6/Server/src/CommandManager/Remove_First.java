package CommandManager;

import CollectionManager.CollectionManager;
import Response.*;

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
    public Response execution(String args, Object object){
        if ((args == null || args.isEmpty())){
            if (!collectionManager.getCollection().isEmpty()) {
                collectionManager.remove(collectionManager.getCollection().getFirst().getId());
                logger.info(super.getName());
                return new Response(STATUS.OK, "Первый элемент коллекции успешно удалён");
            }
            else {
                logger.info("Коллекция пустая");
                return new Response(STATUS.OK, "Коллекция пустая(");
            }
        }
        else {
            logger.warning("Неправильное количество аргументов!)");
            return new Response(STATUS.ERROR,
                    "Неправильное количество аргументов!)");
        }
    }
}
