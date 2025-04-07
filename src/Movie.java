public class Movie {
    private final String title;
    private final String genre;

    // Constructor
    public Movie(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }
}