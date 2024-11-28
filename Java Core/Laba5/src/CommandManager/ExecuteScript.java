package CommandManager;

import CollectionManager.CollectionManager;
import Console.Console;

/**
 * ExecuteScript - считывает и исполняет скрипт из файла
 */
public class ExecuteScript extends Command{
    private final Console console;
    private final CommandManager commandManager;
    private final CollectionManager collectionManager;
    public ExecuteScript(Console console, CollectionManager collectionManager, CommandManager commandManager){
        super("execute_script", " считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        this.console = console;
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
    }
    /**
     * Выполнение команды
     * @param args
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    public CommandMessage execution(String args){
        return new CommandMessage("execution");
    }
}
