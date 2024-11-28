package CommandManager;

import CollectionManager.CollectionManager;
import CollectionManager.SortManager;
import Models.Movie;
import Response.*;
import java.util.logging.Logger;

/**
 * Print_Ascending - выводит элементы коллекции в порядке возрастания
 */
public class Print_Ascending extends Command{
    private final Logger logger;
    private final CollectionManager collectionManager;
    public Print_Ascending(CollectionManager collectionManager, CommandManager commandManager, Logger logger){
        super("print_ascending", "вывести элементы коллекции в порядке возрастания");
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
            logger.info(super.getName());
            if (!collectionManager.getCollection().isEmpty()) {
                SortManager sortManager = new SortManager();
                collectionManager.getCollection().sort(sortManager.SortCollection());
                stringBuilder.append("Отсортированные элементы коллекции ").append("\n");
                collectionManager.getCollection().forEach(movie -> stringBuilder.append(movie).append("\n"));
                return new Response(STATUS.OK, "Элемент коллекции успешно выведены в порядке возрастания\n" + stringBuilder);
            }
            else {return new Response(STATUS.OK, "Коллекция пустая(");}
        }
        else {
            logger.info("Неправильное количество аргументов!)");
            return new Response(STATUS.ERROR,
                    "Неправильное количество аргументов!)");
        }
    }
}
