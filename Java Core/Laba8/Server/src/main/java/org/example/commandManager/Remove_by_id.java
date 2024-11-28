package org.example.commandManager;

import org.example.collectionManager.CollectionManager;
import org.example.managers.UserStatusManager;
import org.example.models.Movie;
import org.example.response.Response;
import org.example.response.STATUS;

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
    public Response execution(String args, Object object, UserStatusManager userStatusManager) {
        if (!userStatusManager.getStatus()){
            return new Response(STATUS.OK, "Войдите в аккаунт!");
        }
        if (args == null || args.isEmpty()) {
            logger.warning(userStatusManager.getUser_name() + " -> " + "Неправильное количество аргументов!)");
            return new Response(STATUS.ERROR,
                    "Неправильное количество аргументов!)");
        }
        else{
            int id = Integer.parseInt(args.split("")[0]);
            if (collectionManager.getCollection().isEmpty()) {
                logger.info(userStatusManager.getUser_name() + " -> " + super.getName());
                return new Response(STATUS.OK, "Все элементы коллекции уже удалены");
            }
            boolean exist = false;
            Optional<Movie> movieToRemove = collectionManager.getCollection().stream()
                    .filter(movie -> movie.getId() == id && movie.getUser_name().equals(userStatusManager.getUser_name()))
                    .findFirst();
            if (movieToRemove.isPresent()) {
                collectionManager.remove(movieToRemove.get().getId());
                exist = true;
            }
            if (!exist){
                logger.warning(userStatusManager.getUser_name() + " -> " + "У вашего аккаунта нет доступа к данному ID");
                return new Response(STATUS.ERROR, "У вашего аккаунта нет доступа к данному ID");
            } else {
                logger.info(userStatusManager.getUser_name() + " -> " + super.getName());
                return new Response(STATUS.OK, "Элемент коллекции с данным ID успешно удалён");
            }
        }
    }
}
