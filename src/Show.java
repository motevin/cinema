import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class for a showing of a movie.
 * Specifies time and theater.
 * @author Tevin
 * @version 1.0
 */
public class Show {
    /**
     * Movie being shown
     */
    private Movie movie;

    /**
     * Theater the movie is being shown in
     */
    private Theater theater;

    /**
     * The time the movie is being shown
     */
    private int showTime;

    /**
     * An ArrayList of the tickets being sold
     */
    private ArrayList<Ticket> tickets;

    /**
     * Available seats for this show. Changes at each order
     */
    private int availSeats;

    /**
     * The constructor for a show
     * @param movie The movie showing
     * @param theater The theater the movie is in
     * @param showTime the time the movie is showing. # of mins since midnight
     * @param tickets The tickets being sold for this movie
     */
    public Show(Movie movie, Theater theater, int showTime, 
            ArrayList<Ticket> tickets) {

        this.movie = movie;
        this.theater = theater;
        this.showTime = showTime;
        this.tickets = tickets;
        this.availSeats = theater.getSeats();
    }

    /**
     * Alternate Constructor used for 
     * finding the right show when generating orders
     * Has no ArrayList<Ticket> because that doesn't 
     * matter when finding equality
     * @param movie The movie being shown
     * @param theater The theater the movie is in
     * @param showTime The time the movie starts
     */
    public Show(Movie movie, Theater theater, int showTime) {
        this.movie = movie;
        this.theater = theater;
        this.showTime = showTime;
    }

    /**
     * Returns the show as a nicely formatted string
     * Name \t\t Theater \t\t Time \t\t Remaining Seats
     * @return A string format of the show
     */
    public String getShow() {
        String m = this.getMovie().getName();
        String t = this.getTheater().getName();
        int timeHr = this.getShowTime() / 60;
        int timeMin = this.getShowTime() % 60;
        String ampm = "am";
        String timeMinStr = Integer.toString(timeMin);

        //correct format for Pm shows
        if (timeHr > 12) {
            timeHr = timeHr - 12;
            ampm = "pm";
        }

        //Correct format for on the hour shows
        if (timeMin == 0) {
            timeMinStr = "00";
        }

        String time = timeHr + ":" + timeMinStr + ampm;
        int remaining = this.getAvailSeats();
        String result;
        result = String.format("%-30s %-10s %-10s %d", 
                m, t, time, remaining);
        return result;
    }


    /**
     * Get the movie for this show
     * @return The Movie for this show
     */
    public Movie getMovie() {
        return this.movie;
    }

    /**
     * get the theater for this show
     * @return The theater for this show
     */
    public Theater getTheater() {
        return this.theater;  
    }

    /**
     * Gets the showtime
     * @return an int with the showtime of this movie
     */
    public int getShowTime() {
        return this.showTime;
    }

    /**
     * Returns the total tickets sold for this show
     * @return An int[] whose elements are how many of each type of 
     *          ticket has been sold
     */
    public int[] getTickets() {
        int[] result = new int[this.tickets.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = this.tickets.get(i).getTotalSold();
        }

        return result;
    }

    /**
     * Returns the ArrayList<Ticket> of tickets in this show
     * @return an ArrayList of the tickets in this show
     */
    public ArrayList<Ticket> getTicketsList() {
        return this.tickets;
    }

    /**
     * Changes the # of tickets sold of each type. Essentially buying a ticket
     * Also updates the number of seats
     * @param ticketsToBuy An array of the tickets you need to buy
     * @param totalTix the total # of tickets being sold
     */
    public void buyTickets(int[] ticketsToBuy, int totalTix) {
        for (int i = 0; i < ticketsToBuy.length; i++) {
            //Add tickets that are being bought to total tickets sold of
            //this type
            this.getTicketsList().get(i).setTotalSold(ticketsToBuy[i]);
        }

        //Update # of seats
        this.setSeats(this.getAvailSeats() - totalTix);
    }

    /**
     * Gets the # of free seats in a theater
     * @return The # of free seats
     */
    public int getAvailSeats() {
        return this.availSeats;
    }

    /**
     * Change the # of available seats (after buying tix)
     * @param newSeats The new # of seats available
     */
    public void setSeats(int newSeats) {
        this.availSeats = newSeats;

    }


    /**
     * Checks equality for shows. Shows are equal if the movie,
     * theater and showtime are equal
     * @param o The object to compare equality
     * @return T if they're equal F otherwise
     */
    public boolean equals(Object o) {
        if (o instanceof Show) {
            Show x = (Show) o;
            return this.movie.equals(x.movie) &&
                    this.theater.equals(x.theater) &&
                    this.showTime == x.showTime;
        }
        else {
            return false;
        }
    }

    /**
     * Transforms a show to a string for the manager reports
     * Format is
     * Movie,Theater,Showtime,TotalSeats,{Tickets}
     * @return A String version of the show
     */
    public String toString() {
        String m = this.getMovie().getName();
        String th = this.getTheater().getName();
        String ti = Arrays.toString(
                this.getTickets())
                .replace("[", "").replace("]", "")
                .replace(" ", "");

        return m + "," + th + "," + this.getShowTime() + "," 
        + this.getTheater().getSeats() + "," + ti + "\n";
    }
}
