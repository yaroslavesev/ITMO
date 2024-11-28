package Console;

import java.util.Scanner;

/**
 * Интерфейс консоль
 */
public interface Console {
    /**
     * Вывод в одну строку
     * @param o - то что выводим
     */
    void print(Object o);
    /**
     * Вывод в новую строку
     * @param o - то что выводим
     */
    void println(Object o);

    /**
     * Считываем строку типа String
     * @return String строку
     */
    String readln();

    /**
     * Выводим сообщение об ошибке
     * @param o - объект, который вызывает ошибку
     */
    void errorMessage(Object o);

    /**
     * Выводит prompt текущей консоли
     */
    void prompt();

    /**
     * @return promt текущей консоли
     */
    String getPrompt();

    /**
     * Выбираем считывание из файла
     * @param scanner - чем будет читать
     */
    void selectFileScanner(Scanner scanner);

    /**
     * Выбираем считываение из консоли
     */
    void selectConsoleScanner();

    /**
     * @return можно считывать или нет
     */
    boolean isCanReadln();
}
