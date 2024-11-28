package CommandManager;

import CollectionManager.CollectionManager;
import Console.Console;
import Models.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static CollectionManager.IDManager.GetNewId;

public class RandomMovies extends Command{
    // TODO: Команда которая принимает int N на вход и генерит N разных вариантов объектов коллекции
    private final Console console;
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    private ArrayList<String> films = new ArrayList<>();
    private ArrayList<String> taglines = new ArrayList<>();
    private ArrayList<String> screenWriters = new ArrayList<>();
    private ArrayList<String> location = new ArrayList<>();
    private MovieGenre[] movieGenre = MovieGenre.values();
    public RandomMovies(Console console, CollectionManager collectionManager, CommandManager commandManager) {
        super("randomMovie", "добавлеят n случайных объектов");
        commandManager.addCommandList(getName(), getDescription());
        this.collectionManager = collectionManager;
        this.console = console;
        this.commandManager = commandManager;
    }
    /**
     * Выполнение команды
     * @param args
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public CommandMessage execution(String args) {
        {
            films.add("Star Wars");
            films.add("Green Elephant");
            films.add("Wolf From Wall Street");
            films.add("Shrek");
            films.add("Sopranos");
            films.add("Breaking Bad");
            films.add("Brat");
            films.add("Sherlock");

            taglines.add("kill");
            taglines.add("die");
            taglines.add("love");
            taglines.add("win");
            taglines.add("lose");
            taglines.add("hope");
            taglines.add("survive");
            taglines.add("solve");

            screenWriters.add("Balabanov");
            screenWriters.add("Bondarchuk");
            screenWriters.add("Kubrick");
            screenWriters.add("Nolan");
            screenWriters.add("Scorsese");
            screenWriters.add("Lynch");
            screenWriters.add("Spielberg");
            screenWriters.add("Tarantino");

            location.add("Britain");
            location.add("Italy");
            location.add("France");
            location.add("Usa");
            location.add("Russia");
            location.add("Germany");
            location.add("China");
            location.add("Korea");
        }
        if (args == null || args.isEmpty()) {
            return new CommandMessage(false,
                    "Неправильное количество аргументов!)");
        }
        else{
            Integer N = Integer.parseInt(args.split(" ")[0]);
            Random random = new Random();
            for (int i = 0; i < N; i ++){
                Movie movie = new Movie();
                movie.setId(GetNewId());
                movie.setName(films.get(random.nextInt(films.size())));
                Coordinates coordinates = new Coordinates();
                coordinates.setX(random.nextInt(10000));
                coordinates.setY(random.nextInt(627));
                movie.setCoordinates(coordinates);
                movie.setCreationDate(new Date());
                movie.setOscarsCount(random.nextInt(1, 6));
                movie.setBudget(random.nextInt(100000, 999999999));
                movie.setTagline(taglines.get(random.nextInt(taglines.size())));
                movie.setGenre(movieGenre[random.nextInt(movieGenre.length)]);
                Person screenWriter = new Person();
                movie.setScreenwriter(screenWriter);
                movie.getScreenWriter().setName(screenWriters.get(random.nextInt(screenWriters.size())));
                movie.getScreenWriter().setBirthday(LocalDate.of(random.nextInt(1940, 2000), random.nextInt(1, 12), random.nextInt(1, 28)));
                Location location1 = new Location();
                screenWriter.setLocation(location1);
                movie.getScreenWriter().getLocation().setName(location.get(random.nextInt(location.size())));
                movie.getScreenWriter().getLocation().setX(random.nextInt(10000));
                movie.getScreenWriter().getLocation().setY((float)random.nextInt(10000));
                movie.getScreenWriter().getLocation().setZ(random.nextInt(10000));
                collectionManager.add(movie);
            }
            return new CommandMessage("В коллекцию успешно добавлено " + N + " элементов");
        }
    }
}



