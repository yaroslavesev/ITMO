package InputManager;
import java.util.NoSuchElementException;
import java.util.Scanner;
public class DefaultInput implements AbstractInput {
    private static final Scanner consoleScanner = new Scanner(System.in);
    private static Scanner fileScanner = null;
    @Override
    public void printOnThatLine(Object o){
        System.out.print(o);
    }
    @Override
    public void printOnNextLine(Object o){
        System.out.println(o);
    }
    @Override
    public String readln() throws NoSuchElementException, IllegalStateException {
        return (fileScanner!=null?fileScanner: consoleScanner).nextLine();
    }
    @Override
    public void errorMessage(Object o){
        System.err.println(o);
    }
    @Override
    public void scriptMod(Scanner scanner) {
        fileScanner = scanner;
    }
    @Override
    public void consoleMod() {
        fileScanner = null;
    }
    @Override
    public boolean isCanReadln() throws IllegalStateException {
        return (fileScanner!=null?fileScanner: consoleScanner).hasNextLine();
    }
}
