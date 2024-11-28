package CommandManager;

import CollectionManager.CollectionManager;
import Console.Console;
import Models.Movie;
import Console.Enter;

/**
 * Команда Update - обновляет значение элемента коллекции, id которого равен заданному.
 */
public class Update extends Command{
    private final Console console;
    private final CollectionManager collectionManager;

    public Update(Console console, CollectionManager collectionManager, CommandManager commandManager) {
        super("update", "обновить значение элемента коллекции, id которого равен заданному");
        commandManager.addCommandList(getName(), getDescription());
        this.collectionManager = collectionManager;
        this.console = console;
    }
    /**
     * Выполнение команды
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public CommandMessage execution(String args) {
        if (args == null || args.isEmpty()) {
            return new CommandMessage(false,
                    "Неправильное количество аргументов!)");
        } else {
            Integer id = Integer.parseInt(args.split("")[0]);
            System.out.println(id);
            System.out.println(id.getClass());
            if (collectionManager.getCollection().isEmpty()) {
                return new CommandMessage(false, "Коллекция пустая");
            }
            boolean exist = false;
            for (Movie movie : collectionManager.getCollection()) {
                if (movie.getId() == id) {
                    exist = true;
                    break;
                }
            }
            if (exist){
                try {
                    collectionManager.remove(id);
                    console.println("* Обновление Movie:");
                    Movie a = Enter.enterMovie(console, id);
                    if (a != null && a.validate()) {
                        collectionManager.add(a);
                        return new CommandMessage("Movie успешно обновлён!");
                    } else
                        return new CommandMessage(false, "Поля movie не валидны! Movie не создан!");
                } catch (Enter.EnterBreak e) {
                    throw new RuntimeException(e);
                }
            } else {
                return new CommandMessage(false, "Такого ID не существует");
            }
        }
    }
}
