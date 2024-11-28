package DumpManager;

import CollectionManager.CollectionManager;
import Models.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static CollectionManager.IDManager.AddId;
import static CollectionManager.IDManager.ListID;

/**
 * Чтение и запись XML-файла
 */
public class DumpManager {
    private final CollectionManager collectionManager;
    public DumpManager(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
    /**
     * Читает XML-файл
     * @param filePath - путь к XML-файлу
     * @return результат выполнения (да/нет)
     */
public boolean readMoviesFromXmlFile(String filePath) {
    try {
        File xmlFile = new File(filePath);
        xmlFile.compareTo(xmlFile);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        Element rootElement = doc.getDocumentElement();
        for (Node node = rootElement.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element movieElement = (Element) node;
                Movie movie = createMovieFromElement(movieElement);
                if (movie.validate()){
                    if (!(ListID.contains(movie.getId()))){
                        collectionManager.getCollection().add(movie);
                        AddId(movie.getId());
                    }
                }
            }
        }
        collectionManager.setLastInitTime(LocalDateTime.now());
    } catch (FileNotFoundException e){
        System.out.println("Такого пути не существует");
        return false;
    } catch (ParserConfigurationException | SAXException | IOException e) {
        System.out.println("Проблемы со считыванием файла");
        return false;
    }
    return !collectionManager.getCollection().isEmpty();
}

    /**
     * Создаёт Movie с помощью сторонних библиотек
     * @param element - узел, являющийся элементом коллекции
     * @return Movie - готовый элемент
     */
    private static Movie createMovieFromElement(Element element) {
        Element coordinatesElement = (Element) element.getElementsByTagName("coordinates").item(0);
        Coordinates coordinates = new Coordinates();
        coordinates.setX(Long.parseLong(coordinatesElement.getElementsByTagName("x").item(0).getTextContent()));
        coordinates.setY(Integer.parseInt(coordinatesElement.getElementsByTagName("y").item(0).getTextContent()));
        Element screenwriterElement = (Element) element.getElementsByTagName("screenwriter").item(0);
        Person screenwriter = new Person();
        screenwriter.setName(screenwriterElement.getElementsByTagName("name").item(0).getTextContent());
        String birthdayStr = screenwriterElement.getElementsByTagName("birthday").item(0).getTextContent();
        screenwriter.setBirthday(LocalDate.parse(birthdayStr));
        Element locationElement = (Element) element.getElementsByTagName("location").item(0);
        Location location = new Location();
        location.setName(locationElement.getElementsByTagName("name").item(0).getTextContent());
        location.setX(Float.parseFloat(locationElement.getElementsByTagName("x").item(0).getTextContent()));
        location.setY(Float.parseFloat(locationElement.getElementsByTagName("y").item(0).getTextContent()));
        location.setZ(Float.parseFloat(locationElement.getElementsByTagName("z").item(0).getTextContent()));
        screenwriter.setLocation(location);
        CollectionManager collectionManager = new CollectionManager();
        return collectionManager.MovieCreator(Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent()), element.getElementsByTagName("name").item(0).getTextContent(), coordinates, new Date(), Integer.parseInt(element.getElementsByTagName("oscarsCount").item(0).getTextContent()), Double.parseDouble(element.getElementsByTagName("budget").item(0).getTextContent()), element.getElementsByTagName("tagline").item(0).getTextContent(), MovieGenre.valueOf(element.getElementsByTagName("genre").item(0).getTextContent()), screenwriter);
    }
    /**
     * Записывает XML-файл
     * @param Filepath - путь к XML-файлу
     */
    public void Writer(String Filepath) throws FileNotFoundException{
        PrintWriter writer;
        try {
            writer = new PrintWriter(new FileWriter(Filepath));
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<movies>");
            for (Movie movie : collectionManager.getCollection()) {
                writer.println("\t<movie>");
                writer.println("\t\t<id>" + movie.getId() + "</id>");
                writer.println("\t\t<name>" + movie.getName() + "</name>");
                writer.println("\t\t<coordinates>");
                writer.println("\t\t\t<x>" + movie.getCoordinates().getX() + "</x>");
                writer.println("\t\t\t<y>" + movie.getCoordinates().getY() + "</y>");
                writer.println("\t\t</coordinates>  ");
                writer.println("\t\t<creationDate>" + movie.getCreationDate() + "</creationDate>");
                writer.println("\t\t<oscarsCount>" + movie.getOscarsCount() + "</oscarsCount>");
                writer.println("\t\t<budget>" + movie.getBudget() + "</budget>");
                writer.println("\t\t<tagline>" + movie.getTagline() + "</tagline>");
                writer.println("\t\t<genre>" + movie.getGenre() + "</genre>");
                writer.println("\t\t<screenwriter>");
                writer.println("\t\t\t<name>" + movie.getScreenWriter().getName() + "</name>");
                writer.println("\t\t\t<birthday>" + movie.getScreenWriter().getBirthday() + "</birthday>");
                writer.println("\t\t\t<location>");
                writer.println("\t\t\t\t<x>" + movie.getScreenWriter().getLocation().getX() + "</x>");
                writer.println("\t\t\t\t<y>" + movie.getScreenWriter().getLocation().getY() + "</y>");
                writer.println("\t\t\t\t<z>" + movie.getScreenWriter().getLocation().getZ() + "</z>");
                writer.println("\t\t\t\t<name>" + movie.getScreenWriter().getLocation().getName() + "</name>");
                writer.println("\t\t\t</location>");
                writer.println("\t\t</screenwriter>");
                writer.println("\t</movie>");
            }
            writer.println("</movies>");
            collectionManager.setLastSaveTime(LocalDateTime.now());
            writer.close();
            System.out.println("XML файл успешно создан.");
        }
        catch (FileNotFoundException e){
            System.out.println("Такого пути не существует");
        }
        catch (IOException e) {
            System.out.println("Ошибка записи файла");
            throw new RuntimeException(e);
        }
    }
}
