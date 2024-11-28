import CollectionManager.CollectionManager;
import CommandManager.*;
import DumpManager.DumpManager;
import Server.Server;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

public class ServerMain {
    private final static Logger logger = Logger.getLogger(ServerMain.class.getName());
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int port;
        var commandManager = new CommandManager();
        if (args.length == 0) {
            System.out.println(
                    "Введите имя загружаемого файла как аргумент командной строки");
            System.exit(-1);
        }
        var collectionManager = new CollectionManager();
        var dumpManager = new DumpManager(collectionManager);
        if (!dumpManager.readMoviesFromXmlFile(args[0])) {
            System.exit(-1);
        }
        commandManager.addCommand("show", new Show(collectionManager, commandManager, logger));
        commandManager.addCommand("help", new Help(collectionManager, commandManager, logger));
        commandManager.addCommand("info", new Info(collectionManager, commandManager, logger));
        commandManager.addCommand("clear", new Clear(collectionManager,  commandManager, logger));
        commandManager.addCommand("add", new Add(collectionManager, commandManager, logger));
        commandManager.addCommand("print_ascending", new Print_Ascending(collectionManager, commandManager, logger));
        commandManager.addCommand("min_by_name", new Min_by_name(collectionManager, commandManager, logger));
        commandManager.addCommand("sum_of_budget", new Sum_of_budget(collectionManager, commandManager, logger));
        commandManager.addCommand("remove_first", new Remove_First(collectionManager, commandManager, logger));
        commandManager.addCommand("remove_by_id", new Remove_by_id(collectionManager, commandManager, logger));
        commandManager.addCommand("update", new Update(collectionManager, commandManager, logger));
        commandManager.addCommand("add_if_max", new Add_if_max(collectionManager, commandManager, logger));
        commandManager.addCommand("remove_greater", new Remove_greater(collectionManager, commandManager, logger));
        commandManager.addCommand("group_by", new Group_by(collectionManager, commandManager, logger));
        commandManager.addCommand("randomMovies", new RandomMovies(collectionManager, commandManager, logger));
        commandManager.addCommand("save", new Save(collectionManager, commandManager, args[0], logger));
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Введите порт: ");
            try {
                port = Integer.parseInt(scanner.nextLine());
                if (port > 0 && port <= 65535) {
                    break;
                }
            } catch (NumberFormatException e){
                System.out.println("Попробуйте ещё раз");
            }
        }
        Server server = new Server(port, commandManager, args[1]);
        server.start();
    }
}