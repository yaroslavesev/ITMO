package CommandManager;
import Response.*;
import CollectionManager.CollectionManager;
import Models.Movie;
import Response.Response;

import java.util.logging.Logger;

import static CollectionManager.IDManager.GetNewId;

/**
 * Add - добавляет элемент в коллекцию
 */
public class Add extends Command {
    private final CollectionManager collectionManager;
    private final Logger logger;
    public Add(CollectionManager collectionManager, CommandManager commandManager, Logger logger) {
        super("add", " добавить новый элемент в коллекцию");
        commandManager.addCommandList(getName(), getDescription());
        this.collectionManager = collectionManager;
        this.logger = logger;
    }
    /**
     * Выполнение команды
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public Response execution(String args, Object object) {
        if ((args == null || args.isEmpty())) {
            if (object.equals("")){
                logger.info(super.getName());
                return new Response(STATUS.NEED_OBJECT, "* Создание нового Movie:", GetNewId());
            } else {
                Movie a = (Movie) object;
                if (a.validate()) {
                    collectionManager.add(a);
                    logger.info(super.getName());
                    return new Response(STATUS.OK, "Movie успешно добавлен!");
                } else
                    return new Response(STATUS.ERROR, "Поля movie не валидны! Movie не создан!");
            }
        }
        else{
            logger.warning("Неправильное количество аргументов!)");
            return new Response(STATUS.ERROR,
                    "Неправильное количество аргументов!)");
        }
    }
}
