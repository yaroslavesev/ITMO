package Runner;

import CommandManager.CommandManager;
import CommandManager.CommandMessage;
import Console.Console;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Парсит команды и выполняет скрипт
 */
public class Runner {
    private Console console;
    private final CommandManager commandManager;
    private final List<String> scriptStack = new ArrayList<>();
    private int lengthRecursion = -1;

    public Runner(Console console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
    }

    /**
     * Интерактивный режим
     */
    public void interactiveMode() {
        try {
            CommandMessage commandStatus;
            String[] userCommand = {"", ""};

            while (true) {
                console.prompt();
                userCommand = (console.readln().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                commandStatus = launchCommand(userCommand);
                if (commandStatus.getMessage().equals("exit")) break;
                console.println(commandStatus.getMessage());
            }
        } catch (NoSuchElementException exception) {
            console.errorMessage("Пользовательский ввод не обнаружен!");
        } catch (IllegalStateException exception) {
            console.errorMessage("Непредвиденная ошибка!");
        }
    }

    /**
     * Проверяет рекурсивность выполнения скриптов.
     * @param argument Название запускаемого скрипта
     * @return можно ли выполнять скрипт.
     */
    private boolean checkRecursion(String argument, Scanner scriptScanner) {
        var recStart = -1;
        var i = 0;
        for (String script : scriptStack) {
            i++;
            if (argument.equals(script)) {
                if (recStart < 0) recStart = i;
                if (lengthRecursion < 0) {
                    console.selectConsoleScanner();
                    console.println("Была замечена рекурсия! Введите максимальную глубину рекурсии (0..500)");
                    while (lengthRecursion < 0 || lengthRecursion > 500) {
                        try { console.print("> "); lengthRecursion = Integer.parseInt(console.readln().trim()); } catch (NumberFormatException e) { console.println("длина не распознана"); }
                    }
                    console.selectFileScanner(scriptScanner);
                }
                if (i > recStart + lengthRecursion || i > 500)
                    return false;
            }
        }
        return true;
    }


    /**
     * Режим для запуска скрипта.
     * @param argument Аргумент скрипта
     * @return Код завершения.
     */
    private CommandMessage scriptMode(String argument) {
        String[] userCommand = {"", ""};
        StringBuilder executionOutput = new StringBuilder();

        if (!new File(argument).exists()) return new CommandMessage(false, "Файл не существет!");
        if (!Files.isReadable(Paths.get(argument))) return new CommandMessage(false, "Прав для чтения нет!");

        scriptStack.add(argument);
        try (Scanner scriptScanner = new Scanner(new File(argument))) {

            CommandMessage commandStatus;

            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            console.selectFileScanner(scriptScanner);
            do {
                userCommand = (console.readln().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                while (console.isCanReadln() && userCommand[0].isEmpty()) {
                    userCommand = (console.readln().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                }
                executionOutput.append(console.getPrompt() + String.join(" ", userCommand) + "\n");
                var needLaunch = true;
                if (userCommand[0].equals("execute_script")) {
                    needLaunch = checkRecursion(userCommand[1], scriptScanner);
                }

                commandStatus = needLaunch ? launchCommand(userCommand) : new CommandMessage("Превышена максимальная глубина рекурсии");
                if (userCommand[0].equals("execute_script")) console.selectFileScanner(scriptScanner);
                executionOutput.append(commandStatus.getMessage()+"\n");
            } while (commandStatus.getExitCode() && !commandStatus.getMessage().equals("exit") && console.isCanReadln());

            console.selectConsoleScanner();
            if (!commandStatus.getExitCode() && !(userCommand[0].equals("execute_script") && !userCommand[1].isEmpty())) {
                executionOutput.append("Проверьте скрипт на корректность введенных данных!\n");
            }

            return new CommandMessage(commandStatus.getExitCode(), executionOutput.toString());
        } catch (FileNotFoundException exception) {
            return new CommandMessage(false, "Файл со скриптом не найден!");
        } catch (NoSuchElementException exception) {
            return new CommandMessage(false, "Файл со скриптом пуст!");
        } catch (IllegalStateException exception) {
            console.errorMessage("Непредвиденная ошибка!");
            System.exit(0);
        } finally {
            scriptStack.remove(scriptStack.size() - 1);
        }
        return new CommandMessage("");
    }

    /**
     * Launchs the command.
     * @param userCommand Команда для запуска
     * @return Код завершения.
     */
    private CommandMessage launchCommand(String[] userCommand) {
        if (userCommand[0].equals("")) return new CommandMessage("");
        var command = commandManager.getCommands().get(userCommand[0]);

        if (command == null) return new CommandMessage(false, "Команда '" + userCommand[0] + "' не найдена. Наберите 'help' для справки");

        switch (userCommand[0]) {
            case "execute_script" -> {
                CommandMessage tmp = null;
                tmp = commandManager.getCommands().get("execute_script").execution(userCommand[1]);
                if (!tmp.getExitCode() || tmp == null) return tmp;
                CommandMessage tmp2 = scriptMode(userCommand[1]);
                return new CommandMessage(tmp2.getExitCode(), tmp.getMessage()+"\n"+tmp2.getMessage().trim());
            }
            default -> {
                return command.execution(userCommand[1]);
            }
        }
    }
}