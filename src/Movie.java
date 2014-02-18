/**
 * Class for a Movie
 * Contains:
 *  Theater Name
 *  Length of the movie
 * @author Tevin
 * @version 1.0
 *
 */
public class Movie {

    /**
     * Name of the movie
     */
    private String name;

    /**
     * Length of the movie in mins
     */
    private int length;

    /**
     * Constructor for a movie. Contains a name and the length
     * @param name The name of the movie
     * @param length The length of the movie
     */
    Movie(String name, int length) {
        this.name = name;
        this.length = length;
    }

    /**
     * Gets the name of the movie
     * @return The name of the movie
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the length of the movie
     * @return The length of the movie
     */
    public int getLength() {
        return this.length;
    }

    @Override
    /**
     * Checks equality for movies. 
     * Movies are equal if their names and runtimes are equal
     * @param o The other object
     * @return True if they're equal, false otherwise
     */
    public boolean equals(Object o) {
        if (o instanceof Movie) {
            Movie x = (Movie) o;
            return this.name.equals(x.name) &&
                    this.length == x.length;
        }
        else {
            return false;
        }
    }

    /**
     * Prints the movie as a string
     * @return the title of the movie
     */
    public String toString() {
        return this.getName();
    }
}
