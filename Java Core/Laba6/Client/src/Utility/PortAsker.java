package Utility;

import java.util.Scanner;
public class PortAsker {
    public static int getPort(){
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Введите порт: ");
            try {
                int port = Integer.parseInt(scanner.nextLine());
                if (port > 0 && port <= 65535) {
                    return port;
                }
            } catch (NumberFormatException e){
                System.out.println("Попробуйте ещё раз");
            }
        }
    }
}
