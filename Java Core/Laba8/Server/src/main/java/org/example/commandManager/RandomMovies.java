package org.example.commandManager;
import org.example.collectionManager.CollectionManager;
import org.example.managers.UserStatusManager;
import org.example.models.*;
import org.example.response.Response;
import org.example.response.STATUS;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import static org.example.collectionManager.IDManager.GetNewId;

public class RandomMovies extends Command{
    private final CollectionManager collectionManager;
    private final ArrayList<String> films = new ArrayList<>();
    private final ArrayList<String> taglines = new ArrayList<>();
    private final ArrayList<String> screenWriters = new ArrayList<>();
    private final ArrayList<String> location = new ArrayList<>();
    private final MovieGenre[] movieGenre = MovieGenre.values();
    private final Logger logger;

    public RandomMovies(CollectionManager collectionManager, CommandManager commandManager, Logger logger) {
        super("randomMovies", "добавляет n случайных объектов");
        commandManager.addCommandList(getName(), getDescription());
        this.collectionManager = collectionManager;
        this.logger = logger;
    }
    /**
     * Выполнение команды
     * @return Успешность выполнения команды и сообщение об успешности.
     */
    @Override
    public Response execution(String args, Object object, UserStatusManager userStatusManager) {
        if (!userStatusManager.getStatus()){
            return new Response(STATUS.OK, "Войдите в аккаунт!");
        }
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
            logger.warning(userStatusManager.getUser_name() + " -> " + "Неправильное количество аргументов!)");
            return new Response(STATUS.ERROR,
                    "Неправильное количество аргументов!)");
        }
        else{
            int N = Integer.parseInt(args.split(" ")[0]);
            Random random = new Random();
            IntStream.range(0, N)
                    .mapToObj(i -> {
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
                        screenWriter.setName(screenWriters.get(random.nextInt(screenWriters.size())));
                        screenWriter.setBirthday(LocalDate.of(random.nextInt(1940, 2000), random.nextInt(1, 12), random.nextInt(1, 28)));
                        Location location1 = new Location();
                        screenWriter.setLocation(location1);
                        location1.setName(location.get(random.nextInt(location.size())));
                        location1.setX(random.nextInt(10000));
                        location1.setY((float)random.nextInt(10000));
                        location1.setZ(random.nextInt(10000));
                        movie.setUser_name(userStatusManager.getUser_name());
                        return movie;
                    })
                    .forEach(collectionManager::add);

            logger.info(userStatusManager.getUser_name() + " -> " + super.getName());
            return new Response(STATUS.OK, "В коллекцию успешно добавлено " + N + " элементов");
        }
    }
}



