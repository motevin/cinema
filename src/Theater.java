/**
 * Class for a Theater
 * @author Tevin
 * @version 1.0
 */
public class Theater {

    /**
     * The name of this Theater
     */
    String name;

    /**
     * The total number of seats in this theatre
     */
    int totalSeats;

    /**
     * Constructor for Theater
     * @param name The name of this Theater
     * @param totalSeats The total number of seats in the theater
     */
    public Theater(String name, int totalSeats) {
        this.name = name;
        this.totalSeats = totalSeats;
    }

    /**
     * Gets the name of the theater
     * @return The str name of the theater
     */
    public String getName() {
        return this.name;
    }

    /**
     * Accessor method for total Seats in a theater
     * @return the total number of seats in this theater
     */
    public int getSeats() {
        return this.totalSeats;
    }

    /**
     * Checks equality of 2 theaters. 
     * 2 Theaters are equal if their names are equal
     * @param o The other object
     * @return T if equal, F otherwise
     */
    public boolean equals(Object o) {
        if (o instanceof Theater) {
            Theater x = (Theater) o;
            return this.name.equals(x.name);
        }
        else {
            return false;
        }
    }

    /**
     * Prints the theater as a string
     * @return the title of the movie
     */
    public String toString() {
        return this.getName();
    }

}
