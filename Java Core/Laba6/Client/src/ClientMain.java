import Client.Client;
import java.io.IOException;
import Utility.PortAsker;

public class ClientMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        int port;
//        while (true) {
//            Scanner scanner = new Scanner(System.in);
//            System.out.print("Введите порт: ");
//            try {
//                port = Integer.parseInt(scanner.nextLine());
//                break;
//            } catch (NumberFormatException e){
//                System.out.println("Попробуйте ещё раз");
//            }
//        }
        Client client = new Client("localhost", PortAsker.getPort());
        client.run();
    }
}
