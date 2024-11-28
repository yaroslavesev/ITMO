package CommandManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Отвечает за выполнение свзяь между названием и описанием команды, названием и выполнением
 */
public class CommandManager {
    private Map<String, String> CommandList = new HashMap<>();
    private Map<String, Command> Commands = new HashMap<>();
    public Map<String, String> getCommandList() {
        return CommandList;
    }

    /**
     * @return Возвращает Map<название команды, команда>
     */
    public Map<String, Command> getCommands() {
        return Commands;
    }

    /**
     * Добавляет в Map<название команды, команда> новый элемент
     * @param name
     * @param command
     */
    public void addCommand(String name, Command command){
        Commands.put(name, command);
    }
    /**
     * Добавляет в Map<название команды, описание команды> новый элемент
     * @param name
     * @param description
     */
    public void addCommandList(String name, String description){
        CommandList.put(name, description);
    }

}
