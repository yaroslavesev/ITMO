package CommandManager;

import CollectionManager.CollectionManager;
import Console.Console;

/**
 * Save - сохраняет коллекцию
 */
public class Save extends Command{
    private final CollectionManager collectionManager;
    private final String path;
    public Save(Console console, CollectionManager collectionManager, CommandManager commandManager, String path){
        super("save", "сохранить коллекцию");
        commandManager.addCommandList(getName(), getDescription());
        this.collectionManager = collectionManager;
        this.path = path;
    }
    /**
     * Выполнение команды
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public CommandMessage execution(String args){
        if ((args == null || args.isEmpty())){
            collectionManager.saveCollection(path);
            return new CommandMessage("Коллекция успешно сохранена");
        }
        else {
            return new CommandMessage(false,
                    "Неправильное количество аргументов!)");
        }
    }
}
