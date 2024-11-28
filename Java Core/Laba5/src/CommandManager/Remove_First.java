package CommandManager;

import CollectionManager.CollectionManager;
import Console.Console;
import static CollectionManager.IDManager.RemoveId;

/**
 * Remove_First - удаляет первый элемент из коллекции
 */
public class Remove_First extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    public Remove_First(Console console, CollectionManager collectionManager, CommandManager commandManager){
        super("remove_first", "удалить первый элемент из коллекции");
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
                RemoveId(collectionManager.getCollection().getFirst().getId());
                collectionManager.getCollection().removeFirst();
                return new CommandMessage("Первый элемент коллекции успешно удалён");
            }
            else {return new CommandMessage(false, "Коллекция пустая(");}
        }
        else {
            return new CommandMessage(false,
                    "Неправильное количество аргументов!)");
        }
    }
}
