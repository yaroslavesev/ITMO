package CommandManager;
import CollectionManager.CollectionManager;
import Models.Movie;
import Response.*;

import java.util.logging.Logger;

/**
 * Sum_of_budget - команда, которая сумму всех бюджетов
 */
public class Sum_of_budget extends Command {
    private final CollectionManager collectionManager;
    private final Logger logger;
    public Sum_of_budget(CollectionManager collectionManager, CommandManager commandManager, Logger logger){
        super("sum_of_budget", "вывести сумму значений поля budget для всех элементов коллекции");
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
                double sum = collectionManager.getCollection().stream()
                        .mapToDouble(Movie::getBudget)
                        .sum();
                System.out.println(super.getName());
                return new Response(STATUS.OK, "Сумма бюджетов всех элементов коллекции = " + sum);
            }
            else {System.out.println(super.getName());
                return new Response(STATUS.OK, "Коллекция пустая(");}
        }
        else {
            logger.warning("Неправильное количество аргументов!)");
            return new Response(STATUS.ERROR,
                    "Неправильное количество аргументов!)");
        }
    }
}
