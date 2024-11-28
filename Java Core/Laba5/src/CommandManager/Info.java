package CommandManager;

import CollectionManager.CollectionManager;
import Console.Console;

/**
 * Info - выводит информацию о коллекции
 */
public class Info extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    public Info(Console console, CollectionManager collectionManager, CommandManager commandManager){
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
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
            console.println("Данные о коллекции");
            console.println("Тип - LinkedList<Movie>");
            console.println("Дата инициализации - " + collectionManager.getLastInitTime());
            console.println("Количество элементов - " + collectionManager.getCollection().size());
            console.println("Дата сохранения - " + collectionManager.getLastSaveTime());
            return new CommandMessage("Данные о коллекции успешно выведены");
        } else {
            return new CommandMessage(false,
                    "Неправильное количество аргументов!)");
        }
    }
}
