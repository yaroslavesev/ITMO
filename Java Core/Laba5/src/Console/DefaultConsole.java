package Console;
import java.util.NoSuchElementException;
import java.lang.IllegalStateException;
import java.util.Scanner;
public class DefaultConsole implements Console {
    private static final String P = "$ ";
    private static Scanner fileScanner = null;
    private static final Scanner defScanner = new Scanner(System.in);
    @Override
    public void print(Object o){
        System.out.print(o);
    }
    @Override
    public void println(Object o){
        System.out.println(o);
    }
    @Override
    public String readln() throws NoSuchElementException, IllegalStateException {
        return (fileScanner!=null?fileScanner:defScanner).nextLine();
    }
    @Override
    public void errorMessage(Object o){
        System.err.println(o);
    }
    @Override
    public void prompt() {
        print(P);
    }

    /**
     * @return prompt текущей консоли
     */
    @Override
    public String getPrompt() {
        return P;
    }
    @Override
    public void selectFileScanner(Scanner scanner) {
        fileScanner = scanner;
    }
    @Override
    public void selectConsoleScanner() {
        fileScanner = null;
    }
    @Override
    public boolean isCanReadln() throws IllegalStateException {
        return (fileScanner!=null?fileScanner:defScanner).hasNextLine();
    }
}
