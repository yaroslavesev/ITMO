
package CommandManager;

import CollectionManager.CollectionManager;
import CommandManager.Groups.*;
import Console.Console;

import java.util.HashMap;
import java.util.NoSuchElementException;
public class Group_by extends Command {

    // TODO: класс который реализует группировку по переданному пол.
    // returns - общее количество групп и топ 3 группы по количенству объектов в них
    private final HashMap<String, String> groups = new HashMap<>();
    private final Console console;
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    public Console getConsole(){
        return this.console;
    }
    public CollectionManager getCollectionManager(){
        return this.collectionManager;
    }
    public CommandManager getCommand(){
        return this.commandManager;
    }
    public Group_by(Console console, CollectionManager collectionManager, CommandManager commandManager) {
        super("group_by", "группировка объектов по переданному полю и вывод топ-3 объектов по этому полю");
        commandManager.addCommandList(getName(), getDescription());
        this.collectionManager = collectionManager;
        this.console = console;
        this.commandManager = commandManager;
    }
    public static class EnterBreak extends Exception {
    }

    /**
     * Выполнение команды
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public CommandMessage execution(String args) {
        groups.put("name", new Group_by_name(collectionManager).execution());
        groups.put("coordinates", new Group_by_coordinates(collectionManager).execution());
        groups.put("creationDate", new Group_by_creationDate(collectionManager).execution());
        groups.put("oscarsCount", new Group_by_oscarsCount(collectionManager).execution());
        groups.put("budget", new Group_by_budget(collectionManager).execution());
        groups.put("tagline", new Group_by_tagline(collectionManager).execution());
        groups.put("genre", new Group_by_genre(collectionManager).execution());
        groups.put("screenWriter", new Group_by_screenWriter(collectionManager).execution());
        if (args.isEmpty()) {
            if (!collectionManager.getCollection().isEmpty()) {
                try {
                    String fieldName = String.valueOf(EnterField());
                    console.println(groups.get(fieldName));
                    return new CommandMessage("Количество групп и их топ успешно выведены!");
                } catch (EnterBreak e) {
                    console.println("Ошибка ввода");
                    return null;
                }
            } else {
                return new CommandMessage(false, "Коллекция пустая(");
            }
        } else {
            return new CommandMessage(false,
                    "Неправильное количество аргументов!)");
        }
    }
    public String EnterField() throws EnterBreak {
        try {
            String field;
            while (true) {
                console.println("Введите название одного из данных полей: ");
                for (String fields : groups.keySet()) {
                    console.println(fields);
                }
                console.print("Введите поле корректно (строка): ");
                field = console.readln().trim();
                if (field.equals("exit")) throw new EnterBreak();
                if (!field.isEmpty() && groups.get(field) != null) {
                    break;
                }
            }
            return field;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.errorMessage("Ошибка чтения");
            return null;
        }
    }
}