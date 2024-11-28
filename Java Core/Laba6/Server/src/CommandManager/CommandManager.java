package CommandManager;
import java.util.HashMap;
import java.util.Map;
/**
 * Отвечает за выполнение связь между названием и описанием команды, названием и выполнением
 */
public class CommandManager {
    private final Map<String, String> CommandList = new HashMap<>();
    private final Map<String, Command> Commands = new HashMap<>();
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
     */
    public void addCommand(String name, Command command){
        Commands.put(name, command);
    }
    /**
     * Добавляет в Map<название команды, описание команды> новый элемент
     */
    public void addCommandList(String name, String description){
        CommandList.put(name, description);
    }

}
