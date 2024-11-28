import Console.DefaultConsole;
import DumpManager.*;
import CollectionManager.*;
import CommandManager.*;
import Runner.*;
public class Main {
    public static void main(String[] args) {
        var console = new DefaultConsole();
        if (args.length == 0) {
            console.println(
                    "Введите имя загружаемого файла как аргумент командной строки");
            System.exit(1);
        }
        var collectionManager = new CollectionManager();
        var dumpManager = new DumpManager(collectionManager);
        if (!dumpManager.readMoviesFromXmlFile(args[0])) {
            System.exit(1);
        }
        var commandManager = new CommandManager();
        commandManager.addCommand("help", new Help(console, collectionManager, commandManager));
        commandManager.addCommand("info", new Info(console, collectionManager, commandManager));
        commandManager.addCommand("show", new Show(console, collectionManager, commandManager));
        commandManager.addCommand("clear", new Clear(console, collectionManager,  commandManager));
        commandManager.addCommand("add", new Add(console, collectionManager, commandManager));
        commandManager.addCommand("print_ascending", new Print_Ascending(console, collectionManager, commandManager));
        commandManager.addCommand("min_by_name", new Min_by_name(console, collectionManager, commandManager));
        commandManager.addCommand("sum_of_budget", new Sum_of_budget(console, collectionManager, commandManager));
        commandManager.addCommand("remove_first", new Remove_First(console, collectionManager, commandManager));
        commandManager.addCommand("remove_by_id", new Remove_by_id(console, collectionManager, commandManager));
        commandManager.addCommand("update", new Update(console, collectionManager, commandManager));
        commandManager.addCommand("save", new Save(console, collectionManager, commandManager, args[0]));
        commandManager.addCommand("exit", new Exit(console, collectionManager, commandManager));
        commandManager.addCommand("add_if_max", new Add_if_max(console, collectionManager, commandManager));
        commandManager.addCommand("remove_greater", new Remove_greater(console, collectionManager, commandManager));
        commandManager.addCommand("execute_script", new ExecuteScript(console, collectionManager, commandManager));
        commandManager.addCommand("group_by", new Group_by(console, collectionManager, commandManager));
        commandManager.addCommand("randomMovies", new RandomMovies(console, collectionManager, commandManager));
        new Runner(console, commandManager).interactiveMode();
    }
}