package org.example.server;

import org.example.commandManager.CommandManager;
import org.example.launcher.LaunchCommand;
import org.example.managers.DumpManager;
import org.example.managers.PasswordManager;
import org.example.managers.UserStatusManager;
import org.example.response.Response;
import org.example.response.STATUS;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());
    private final int port;
    private final LaunchCommand launchCommand;
    private final Map<SocketChannel, ObjectOutputStream> clients;
    private final Map<SocketChannel, UserStatusManager> users;
    private final ExecutorService requestThreadPool;
    private final ExecutorService responseThreadPool;
    private final ReadWriteLock clientsLock;
    private final DumpManager dumpManager;
    public Server(int port, CommandManager commandManager, String propPath, DumpManager dumpManager) {
        this.port = port;
        this.launchCommand = new LaunchCommand(commandManager);
        this.dumpManager = dumpManager;
        clients = new HashMap<>();
        users = new HashMap<>();
        requestThreadPool = Executors.newCachedThreadPool();
        responseThreadPool = Executors.newFixedThreadPool(228);
        clientsLock = new ReentrantReadWriteLock();
        System.setProperty("java.util.logging.config.file", propPath);
    }

    public void start() throws IOException {
        logger.info("Starting the server");
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", port));
        while (true) {
            logger.info("Waiting for client connection");
            SocketChannel clientChannel = serverSocketChannel.accept();
            clientsLock.writeLock().lock();
            try {
                ObjectOutputStream oos = new ObjectOutputStream(clientChannel.socket().getOutputStream());
                UserStatusManager userStatusManager = new UserStatusManager(false, "");
                clients.put(clientChannel, oos);
                users.put(clientChannel, userStatusManager);
            } finally {
                clientsLock.writeLock().unlock();
            }
            requestThreadPool.execute(() -> handleClient(clientChannel));
        }
    }
//    public void start() throws IOException {
//        logger.info("Запуск сервера");
//        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
//        serverSocketChannel.bind(new InetSocketAddress("localhost", port));
//        while (true) {
//            Thread clientHandlerThread = new Thread(() -> {
//                SocketChannel clientChannel;
//                try {
//                    clientChannel = serverSocketChannel.accept();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                clientsLock.writeLock().lock();
//                try {
//                    logger.info("Ожидание подключения клиента");
//                    ObjectOutputStream oos = new ObjectOutputStream(clientChannel.socket().getOutputStream());
//                    UserStatusManager userStatusManager = new UserStatusManager(false, "");
//                    clients.put(clientChannel, oos);
//                    users.put(clientChannel, userStatusManager);
//                } catch (IOException e) {
//                    logger.warning("Ошибка при обработке подключения клиента: " + e.getMessage());
//                } finally {
//                    clientsLock.writeLock().unlock();
//                }
//                requestThreadPool.execute(() -> handleClient(clientChannel));
//            });
//            clientHandlerThread.start();
//        }
//    }


    private void handleClient(SocketChannel clientChannel) {
        try {
            UserStatusManager userStatusManager = users.get(clientChannel);
            ObjectInputStream ois = new ObjectInputStream(clientChannel.socket().getInputStream());
            while (true) {
                try {
                    Response message = (Response) ois.readObject();
                    logger.info("Received a request from client: " + users.get(clientChannel).getUser_name());
                    if (message.getStatus().equals(STATUS.COMMAND)) {
                        if (message.getMessage().equals("save")){
                            sendResponse(clientChannel, new Response(STATUS.ERROR, "Такой команды не существует \nЧтобы сохраниться выйдите из аккаунта!"));
                        } else {
                            Response commandResult = launchCommand.commandParser(message.getMessage(), message.getObject(), userStatusManager);
                            sendResponse(clientChannel, commandResult);
                        }
                    } else if (message.getStatus().equals(STATUS.USERCHECK)) {
                        Response commandResult;
                        switch (message.getMessage()) {
                            case "checkUser" -> {
                                commandResult = new Response(STATUS.USERCHECK, "", dumpManager.checkUser((String) message.getObject()));
                                sendResponse(clientChannel, commandResult);
                            }
                            case "registerUser" -> {
                                String data = (String) message.getObject();
                                dumpManager.registerUser(data.split(" ")[0], PasswordManager.hashPassword((data.split(" ")[1])));
                                logger.info("User = " + users.get(clientChannel).getUser_name() + " added successfully!");
                                commandResult = new Response(STATUS.USERCHECK, "User added successfully!");
                                userStatusManager.setUser_name(data.split(" ")[0]);
                                userStatusManager.setStatus(true);
                                sendResponse(clientChannel, commandResult);
                            }
                            case "checkPassword" -> {
                                String data = (String) message.getObject();
                                commandResult = new Response(STATUS.USERCHECK, "", dumpManager.checkPassword(data.split(" ")[0], (data.split(" ")[1])));
                                System.out.println(dumpManager.checkPassword(data.split(" ")[0], (data.split(" ")[1])));
                                if (dumpManager.checkPassword(data.split(" ")[0], (data.split(" ")[1]))){
                                    userStatusManager.setUser_name(data.split(" ")[0]);
                                    userStatusManager.setStatus(true);
                                    logger.info("User = " + users.get(clientChannel).getUser_name() + " successfully logged in!");
                                } else {
                                    logger.info("Passwords are different");
                                }
                                sendResponse(clientChannel, commandResult);
                            }
                            case "logout" -> {
                                logger.info("logout...");
                                userStatusManager.setUser_name("");
                                userStatusManager.setStatus(false);
                            }
                        }
                    } else {
                        Response commandResult = launchCommand.doCommand("save", "", "", userStatusManager);
                        sendResponse(clientChannel, commandResult);
                    }
                } catch (IOException e) {
                    logger.info("Client = " + users.get(clientChannel).getUser_name() + " - disconnected");
                    clientsLock.writeLock().lock();
                    try {
                        clients.remove(clientChannel);
                        userStatusManager.setUser_name("");
                        userStatusManager.setStatus(false);
                        users.remove(clientChannel);
                    } finally {
                        clientsLock.writeLock().unlock();
                    }
                    break;
                } catch (ClassNotFoundException e) {
                    logger.log(Level.SEVERE, "Error reading object from client = " + users.get(clientChannel).getUser_name());
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error handling client: = " + users.get(clientChannel).getUser_name());
        }
    }

//    private void sendResponse(SocketChannel clientChannel, Response response) {
//        clientsLock.readLock().lock();
//        try {
//            ObjectOutputStream oos = clients.get(clientChannel);
//            responseThreadPool.execute(() -> {
//                try {
//                    logger.info("Sending response to client: " + users.get(clientChannel).getUser_name());
//                    oos.writeObject(response);
//                    oos.flush();
//                } catch (IOException e) {
//                    logger.log(Level.SEVERE, "Error sending response to client: " + users.get(clientChannel).getUser_name() , e);
//                }
//            });
//        } finally {
//            clientsLock.readLock().unlock();
//        }
//    }
    private void sendResponse(SocketChannel clientChannel, Response response) {
        responseThreadPool.execute(() -> {
            clientsLock.readLock().lock();
            ObjectOutputStream oos = clients.get(clientChannel);
            try {
                logger.info("Sending response to client: " + users.get(clientChannel).getUser_name());
                oos.writeObject(response);
                oos.flush();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error sending response to client: " + users.get(clientChannel).getUser_name() , e);
            }
            clientsLock.readLock().unlock();
        });
    }
}
