package CommandManager;

import CollectionManager.CollectionManager;
import Console.Console;

/**
 * Exit - закрывает программу без сохранения
 */
public class Exit extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    public Exit(Console console, CollectionManager collectionManager, CommandManager commandManager){
        super("exit", "завершить программу (без сохранения в файл)");
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
            System.exit(1);
            return new CommandMessage("Программа успешно завершена без сохранения в файл");
        }
        else {
            return new CommandMessage(false,
                    "Неправильное количество аргументов!)");
        }
    }
}
