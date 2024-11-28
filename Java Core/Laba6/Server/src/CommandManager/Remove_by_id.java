package CommandManager;
import Response.*;
import CollectionManager.CollectionManager;
import Models.Movie;
import Response.Response;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * Remove_by_id - удаляет элемент из коллекции по Id
 */
public class Remove_by_id extends Command{
    private final CollectionManager collectionManager;
    private final Logger logger;
    public Remove_by_id(CollectionManager collectionManager, CommandManager commandManager, Logger logger) {
        super("remove_by_id", "удалить по id");
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
        if (args == null || args.isEmpty()) {
            logger.warning("Неправильное количество аргументов!)");
            return new Response(STATUS.ERROR,
                    "Неправильное количество аргументов!)");
        }
        else{
            Integer id = Integer.parseInt(args.split("")[0]);
            if (collectionManager.getCollection().isEmpty()) {
                return new Response(STATUS.OK, "Все элементы коллекции уже удалены");
            }
            boolean exist = false;
            Optional<Movie> movieToRemove = collectionManager.getCollection().stream()
                    .filter(movie -> movie.getId() == id)
                    .findFirst();
            if (movieToRemove.isPresent()) {
                collectionManager.remove(movieToRemove.get().getId());
                exist = true;
            }
            if (!exist){
                logger.warning("Такого ID не существует");
                return new Response(STATUS.ERROR, "Такого ID не существует");
            } else {
                logger.info(super.getName());
                return new Response(STATUS.OK, "Элемент коллекции с данным ID успешно удалён");
            }
        }
    }
}
