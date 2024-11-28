package Server;
import CommandManager.*;
import Response.*;
import Launcher.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;

public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());
    private final int port;
    private final LaunchCommand launchCommand;
    private final Map<SocketChannel, ObjectOutputStream> clients;
    public Server(int port, CommandManager commandManager, String propPath) {
        this.port = port;
        launchCommand = new LaunchCommand(commandManager);
        clients = new HashMap<>();
        System.setProperty("java.util.logging.config.file", propPath);
    }

    public void start() throws IOException, ClassNotFoundException {
        logger.info("Старт сервера");
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", port));
        while (true) {
            logger.info("Подключение к клиенту");
            SocketChannel clientChannel = serverSocketChannel.accept();
            ObjectOutputStream oos = new ObjectOutputStream(clientChannel.socket().getOutputStream());
            clients.put(clientChannel, oos);
            handleClient(clientChannel);
        }
    }
    private void handleClient(SocketChannel clientChannel) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(clientChannel.socket().getInputStream());
        while (true) {
            try {
                Response message = (Response) ois.readObject();
                logger.info("Получение запроса");
                if (message.getStatus().equals(STATUS.COMMAND)) {
                    Response command_result = launchCommand.commandParser(message.getMessage(), message.getObject());
                    sendResponse(clientChannel, command_result);
                } else {
                    Response command_result = launchCommand.doCommand("save", "", "");
                    System.out.println(command_result);
                    sendResponse(clientChannel, command_result);
                }
            } catch (IOException e) {
                logger.info("Клиент отключился");
                clients.remove(clientChannel);
                break;
            }
        }
    }
    private void sendResponse(SocketChannel clientChannel, Response response) throws IOException {
        ObjectOutputStream oos = clients.get(clientChannel);
        logger.info("Отправка результата на клиент");
        oos.writeObject(response);
        oos.flush();
    }
}
