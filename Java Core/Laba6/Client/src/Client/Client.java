package Client;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import InputManager.DefaultInput;
import InputManager.Input;
import Models.Movie;
import Response.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import Utility.PortAsker;
import static InputManager.Input.inputMovie;
public class Client {
    ObjectOutputStream oos;
    ObjectInputStream ois;
    ArrayList<String> scriptHistory = new ArrayList<>();
    private final String host;
    private int port;
    public Client(String host, int port) {
        this.port = port;
        this.host = host;
    }
    public void run(){
        while (true){
            try {
                Socket socket = new Socket(host, port);
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
                start();
                if (!socket.isClosed()){
                    break;
                }
            } catch (IOException | ClassNotFoundException e ) {
                System.err.println("Не удалось подключиться к серверу. Пожалуйста, попробуйте позже.");
                System.out.println();
                System.out.print("Введите exit, чтобы завершить работу или любой другой символ, чтобы продолжить: ");
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNextLine()){
                    if (scanner.nextLine().equals("exit")){
                        System.out.println("Завершение работы");
                        System.exit(0);
                    }
                }
                System.out.print("Введите Y, чтобы сменить порт: ");
                try {
                    System.out.println("Ждём...");
                    Thread.sleep(3333);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
    public void launch(Scanner scanner, DefaultInput defaultInput) throws IOException, ClassNotFoundException {
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            if (s.trim().split(" ")[0].equals("exit")) {
                Response response = new Response(STATUS.SAVE);
                oos.writeObject(response);
                oos.flush();
                System.out.println(ois.readObject());
                break;
            }
            if (s.trim().split(" ")[0].equals("execute_script")) {
                scriptMode((s.trim() + " ").split(" ", 2)[1].trim());
            }
            Response response1 = new Response(STATUS.COMMAND, s);
            try {
                oos.writeObject(response1);
                oos.flush();
                Response response2 = (Response) ois.readObject();
                if (response2.getStatus().equals(STATUS.NEED_OBJECT)) {
                    try {
                        Movie movie = inputMovie(defaultInput, 999999999);
                        if (!response2.getObject().equals("")) {
                            movie.setId((Integer) response2.getObject());
                        }
                        Response response3 = new Response(STATUS.COMMAND, s, movie);
                        oos.writeObject(response3);
                        oos.flush();
                        System.out.println(ois.readObject());
                    } catch (Input.EnterBreak e) {
                        System.out.println("Завершение работы");
                        System.exit(1);
                    }
                } else {
                    System.out.println(response2);
                }
            } catch (IOException e) {
                run();
            }
        }
    }

    public void start() throws IOException, ClassNotFoundException {
        DefaultInput defaultInput = new DefaultInput();
        Scanner scanner = new Scanner(System.in);
        launch(scanner, defaultInput);
    }


    public void scriptMode(String args) throws IOException, ClassNotFoundException {
        if (!new File(args).exists()) {
            System.out.println(new Response(STATUS.ERROR, "Файл не существует!"));
            start();
        }
        if (!Files.isReadable(Paths.get(args))) {
            System.out.println(new Response(STATUS.ERROR, "Прав для чтения нет!"));
            start();
        }
        try (Scanner scriptScanner = new Scanner(new File(args))) {
            System.out.println(args);
            DefaultInput defaultInput = new DefaultInput();
            defaultInput.scriptMod(scriptScanner);
            if (scriptHistory.contains(args)){
                System.out.println("Рекурсия!!!");
                start();
            } else {
                scriptHistory.add(args);
                launch(scriptScanner, defaultInput);
                scriptHistory.clear();
                start();
            }
        }
    }
}
