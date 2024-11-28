package CommandManager;

import CollectionManager.CollectionManager;
import Console.Console;

import java.util.Map;

/**
 * Help - выводит справку по достпуным командам
 */
public class Help extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    public Help(Console console, CollectionManager collectionManager, CommandManager commandManager){
        super("help", "вывести справку по доступным командам");
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
            console.println("Информация о командах");
            for (Map.Entry<String, String> entry : commandManager.getCommandList().entrySet()){
                console.println(entry.getKey() + " : " + entry.getValue());
            }

            return new CommandMessage("Информация о командах выведена успешно");
        }
        else {
            return new CommandMessage(false,
                    "Неправильное количество аргументов!)");
        }
    }
}
