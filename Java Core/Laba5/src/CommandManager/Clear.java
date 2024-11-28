package CommandManager;

import CollectionManager.CollectionManager;
import Console.Console;

/**
 * Clear - очищает коллекцию
 */
public class Clear extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    public Clear(Console console, CollectionManager collectionManager, CommandManager commandManager){
        super("clear", "очистить коллекцию");
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
                    collectionManager.getCollection().clear();
                return new CommandMessage("Коллекция успешно очищена");
            }
            else {return new CommandMessage(false, "Коллекция уже очищена(");}
        }
        else {
            return new CommandMessage(false,
                    "Неправильное количество аргументов!)");
        }
    }
}
