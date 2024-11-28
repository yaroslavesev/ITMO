package CommandManager;

import CollectionManager.CollectionManager;
import Console.Console;
import Models.Movie;

/**
 * Show - выводит все элементы коллекции
 */
public class Show extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    public Show(Console console, CollectionManager collectionManager, CommandManager commandManager){
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
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
                console.println("Элементы коллекции: ");
                for (Movie movie: collectionManager.getCollection()){
                    console.println(movie);
                }
                return new CommandMessage("Коллекция успешно выведена");
            }
            else {return new CommandMessage(false, "Коллекция пустая(");}
        }
        else {
            return new CommandMessage(false,
                    "Неправильное количество аргументов!)");
        }
    }
}
