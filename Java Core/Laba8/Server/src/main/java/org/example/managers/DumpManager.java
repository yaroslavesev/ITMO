package org.example.managers;

import org.example.models.Coordinates;
import org.example.models.Location;
import org.example.models.Movie;
import org.example.models.Person;
import org.example.server.Server;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;

import static org.example.collectionManager.IDManager.AddId;
import static org.example.managers.PasswordManager.hashPassword;
public class DumpManager {
    private Connection connection;
    private static final Logger logger = Logger.getLogger(Server.class.getName());
    public DumpManager(String url, Properties info, String propPath){
        System.setProperty("java.util.logging.config.file", propPath);
        start(url, info);
    }
    public void start(String url, Properties info){
        while (true){
            try {
                connection = DriverManager.getConnection(url, info);
                logger.info("Successfully connected to the database");
                break;
            } catch (SQLException | NullPointerException e) {
                logger.warning("Error with connection to DataBase");
                System.out.println("Enter Y if you want to stop server");
                Scanner scanner = new Scanner(System.in);
                if (scanner.nextLine().equalsIgnoreCase("Y")){
                    logger.info("Stop working...");
                    System.exit(0);
                    break;
                }
            }
        }
    }
    public void initDataBase(){
        createUserTable();
        createTableMovie();
    }
    public void createTableLocation(){
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = """
                    CREATE TABLE IF NOT EXISTS Location (
                    \tx float,
                    \ty float NOT NULL,
                    \tz float,
                    \tname TEXT NOT NULL,
                    \tPRIMARY KEY (x, y, z, name)
                    );""";
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            logger.warning("Error creating LOCATION");
        }
    }

    public void createTablePerson(){
        try {
            createTableLocation();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = """
                    CREATE TABLE IF NOT EXISTS Person (
                    \tname TEXT NOT NULL,
                    \tlocation_x float,
                    \tlocation_y float NOT NULL,
                    \tlocation_z float,
                    \tlocation_name TEXT NOT NULL,
                    \tbirthday TIMESTAMP NOT NULL,
                    \tFOREIGN KEY(location_x, location_y, location_z, location_name) REFERENCES Location(x, y, z, name),
                    \tPRIMARY KEY (name, birthday, location_x, location_y, location_z, location_name)
                    );""";
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            logger.warning("Error creating PERSON");
        }
    }

    public void createTableCoordinates(){
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = """
                    CREATE TABLE IF NOT EXISTS Coordinates (
                    \tx bigint NOT NULL,
                    \ty int NOT NULL CHECK (y <= 628),
                    \tPRIMARY KEY (x, y)\s
                    );""";
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
           logger.warning("Error creating COORDINATES");
        }
    }
    public void createTableMovie(){
        createTableCoordinates();
        createTablePerson();
        createIdSeq();
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = """
                    CREATE TABLE IF NOT EXISTS Movie (
                    \tid int NOT NULL DEFAULT nextval('ID_SEQ'),
                    \tname TEXT NOT NULL,
                    \tcoordinates_x bigint,
                    \tcoordinates_y int NOT NULL,
                    \tFOREIGN KEY(coordinates_x, coordinates_y) REFERENCES Coordinates(x, y),
                    \tcreationDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    \toscarsCount int CHECK (oscarsCount > 0),\s
                    \tbudget float NOT NULL CHECK (budget > 0),
                    \ttagline TEXT NOT NULL,
                    \tgenre TEXT NOT NULL,
                    \tscreenwriterName TEXT NOT NULL,
                    \tscreenwriterBirthday TIMESTAMP NOT NULL,
                    \tlocation_x float,
                    \tlocation_y float NOT NULL,
                    \tlocation_z float,
                    \tlocation_name TEXT NOT NULL,
                    \tFOREIGN KEY (screenwriterName, screenwriterBirthday, location_x, location_y, location_z, location_name) REFERENCES
                    \tPerson(name, birthday, location_x, location_y, location_z, location_name),
                    \tuser_name TEXT,FOREIGN KEY (user_name) REFERENCES users(user_name));""";
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            logger.warning("Error creating MOVIE");
        }
    }
    public void createUserTable(){
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = "CREATE TABLE IF NOT EXISTS USERS " +
                    "(user_name TEXT PRIMARY KEY, " +
                    " password TEXT);";
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            logger.warning("Error creating USERS");
        }
    }
    public void createIdSeq(){
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = "CREATE SEQUENCE IF NOT EXISTS ID_SEQ START WITH 1 INCREMENT BY 1;";
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            logger.warning("Error creating SEQUENCE");
        }
    }
    public boolean checkUser(String user_name){
        boolean exists = false;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        try {
            String sql = "SELECT COUNT(*) AS count FROM users WHERE user_name = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user_name);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                if (count > 0) {
                    exists = true;
                }
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            logger.warning("Error while checking user");
        }
        return exists;
    }
    public void registerUser(String user_name, String pswd){
        PreparedStatement preparedStatement = null;
        try {
            String sql = "INSERT INTO users (user_name, password) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user_name);
            preparedStatement.setString(2, pswd);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warning("Error adding user registration");
        }
        finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                logger.warning("Error with closing statement");
            }
        }
    }
    public boolean checkPassword(String user_name, String pswd) {
        String sql = "SELECT password FROM Users WHERE user_name = ?";
        PreparedStatement prepareStatement;
        try {
            prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, user_name);
            ResultSet resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("password");
                String hashedInputPassword = hashPassword(pswd);
                prepareStatement.close();
                resultSet.close();
                return hashedInputPassword.equals(hashedPassword);
            }
            prepareStatement.close();
            resultSet.close();
        } catch (SQLException ex) {
            logger.warning("Error while reading user_name and passwd");
        }
        return false;
    }
    public ArrayList<String> getUsers() {
        String sql = "SELECT user_name FROM Users;";
        PreparedStatement prepareStatement;
        ArrayList<String> users = new ArrayList<>();
        try {
            prepareStatement = connection.prepareStatement(sql);
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                String user = resultSet.getString("user_name");
                users.add(user);
            }
            if (!users.isEmpty()){
                prepareStatement.close();
                resultSet.close();
            } else {
                users.add("There are no users yet...");
                return users;
            }
        } catch (SQLException ex) {
            logger.warning("Error while reading FROM users");
        }
        return users;
    }
    public LinkedList<Movie> readFromDataBase(){
        PreparedStatement preparedStatement;
        LinkedList<Movie> movies = new LinkedList<>();
        try {
            String sql = "SELECT * FROM Movie";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Movie movie = new Movie();
                Coordinates coordinates = new Coordinates();
                movie.setId(rs.getInt("id"));
                AddId(rs.getInt("id"));
                movie.setName(rs.getString("name"));
                coordinates.setX(rs.getLong("coordinates_x"));
                coordinates.setY(rs.getInt("coordinates_y"));
                movie.setCoordinates(coordinates);
                movie.setCreationDate(rs.getTimestamp("creationDate"));
                movie.setOscarsCount(rs.getInt("oscarsCount"));
                movie.setBudget(rs.getDouble("budget"));
                movie.setTagline(rs.getString("tagline"));
                movie.setGenre(CastManager.castToDragonType(rs.getString("genre")));
                Location location = new Location();
                location.setX(rs.getFloat("location_x"));
                location.setY(rs.getFloat("location_y"));
                location.setX(rs.getFloat("location_x"));
                location.setName(rs.getString("location_name"));
                Person person = new Person();
                person.setLocation(location);
                person.setBirthday(rs.getTimestamp("screenwriterBirthday").toLocalDateTime().toLocalDate());
                person.setName(rs.getString("screenwriterName"));
                movie.setUser_name(rs.getString("user_name"));
                movie.setScreenwriter(person);
                movies.add(movie);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            logger.warning("Error while reading from Movie");
        }
        return movies;
    }
    public void saveToDataBase(LinkedList<Movie> movies, String user_name){
        StringBuilder sql = new StringBuilder("DELETE FROM Movie WHERE user_name = ?;");
        PreparedStatement prepareStatement;
        for (Movie movie: movies){
            if (movie.getUser_name().equals(user_name)){
                if (!movie.getUser_name().isEmpty()){
                    var value = getValue(movie);
                    sql.append(value);
                }
            }
        }
        try {
            prepareStatement = connection.prepareStatement(sql.toString());
            prepareStatement.setString(1, user_name);
            prepareStatement.executeQuery();
            prepareStatement.close();
        } catch (SQLException ex) {
            logger.info("Saved");
        }
    }
    private static String getValue(Movie movie) {
        String value = "INSERT INTO Coordinates(x, y)" +
                "VALUES (" + movie.getCoordinates().getX() + "," + movie.getCoordinates().getY()+ ") ON CONFLICT (x, y) DO NOTHING;";
        value += "INSERT INTO Location(x, y, z, name)" +
                "VALUES (" + movie.getScreenWriter().getLocation().getX() + "," + movie.getScreenWriter().getLocation().getY() + ", "
                + movie.getScreenWriter().getLocation().getZ() + ", " + "'" + movie.getScreenWriter().getLocation().getName() + "'" +") ON CONFLICT (x, y, z, name) DO NOTHING;";
        value += "INSERT INTO Person(name, birthday, location_x, location_y, location_z, location_name)" +
                "VALUES (" + "'" +movie.getScreenWriter().getName() + "'" + ", " + "'" + movie.getScreenWriter().getBirthday() + "'" + ", " + movie.getScreenWriter().getLocation().getX() + "," + movie.getScreenWriter().getLocation().getY() + ", " +
                movie.getScreenWriter().getLocation().getZ() + ", " + "'" + movie.getScreenWriter().getLocation().getName() + "'" +") ON CONFLICT (name, birthday, location_x, location_y, location_z, location_name) DO NOTHING;";
        value += "INSERT INTO Movie(id, name, coordinates_x, coordinates_y, creationDate, oscarsCount, budget, tagline, genre, screenwriterName, " +
                "screenwriterBirthday, location_x, location_y, location_z, location_name, user_name)" +
                "VALUES";
        value += "(" + movie.getId() + "," + "'" + movie.getName() + "'" + "," + movie.getCoordinates().getX() + "," + movie.getCoordinates().getY()
                + "," + "'" + movie.getCreationDate() + "'" +  "," + movie.getOscarsCount() + "," + movie.getBudget() + "," + "'" + movie.getTagline() + "'" + ","
                + "'" + movie.getGenre() + "'" + "," + "'" + movie.getScreenWriter().getName() + "'" + "," + "'" + movie.getScreenWriter().getBirthday() + "'" + ", " +
                movie.getScreenWriter().getLocation().getX() + ", " +
                movie.getScreenWriter().getLocation().getY() + ", " +
                movie.getScreenWriter().getLocation().getZ() + ", " +
                "'" + movie.getScreenWriter().getLocation().getName() + "'" + ", " +
                "'" + movie.getUser_name() + "'" + ");";
        return value;
    }
}
