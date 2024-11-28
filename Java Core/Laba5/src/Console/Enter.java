package Console;

import Models.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;
import java.util.NoSuchElementException;

/**
 * Ввод элементов коллекции
 */
public class Enter {
    /**
     * Прерывание ввода
     */
    public static class EnterBreak extends Exception {
    }

    /**
     * Ввод полей Movie
     * @param console - консоль ввода
     * @param id - Movie id
     * @return возваращет Movie
     */
    public static Movie enterMovie(Console console, Integer id) throws EnterBreak{
        try {
            double budget;
            String tagline;
            String name;
            while (true) {
                console.print("Введите корректный Movie name: ");
                name = console.readln().trim();
                if (name.equals("exit")) throw new EnterBreak();
                if (!name.isEmpty()) {
                    break;
                }
            }
            var coordinates = enterCoordinates(console);
            Integer oscarsCount;
            while (true) {
                console.print("Введите корректное oscarsCount > 0: ");
                var line = console.readln().trim();
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
                console.print("Введите корректный budget > 0: ");
                var line = console.readln().trim();
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
            while (true) {
                console.print("Введите корректный tagline: ");
                tagline = console.readln().trim();
                if (tagline.equals("exit")) throw new EnterBreak();
                if (!(tagline.isEmpty())) {
                    break;
                }
            }
            var genre = enterMovieGenre(console);
            var screenwriter = enterPerson(console);
            return new Movie(id, name, coordinates, new Date(), oscarsCount, budget, tagline, genre, screenwriter);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.errorMessage("Ошибка чтения");
            return null;
        }
    }

    /**
     * Заполнение полей Coordinates
     * @return Coordinates
     */
    public static Coordinates enterCoordinates(Console console) throws EnterBreak {
        try {
            long x;
            while (true) {
                console.print("Введите Movie coordinates.x корректно (число): ");
                var line = console.readln().trim();
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
                console.print("Введите Movie coordinates.y корректно (число <= 628): ");
                var line = console.readln().trim();
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
            console.errorMessage("Ошибка чтения");
            return null;
        }
    }

    /**
     * Заполнение Жанра
     * @return genre
     */
    public static MovieGenre enterMovieGenre(Console console) throws EnterBreak, IllegalArgumentException {
        try {
            String genre;
            while (true) {
                console.println("Введите название одного из данных жанров: ");
                for (MovieGenre genre1 : MovieGenre.values()) {
                    console.println(genre1);
                }
                console.print("Введите genre корректно (строка): ");
                genre = console.readln().trim();
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
            console.errorMessage("Ошибка чтения");
            return null;
        }
    }

    /**
     * Заполнение screenwriter
     * @return screenwriter
     */
    public static Person enterPerson(Console console) throws EnterBreak {
        try {
            String name;
            while (true) {
                console.print("Введите ScreenWriter name корректно (строка): ");
                name = console.readln().trim();
                if (name.equals("exit")) throw new EnterBreak();
                if (!(name.isEmpty())) {
                    break;
                }
            }
            LocalDateTime dateTime;
            while (true) {
                System.out.print("Введите дату в формате корректно (дд.мм.гггг): ");
                String input = console.readln();
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
            var location = enterLocation(console);
            return new Person(name, dateTime, location);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.errorMessage("Ошибка чтения");
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
    public static Location enterLocation(Console console) throws EnterBreak {
        try {
            float x;
            while (true) {
                console.print("Введите Person location.x корректно (число): ");
                var line = console.readln().trim();
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
                console.print("Введите Person location.y корректно (число): ");
                var line = console.readln().trim();
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
                console.print("Введите Person location.z корректно (число): ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new EnterBreak();
                if (!line.isEmpty()) {
                    try {
                        z = Float.parseFloat(line);
                        break;
                    } catch (NumberFormatException ignored) {}
                }
            }
            String name;
            while (true) {
                console.print("Введите Location name (строка): ");
                name = console.readln().trim();
                if (name.equals("exit")) throw new EnterBreak();
                if (!(name.isEmpty())) {
                    break;
                }
            }
            return new Location(x, y, z, name);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.errorMessage("Ошибка чтения");
            return null;
        }
    }
}