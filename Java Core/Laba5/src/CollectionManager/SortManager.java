package CollectionManager;

import Models.Movie;

import java.util.Comparator;

public class SortManager {
    /**
     * Сравнивает коллекции по полям в порядке их следования
     * @return компаратор
     */
    public Comparator<Movie> SortCollection(){
        return new MovieIdComparator().thenComparing(new MovieNameComparator().thenComparing(new MovieCoordinatesComparator().thenComparing(new MovieCreationDateComparator().thenComparing(new MovieOscarsCountComparator().thenComparing(new MovieBudgetComparator(). thenComparing(new MovieTaglineComparator().thenComparing(new MovieGenreComparator().thenComparing(new MovieScreenWriterComparator()))))))));
    }
    /**
     * Сравнивает коллекции по имени
     * @return компаратор
     */
    public Comparator<Movie> SortCollectionByName(){
        return new MovieNameComparator();
    }

    /**
     * Сравнивает Movie по ID
     */
    static class MovieIdComparator implements Comparator<Movie>{
        @Override
        public int compare(Movie movie1, Movie movie2){
            return Integer.compare(movie1.getId(), movie2.getId());
        }
    }

    /**
     * Сравнивает Movie по budget
     */
    static class MovieBudgetComparator implements Comparator<Movie>{
        @Override
        public int compare(Movie movie1, Movie movie2){
            return Double.compare(movie1.getBudget(), movie2.getBudget());
        }
    }

    /**
     * Сравнивает Movie по OscarsCount
     */
    static class MovieOscarsCountComparator implements Comparator<Movie>{
        @Override
        public int compare(Movie movie1, Movie movie2){
            return movie1.getOscarsCount().compareTo(movie2.getOscarsCount());
        }
    }
    /**
     * Сравнивает Movie по Date
     */
    static class MovieCreationDateComparator implements Comparator<Movie>{
        @Override
        public int compare(Movie movie1, Movie movie2){
            return Long.compare(movie1.getCreationDate().getTime(), movie2.getCreationDate().getTime());
        }
    }

    /**
     * Сравнивает Movie по имени
     */
    static class MovieNameComparator implements Comparator<Movie>{
        @Override
        public int compare(Movie movie1, Movie movie2){
            return movie1.getName().compareTo(movie2.getName());
        }
    }

    /**
     * Сравнивает Movie по genre
     */
    static class MovieGenreComparator implements Comparator<Movie>{
        @Override
        public int compare(Movie movie1, Movie movie2){
            return movie1.getGenre().compareTo(movie2.getGenre());
        }
    }

    /**
     * Сравнивает Movie по TagLine
     */
    static class MovieTaglineComparator implements Comparator<Movie>{
        @Override
        public int compare(Movie movie1, Movie movie2){
            return movie1.getTagline().compareTo(movie2.getTagline());
        }
    }

    /**
     * Сравнивает Movie по Coordinates
     */
    static class MovieCoordinatesComparator implements Comparator<Movie>{
        @Override
        public int compare(Movie movie1, Movie movie2){
            return movie1.getCoordinates().compareTo(movie2.getCoordinates());
        }
    }

    /**
     * Сравнивает Movie по Person
     */
    static class MovieScreenWriterComparator implements Comparator<Movie> {
        @Override
        public int compare(Movie movie1, Movie movie2){
            return movie1.getScreenWriter().compareTo(movie2.getScreenWriter());
        }
    }
}
