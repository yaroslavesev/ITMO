package CommandManager;

import CollectionManager.CollectionManager;
import Console.Console;
import Models.Movie;

/**
 * Remove_by_id - удаляет элемент из коллекции по Id
 */
public class Remove_by_id extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;

    public Remove_by_id(Console console, CollectionManager collectionManager, CommandManager commandManager) {
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
    public CommandMessage execution(String args) {
        if (args == null || args.isEmpty()) {
            return new CommandMessage(false,
                    "Неправильное количество аргументов!)");
        }
        else{
            Integer id = Integer.parseInt(args.split("")[0]);
            if (collectionManager.getCollection().isEmpty()) {return new CommandMessage(false, "Все элементы коллекции уже удалены");}
            boolean exist = false;
            for (Movie movie: collectionManager.getCollection()){
                if (movie.getId() == id){
                    collectionManager.remove(id);
                    exist = true;
                    break;
                }
            }
            if (!exist){
                return new CommandMessage(false, "Такого ID не существует");
            } else {
                return new CommandMessage("Элемент коллекции с данным ID успешно удалён");
            }
        }
    }
}
