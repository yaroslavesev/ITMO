package org.example.inputManager;

import org.example.models.*;
import org.example.models.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;
import java.util.NoSuchElementException;

public class Input {
    public static class EnterBreak extends Exception {
    }

    public static Movie inputMovie(AbstractInput abstractInput, Integer id) throws EnterBreak{
        try {
            double budget;
            String tagline;
            String name;
            do {
                abstractInput.printOnThatLine("Введите корректный Movie name: ");
                name = abstractInput.readln().trim();
                if (name.equals("exit")) throw new EnterBreak();
            } while (name.isEmpty());
            var coordinates = inputCoordinates(abstractInput);
            Integer oscarsCount;
            while (true) {
                abstractInput.printOnThatLine("Введите корректное oscarsCount > 0: ");
                var line = abstractInput.readln().trim();
                if (line.equals("exit")) throw new EnterBreak();
                if (!line.isEmpty()) {
                    try {
                        oscarsCount = Integer.parseInt(line);
                        if (!(oscarsCount <= 0)) break;
                    } catch (NumberFormatException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            while (true) {
                abstractInput.printOnThatLine("Введите корректный budget > 0: ");
                var line = abstractInput.readln().trim();
                if (line.equals("exit")) throw new EnterBreak();
                if (!line.isEmpty()) {
                    try {
                        budget = Double.parseDouble(line);
                        if (budget > 0) break;
                    } catch (NumberFormatException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            do {
                abstractInput.printOnThatLine("Введите корректный tagline: ");
                tagline = abstractInput.readln().trim();
                if (tagline.equals("exit")) throw new EnterBreak();
            } while (tagline.isEmpty());
            var genre = inputMovieGenre(abstractInput);
            var screenwriter = inputPerson(abstractInput);
            return new Movie(id, name, coordinates, new Date(), oscarsCount, budget, tagline, genre, screenwriter);
        } catch (NoSuchElementException | IllegalStateException e) {
            abstractInput.errorMessage("Ошибка чтения");
            return null;
        }
    }

    /**
     * Заполнение полей Coordinates
     * @return Coordinates
     */
    public static Coordinates inputCoordinates(AbstractInput abstractInput) throws EnterBreak {
        try {
            long x;
            while (true) {
                abstractInput.printOnThatLine("Введите Movie coordinates.x корректно (число): ");
                var line = abstractInput.readln().trim();
                if (line.equals("exit")) throw new EnterBreak();
                if (!line.isEmpty()) {
                    try {
                        x = Long.parseLong(line);
                        break;
                    } catch (NumberFormatException ignored) {}
                }
            }
            int y;
            while (true) {
                abstractInput.printOnThatLine("Введите Movie coordinates.y корректно (число <= 628): ");
                var line = abstractInput.readln().trim();
                if (line.equals("exit")) throw new EnterBreak();
                if (!line.isEmpty()) {
                    try {
                        y = Integer.parseInt(line);
                        if (y <= 628) break;
                    } catch (NumberFormatException ignored) {}
                }
            }
            return new Coordinates(x, y);
        } catch (NoSuchElementException | IllegalStateException e) {
            abstractInput.errorMessage("Ошибка чтения");
            return null;
        }
    }

    /**
     * Заполнение Жанра
     * @return genre
     */
    public static MovieGenre inputMovieGenre(AbstractInput abstractInput) throws EnterBreak, IllegalArgumentException {
        try {
            String genre;
            while (true) {
                abstractInput.printOnNextLine("Введите название одного из данных жанров: ");
                for (MovieGenre genre1 : MovieGenre.values()) {
                    abstractInput.printOnNextLine(genre1);
                }
                abstractInput.printOnThatLine("Введите genre корректно (строка): ");
                genre = abstractInput.readln().trim();
                if (genre.equals("exit")) throw new EnterBreak();
                if (!genre.isEmpty()) {
                    boolean genreChecker = false;
                        for (MovieGenre genre1 : MovieGenre.values()) {
                            if (genre1.name().equals(genre)) {
                                genreChecker = true;
                                break;
                            }
                        }
                    if (genreChecker){
                        break;
                    }
                }
            }
            return MovieGenre.valueOf(genre);
        } catch (NoSuchElementException | IllegalStateException e) {
            abstractInput.errorMessage("Ошибка чтения");
            return null;
        }
    }

    /**
     * Заполнение screenwriter
     * @return screenwriter
     */
    public static Person inputPerson(AbstractInput abstractInput) throws EnterBreak {
        try {
            String name;
            do {
                abstractInput.printOnThatLine("Введите ScreenWriter name корректно (строка): ");
                name = abstractInput.readln().trim();
                if (name.equals("exit")) throw new EnterBreak();
            } while (name.isEmpty());
            LocalDateTime dateTime;
            while (true) {
                System.out.print("Введите дату в формате корректно (дд.мм.гггг): ");
                String input = abstractInput.readln();
                String[] parts = input.split("\\.");
                if (parts.length == 3) { // Проверяем корректное количество элементов
                    try {
                        int day = Integer.parseInt(parts[0]);
                        int month = Integer.parseInt(parts[1]);
                        int year = Integer.parseInt(parts[2]);
                        if (isValidDate(year, month, day)) {
                            dateTime = LocalDateTime.of(year, Month.of(month), day, 0, 0);
                            break;
                        } else {
                            System.out.println("Некорректная дата.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Некорректный ввод. Пожалуйста, используйте формат дд.мм.гггг (например, 25.01.2021).");
                    }
                } else {
                    System.out.println("Некорректный ввод. Пожалуйста, используйте формат дд.мм.гггг (например, 25.01.2021).");
                }
            }
            var location = inputLocation(abstractInput);
            return new Person(name, dateTime, location);
        } catch (NoSuchElementException | IllegalStateException e) {
            abstractInput.errorMessage("Ошибка чтения");
            return null;
        }
    }

    /**
     * Валидность дня рождения screenwriter
     * @return валидно или нет
     */
    public static boolean isValidDate(int year, int month, int day) {
        try {
            LocalDateTime.of(year, month, day, 0, 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Заполнение полей Location
     * @return location
     */
    public static Location inputLocation(AbstractInput abstractInput) throws EnterBreak {
        try {
            float x;
            while (true) {
                abstractInput.printOnThatLine("Введите Person location.x корректно (число): ");
                var line = abstractInput.readln().trim();
                if (line.equals("exit")) throw new EnterBreak();
                if (!line.isEmpty()) {
                    try {
                        x = Float.parseFloat(line);
                        break;
                    } catch (NumberFormatException ignored) {}
                }
            }
            Float y;
            while (true) {
                abstractInput.printOnThatLine("Введите Person location.y корректно (число): ");
                var line = abstractInput.readln().trim();
                if (line.equals("exit")) throw new EnterBreak();
                if (!line.isEmpty()) {
                    try {
                        y = Float.parseFloat(line);
                        break;
                    } catch (NumberFormatException ignored) {}
                }
            }
            float z;
            while (true) {
                abstractInput.printOnThatLine("Введите Person location.z корректно (число): ");
                var line = abstractInput.readln().trim();
                if (line.equals("exit")) throw new EnterBreak();
                if (!line.isEmpty()) {
                    try {
                        z = Float.parseFloat(line);
                        break;
                    } catch (NumberFormatException ignored) {}
                }
            }
            String name;
            do {
                abstractInput.printOnThatLine("Введите Location name (строка): ");
                name = abstractInput.readln().trim();
                if (name.equals("exit")) throw new EnterBreak();
            } while (name.isEmpty());
            return new Location(x, y, z, name);
        } catch (NoSuchElementException | IllegalStateException e) {
            abstractInput.errorMessage("Ошибка чтения");
            return null;
        }
    }
}