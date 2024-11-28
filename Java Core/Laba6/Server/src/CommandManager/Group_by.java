package CommandManager;
import CollectionManager.CollectionManager;
import CommandManager.Groups.*;
import Response.*;
import java.util.HashMap;
import java.util.logging.Logger;

public class Group_by extends Command {
    private final Logger logger;
    private final HashMap<String, String> groups = new HashMap<>();
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    public CollectionManager getCollectionManager(){
        return this.collectionManager;
    }
    public CommandManager getCommand(){
        return this.commandManager;
    }
    public Group_by(CollectionManager collectionManager, CommandManager commandManager, Logger logger) {
        super("group_by", "группировка объектов по переданному полю и вывод топ-3 объектов по этому полю");
        commandManager.addCommandList(getName(), getDescription());
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
        this.logger = logger;
    }
    /**
     * Выполнение команды
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public Response execution(String args, Object object) {
        groups.put("name", new Group_by_name(collectionManager).execution());
        groups.put("coordinates", new Group_by_coordinates(collectionManager).execution());
        groups.put("creationDate", new Group_by_creationDate(collectionManager).execution());
        groups.put("oscarsCount", new Group_by_oscarsCount(collectionManager).execution());
        groups.put("budget", new Group_by_budget(collectionManager).execution());
        groups.put("tagline", new Group_by_tagline(collectionManager).execution());
        groups.put("genre", new Group_by_genre(collectionManager).execution());
        groups.put("screenWriter", new Group_by_screenWriter(collectionManager).execution());
        if (args.isEmpty()) {
            logger.warning("Некорректный аргумент");
            return new Response(STATUS.ERROR, "Некорректный аргумент");
        } else {
            if (!groups.containsKey(args)){
                logger.warning("Такого поля не существует, группировка невозможна");
                return new Response(STATUS.ERROR, "Такого поля не существует, группировка невозможна");
            } else {
                logger.warning(super.getName());
                return new Response(STATUS.OK, groups.get(args));
            }
        }
    }
}