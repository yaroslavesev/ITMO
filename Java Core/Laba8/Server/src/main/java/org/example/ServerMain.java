package org.example;

import org.example.collectionManager.CollectionManager;
import org.example.commandManager.*;
import org.example.managers.DumpManager;
import org.example.server.Server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;

public class ServerMain {
    private final static Logger logger = Logger.getLogger(ServerMain.class.getName());
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int port;
        String cfg = "C:\\Users\\yarik\\OneDrive\\Рабочий стол\\Education\\Programming\\Labk8\\Server\\src\\main\\java\\org\\example\\DATA\\db.cfg";
        String prop = "C:\\Users\\yarik\\OneDrive\\Рабочий стол\\Education\\Programming\\Labk8\\Server\\src\\main\\java\\org\\example\\DATA\\logging.properties";
        String url = "C:\\Users\\yarik\\OneDrive\\Рабочий стол\\Education\\Programming\\Labk8\\Server\\src\\main\\java\\org\\example\\DATA\\url.txt";
//        System.out.println(new File("C:\\Users\\yarik\\OneDrive\\Рабочий стол\\Education\\Programming\\Labk8\\Server\\src\\main\\java\\org\\example\\DATA\\db.cfg").exists());
        Class.forName("org.postgresql.Driver");
        Properties info = new Properties();
        if (args.length == 0) {
            System.out.println(
                    "Введите имя загружаемого файла как аргумент командной строки");
            System.exit(-1);
        }
//        info.load(new FileInputStream(args[0]));
//        var dumpManager = new DumpManager(Files.readString(Paths.get(args[2])), info, args[1]);
        info.load(new FileInputStream(cfg));
        var dumpManager = new DumpManager(Files.readString(Paths.get(url)), info, prop);
        var collectionManager = new CollectionManager(dumpManager);
        var commandManager = new CommandManager();
        dumpManager.initDataBase();
        collectionManager.setCollection(dumpManager.readFromDataBase());
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
        commandManager.addCommand("save", new Save(collectionManager, commandManager, logger));
        commandManager.addCommand("randomMovies", new RandomMovies(collectionManager, commandManager, logger));
        commandManager.addCommand("users", new Users(collectionManager, commandManager, logger));
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
        Server server = new Server(port, commandManager, args[1], dumpManager);
        server.start();
    }
}
