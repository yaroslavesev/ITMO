
package org.example;

import org.example.inputManager.DefaultInput;
import org.example.inputManager.Input;
import org.example.models.Movie;
import org.example.response.Response;
import org.example.response.STATUS;
import org.example.utility.PortAsker;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

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
                System.out.println("Ждём...");
                this.port = PortAsker.getPort();
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
            if (s.trim().split(" ")[0].equals("register")) {
                Scanner scanner_register = new Scanner(System.in);
                while (true){
                    System.out.print("Enter name -> ");
                    if (scanner_register.hasNextLine()) {
                        String user_name = scanner_register.nextLine();
                        Response checkUserRequest = new Response(STATUS.USERCHECK, "checkUser", user_name);
                        oos.writeObject(checkUserRequest);
                        oos.flush();
                        Response checkUserResponse = (Response) ois.readObject();
                        if ((boolean)checkUserResponse.getObject()){
                            System.out.println("This name is already exists!");
                            System.out.println("Enter 'Y' to try to login with another name or use command 'login'");
                            String inputCheck = scanner_register.nextLine();
                            if (!(inputCheck.equals("Y") || inputCheck.equals("y"))){
                                break;
                            }
                        } else {
                            while (true){
                                System.out.print("Enter password -> ");
                                String pass1 = enterPassword();
                                System.out.print("Accept password -> ");
                                String pass2 = enterPassword();
                                if (!(pass1.isEmpty()) && pass1.equals(pass2)) {
                                    Response registerUserRequest = new Response(STATUS.USERCHECK, "registerUser", user_name + " " + pass1);
                                    oos.writeObject(registerUserRequest);
                                    oos.flush();
                                    System.out.println(ois.readObject());
                                    break;
                                } else {
                                    System.out.println("Password mismatch");
                                    System.out.println("Enter 'Y' to try to again'");
                                    String inputCheck = scanner_register.nextLine();
                                    if (!(inputCheck.equals("Y") || inputCheck.equals("y"))) break;
                                }
                            }
                            break;
                        }
                    }
                }
            }
            if (s.trim().split(" ")[0].equals("login")) {
                Scanner scanner_login = new Scanner(System.in);
                while (true){
                    System.out.print("Enter name to login -> ");
                    if (scanner_login.hasNextLine()) {
                        String user_name = scanner_login.nextLine();
                        Response checkUserRequest = new Response(STATUS.USERCHECK, "checkUser", user_name);
                        oos.writeObject(checkUserRequest);
                        oos.flush();
                        Response checkUserResponse = (Response) ois.readObject();
                        if ((boolean) checkUserResponse.getObject()){
                            while (true){
                                System.out.print("Enter password -> ");
                                String pswd1 = enterPassword();
                                Response checkPasswordRequest = new Response(STATUS.USERCHECK, "checkPassword", user_name + " " + pswd1);
                                oos.writeObject(checkPasswordRequest);
                                oos.flush();
                                Response checkPasswordResponse = (Response) ois.readObject();
                                if ((boolean)checkPasswordResponse.getObject()){
                                    System.out.println("Вы успешно вошли в аккаунт!");
                                    break;
                                } else {
                                    System.out.println("Enter 'Y' if u want to try again");
                                    String inputCheck = scanner_login.nextLine();
                                    if (!(inputCheck.equals("Y") || inputCheck.equals("y"))) break;
                                }
                            }
                            break;
                        } else {
                            System.out.println("Error: Cant find such user");
                            System.out.println("Enter 'Y' if u want to try again, u can try to register!!!");
                            String inputCheck = scanner_login.nextLine();
                            if (!(inputCheck.equals("Y") || inputCheck.equals("y"))) break;
                        }
                    }
                }
            }
            if (s.trim().split(" ")[0].equals("logout")) {
                Scanner scanner_logout = new Scanner(System.in);
                System.out.println("Enter Y if you really want to logout");
                String inputCheck = scanner_logout.nextLine();
                if (inputCheck.equals("Y") || inputCheck.equals("y")) {
                    oos.writeObject(new Response(STATUS.USERCHECK, "logout"));
                    oos.flush();
                    System.out.println("You are logged out");
                }
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
                        Movie movie = Input.inputMovie(defaultInput, 999999999);
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
                    if (response2.getMessage().equals("Такой команды не существует")){
                        System.out.println();
                    }
                    else System.out.println(response2);
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
    public static String enterPassword() {
//        Console console = System.console();
//        char[] passwordChars = console.readPassword();
//        return new String(passwordChars);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
