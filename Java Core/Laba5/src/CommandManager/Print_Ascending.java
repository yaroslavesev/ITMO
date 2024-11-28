package CommandManager;

import CollectionManager.CollectionManager;
import Console.Console;
import Models.Movie;
import CollectionManager.SortManager;
import java.util.Collections;

/**
 * Print_Ascending - выводит элементы коллекции в порядке возрастания
 */
public class Print_Ascending extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    public Print_Ascending(Console console, CollectionManager collectionManager, CommandManager commandManager){
        super("print_ascending", "вывести элементы коллекции в порядке возрастания");
        commandManager.addCommandList(getName(), getDescription());
        this.collectionManager = collectionManager;
        this.console = console;
        this.commandManager = commandManager;
    }
    /**
     * Выполнение команды
     * @param args
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public CommandMessage execution(String args){
        if ((args == null || args.isEmpty())){
            if (!collectionManager.getCollection().isEmpty()) {
                SortManager sortManager = new SortManager();
                Collections.sort(collectionManager.getCollection(), sortManager.SortCollection());
                console.println("Отсортированные элементы коллекции ");
                for (Movie movie : collectionManager.getCollection()){
                    console.println(movie);
                }
                return new CommandMessage("Элемент коллекции успешно выведены в порядке возрастания");
            }
            else {return new CommandMessage(false, "Коллекция пустая(");}
        }
        else {
            return new CommandMessage(false,
                    "Неправильное количество аргументов!)");
        }
    }
}
