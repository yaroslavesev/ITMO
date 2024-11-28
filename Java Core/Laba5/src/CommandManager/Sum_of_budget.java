package CommandManager;

import CollectionManager.CollectionManager;
import Console.Console;
import Models.Movie;

/**
 * Sum_of_budget - команда, которая сумму всех бюджетов
 */
public class Sum_of_budget extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Sum_of_budget(Console console, CollectionManager collectionManager, CommandManager commandManager){
        super("sum_of_budget", "вывести сумму значений поля budget для всех элементов коллекции");
        commandManager.addCommandList(getName(), getDescription());
        this.collectionManager = collectionManager;
        this.console = console;
    }
    /**
     * Выполнение команды
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public CommandMessage execution(String args){
        if ((args == null || args.isEmpty())){
            if (!collectionManager.getCollection().isEmpty()) {
                double sum = 0;
                for (Movie movie: collectionManager.getCollection()){
                     sum += movie.getBudget();
                }
                console.println("Сумма бюджетов всех элементов коллекции = " + sum);
                return new CommandMessage("Сумма бюджетов успешно выведена");
            }
            else {return new CommandMessage(false, "Коллекция пустая(");}
        }
        else {
            return new CommandMessage(false,
                    "Неправильное количество аргументов!)");
        }
    }
}
