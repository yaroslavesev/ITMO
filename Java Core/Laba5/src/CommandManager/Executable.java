package CommandManager;

/**
 * Интерфейс реализуемый каждой командой, отвечает за непосредственное выполнение команды
 */
public interface Executable {
    CommandMessage execution(String args);
}
