package org.example.commandManager;

import org.example.collectionManager.CollectionManager;
import org.example.managers.UserStatusManager;
import org.example.response.Response;
import org.example.response.STATUS;

import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Help - выводит справку по достпуным командам
 */
public class Help extends Command{
    private final CommandManager commandManager;
    private final Logger logger;
    public Help(CollectionManager collectionManager, CommandManager commandManager, Logger logger){
        super("help", "вывести справку по доступным командам");
        commandManager.addCommandList(getName(), getDescription());
        this.commandManager = commandManager;
        this.logger = logger;
    }
    /**
     * Выполнение команды
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public Response execution(String args, Object object, UserStatusManager userStatusManager) {
        if (args == null || args.isEmpty()) {
            String commandInfo = commandManager.getCommandList()
                    .entrySet()
                    .stream()
                    .map(entry -> entry.getKey() + " : " + entry.getValue())
                    .collect(Collectors.joining("\n", "Информация о командах\n", ""));

            logger.info(userStatusManager.getUser_name() + " -> " + super.getName());
            return new Response(STATUS.OK, commandInfo);
        } else {
            logger.warning("Неправильное количество аргументов!");
            return new Response(STATUS.ERROR, "Неправильное количество аргументов!");
        }
    }

}
