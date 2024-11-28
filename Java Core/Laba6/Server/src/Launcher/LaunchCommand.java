package Launcher;
import CommandManager.CommandManager;
import Response.*;
public class LaunchCommand {
    private final CommandManager commandManager;
    public LaunchCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
    public Response commandParser(String user_input, Object object) {
        String[] parsedResult = (user_input.trim() + " ").split(" ", 2);
        String commandName = parsedResult[0].trim();
        String commandArgs = parsedResult[1].trim();
        return doCommand(commandName, commandArgs, object);
    }
    public Response doCommand(String commandName, String commandArgs, Object object) {
        if (commandName.isEmpty()) return new Response(STATUS.ERROR, "Вы ничего не ввели");
        var command = commandManager.getCommands().get(commandName);
        if (command == null) return new Response(STATUS.ERROR, "Такой команды не существует");
        return command.execution(commandArgs, object);
    }
}