package CommandManager;

import CollectionManager.CollectionManager;
import CollectionManager.SortManager;
import Console.Console;

import java.util.Collections;

/**
 * Min_by_name - вывод элемент, поля name которого минимально
 */
public class Min_by_name extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    public Min_by_name(Console console, CollectionManager collectionManager, CommandManager commandManager){
        super("min_by_name", "вывести любой объект из коллекции, значение поля name которого является минимальным");
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
                Collections.sort(collectionManager.getCollection(), sortManager.SortCollectionByName());
                console.println("Минимальный по имени элемент коллекции");
                console.println(collectionManager.getCollection().getFirst());
                Collections.sort(collectionManager.getCollection(), sortManager.SortCollection());
                return new CommandMessage("Элемент коллекции с минимальным именем успешно выведен");
            }
            else {return new CommandMessage(false, "Коллекция пустая(");}
        }
        else {
            return new CommandMessage(false,
                    "Неправильное количество аргументов!)");
        }
    }
}
