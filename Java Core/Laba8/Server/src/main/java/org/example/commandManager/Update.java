package org.example.commandManager;

import org.example.collectionManager.CollectionManager;
import org.example.managers.UserStatusManager;
import org.example.models.Movie;
import org.example.response.Response;
import org.example.response.STATUS;

import java.util.logging.Logger;

/**
 * Команда Update - обновляет значение элемента коллекции, id которого равен заданному.
 */
public class Update extends Command{
    private final CollectionManager collectionManager;
    private final Logger logger;

    public Update(CollectionManager collectionManager, CommandManager commandManager, Logger logger) {
        super("update", "обновить значение элемента коллекции, id которого равен заданному");
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
            logger.warning(userStatusManager.getUser_name() + " -> " + "Неправильное количество аргументов!");
            return new Response(STATUS.ERROR,
                    "Неправильное количество аргументов!");
        } else {
            int id = Integer.parseInt(args.split(" ")[0]);
            boolean exist = false;
            for (Movie movie : collectionManager.getCollection()) {
                if (movie.getId() == id && movie.getUser_name().equals(userStatusManager.getUser_name())) {
                    exist = true;
                    break;
                }
            }
            if (exist){
                if (object.equals("")){
                    logger.info(userStatusManager.getUser_name() + " -> " + "Отправка запроса на создание объекта");
                    return new Response(STATUS.NEED_OBJECT, "* Создание нового Movie:", id);
                } else {
                    if (collectionManager.getCollection().isEmpty()) {
                        System.out.println(super.getName());
                        logger.info(super.getName());
                        return new Response(STATUS.OK, "Коллекция пустая");
                    }
                }
                Movie a = (Movie) object;
                if (a.validate()) {
                    collectionManager.remove(id);
                    collectionManager.add(a);
                    a.setUser_name(userStatusManager.getUser_name());
                    logger.info(super.getName());
                    return new Response(STATUS.OK, "Movie успешно обновлен!");
                } else {
                    logger.warning(userStatusManager.getUser_name() + " -> " + "Поля movie не валидны! Movie не создан!");
                    return new Response(STATUS.ERROR, "Поля movie не валидны! Movie не создан!");
                }
            } else {
                logger.info(userStatusManager.getUser_name() + " -> " + "На вашем аккаунте не существует такого ID");
                return new Response(STATUS.ERROR, "На вашем аккаунте не существует такого ID");
            }
        }
    }
}
