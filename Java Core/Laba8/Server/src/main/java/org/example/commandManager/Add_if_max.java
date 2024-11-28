package org.example.commandManager;
import org.example.collectionManager.CollectionManager;
import org.example.collectionManager.SortManager;
import org.example.managers.UserStatusManager;
import org.example.models.Movie;
import org.example.response.Response;
import org.example.response.STATUS;

import java.util.logging.Logger;

import static org.example.collectionManager.IDManager.GetNewId;

/**
 * Add_if_max - добавляет элемент в коллекцию, если он больше чем наибольший элемент коллекции
 */
public class Add_if_max extends Command{
    private final CollectionManager collectionManager;
    private final Logger logger;

    public Add_if_max(CollectionManager collectionManager, CommandManager commandManager, Logger logger){
        super("add_if_max", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
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
        if ((args == null || args.isEmpty())) {
            if (object.equals("")){
                logger.info(userStatusManager.getUser_name() + " -> " + "Отправка запроса на создание объекта");
                return new Response(STATUS.NEED_OBJECT, "* Создание нового Movie:", GetNewId());
            } else {
                Movie a = (Movie) object;
                if (a.validate()) {
                    a.setUser_name(userStatusManager.getUser_name());
                    collectionManager.add(a);
                    SortManager sortManager = new SortManager();
                    collectionManager.getCollection().sort(sortManager.SortCollection());
                    if (collectionManager.getCollection().getLast().equals(a)){
                        return new Response(STATUS.OK,"Элемент успешно добавлен в коллекцию");
                    } else {
                        collectionManager.remove(a.getId());
                        return new Response(STATUS.OK, "Элемент не подошёл под условия");
                    }
                }
                else {
                    logger.info(userStatusManager.getUser_name() + " -> " + super.getName());
                    return new Response(STATUS.ERROR, "Поля movie не валидны! Movie не создан!");
                }
            }
        }
        else{
            logger.warning(userStatusManager.getUser_name() + " -> " + "Неправильное количество аргументов! ");
            return new Response(STATUS.ERROR,
                    "Неправильное количество аргументов!)");
        }
    }
}
