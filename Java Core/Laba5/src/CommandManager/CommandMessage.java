package CommandManager;

public class CommandMessage {
    private boolean exitCode;
    private String message;

    public CommandMessage(boolean code, String s) {
        exitCode = code;
        message = s;
    }

    /**
     * Возвращает сообщение и успех выполнения
     * @param s
     */
    public CommandMessage(String s) {
        this(true, s);
    }

    public boolean getExitCode() {
        return exitCode;
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        return String.valueOf(exitCode) + ";" + message + ";";
    }
}
