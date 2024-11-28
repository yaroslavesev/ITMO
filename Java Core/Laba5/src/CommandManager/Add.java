package CommandManager;

import CollectionManager.CollectionManager;
import Console.Console;
import Console.Enter;
import Models.Movie;

import static CollectionManager.IDManager.GetNewId;

/**
 * Add - добавляет элемент в коллекцию
 */
public class Add extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;

    public Add(Console console, CollectionManager collectionManager, CommandManager commandManager) {
        super("add", " добавить новый элемент в коллекцию");
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
    public CommandMessage execution(String args) {
        try {
            if ((args == null || args.isEmpty())) {
                console.println("* Создание нового Movie:");
                Movie a = Enter.enterMovie(console, GetNewId());
                if (a != null && a.validate()) {
                    collectionManager.add(a);
                    return new CommandMessage("Movie успешно добавлен!");
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
