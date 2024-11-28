package org.example.launcher;
import org.example.commandManager.CommandManager;
import org.example.managers.UserStatusManager;
import org.example.response.Response;
import org.example.response.STATUS;

public class LaunchCommand {
    private final CommandManager commandManager;
    public LaunchCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
    public Response commandParser(String user_input, Object object, UserStatusManager userStatusManager) {
        String[] parsedResult = (user_input.trim() + " ").split(" ", 2);
        String commandName = parsedResult[0].trim();
        String commandArgs = parsedResult[1].trim();
        return doCommand(commandName, commandArgs, object, userStatusManager);
    }
    public Response doCommand(String commandName, String commandArgs, Object object, UserStatusManager userStatusManager) {
        if (commandName.isEmpty()) return new Response(STATUS.ERROR, "Вы ничего не ввели");
        var command = commandManager.getCommands().get(commandName);
        if (command == null) return new Response(STATUS.ERROR, "Такой команды не существует");
        return command.execution(commandArgs, object, userStatusManager);
    }
}