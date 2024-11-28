package CommandManager;

import CollectionManager.CollectionManager;
import CollectionManager.SortManager;
import Console.Console;
import Console.Enter;
import Models.Movie;

import java.util.Collections;
import java.util.Iterator;

import static CollectionManager.IDManager.GetNewId;

/**
 * Remove_greater - удаляет из коллекции все элементы, превышающие заданный
 */
public class Remove_greater extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    public Remove_greater(Console console, CollectionManager collectionManager, CommandManager commandManager){
        super("remove_greater", " удалить из коллекции все элементы, превышающие заданный");
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
        try {
            if ((args == null || args.isEmpty())) {
                console.println("* Введите Movie:");
                Movie a = Enter.enterMovie(console, GetNewId());
                if (a != null && a.validate()) {
                    collectionManager.add(a);
                    SortManager sortManager = new SortManager();
                    Collections.sort(collectionManager.getCollection(), sortManager.SortCollection());
                    boolean checker = false;
                    int count = 0;
                    Iterator<Movie> iterator = collectionManager.getCollection().iterator();
                    while (iterator.hasNext()) {
                        Movie b = iterator.next();
                        if (b.equals(a)) {
                            checker = true;
                        }
                        if (checker){
                            iterator.remove();
                            count ++;
                        }
                    }
                    return new CommandMessage("Успешно удалено " + (count - 1) + " элементов");
                } else
                    return new CommandMessage(false, "Поля movie не валидны! Movie не создан!");
            }
            else{
                return new CommandMessage(false,
                        "Неправильное количество аргументов!)");
            }
        } catch (Enter.EnterBreak e) {
            throw new RuntimeException(e);
        }
    }
}
