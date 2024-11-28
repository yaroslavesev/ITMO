package CommandManager;

import CollectionManager.CollectionManager;
import CollectionManager.SortManager;
import Console.Console;
import Console.Enter;
import Models.Movie;

import static CollectionManager.IDManager.GetNewId;

/**
 * Add_if_max - добавляет элемент в коллекцию, если он больше чем наибольший элемент коллекции
 */
public class Add_if_max extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    public Add_if_max(Console console, CollectionManager collectionManager, CommandManager commandManager){
        super("add_if_max", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
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
        try {
            if ((args == null || args.isEmpty())) {
                console.println("* Создание потенциально нового Movie:");
                Movie a = Enter.enterMovie(console, GetNewId());
                if (a != null && a.validate()) {
                    collectionManager.add(a);
                    SortManager sortManager = new SortManager();
                    collectionManager.getCollection().sort(sortManager.SortCollection());
                    if (collectionManager.getCollection().getLast().equals(a)){
                        return new CommandMessage("Элемент успешно добавлен в коллекцию");
                    } else {
                        collectionManager.remove(a.getId());
                        return new CommandMessage(false, "Элемент не подошёл под условия");
                    }
                } else
                    return new CommandMessage(false, "Поля movie не валидны! Movie не создан!");
            }
            else{
                return new CommandMessage(false,
                        "Неправильное количество аргументов!)");
            }
        } catch (Enter.EnterBreak e) {
            throw new RuntimeException(e);
        }
    }
}
