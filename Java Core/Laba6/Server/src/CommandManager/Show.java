package CommandManager;
import Response.*;
import CollectionManager.CollectionManager;
import Models.Movie;
import Response.Response;

import java.util.logging.Logger;

/**
 * Show - выводит все элементы коллекции
 */
public class Show extends Command{
    private final CollectionManager collectionManager;
    private final Logger logger;
    public Show(CollectionManager collectionManager, CommandManager commandManager, Logger logger){
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
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
        StringBuilder stringBuilder = new StringBuilder();
        if ((args == null || args.isEmpty())){
            if (!collectionManager.getCollection().isEmpty()) {
                stringBuilder.append("Элементы коллекции: \n");
                collectionManager.getCollection().forEach(movie -> stringBuilder.append(movie).append("\n"));
                logger.info(super.getName());
                return new Response(STATUS.OK,"Коллекция успешно выведена", stringBuilder.toString());
            }

            else {
                System.out.println(super.getName());
                return new Response(STATUS.OK, "Коллекция пустая(");
            }
        }
        else {
            return new Response(STATUS.ERROR,
                    "Неправильное количество аргументов!)");
        }
    }
}
