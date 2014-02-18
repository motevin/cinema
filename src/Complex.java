import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class that contains and handles data for an entire complex.
 * Contains all the shows in the complex
 * @author Tevin
 * @version 45.324
 */
public class Complex {
    /**
     * All the movies being shown in this theater
     */
    private ArrayList<Movie> movies;

    /**
     * All the theaters in this complex
     */
    private ArrayList<Theater> theaters;

    /**
     * All the different shows in this complex
     */
    private ArrayList<Show> shows;

    /**
     * Constructor for a complex
     * @param movies The movies being shown
     * @param theaters The theaters in the complex
     * @param shows The different showtimes
     */
    Complex(ArrayList<Movie> movies, 
            ArrayList<Theater> theaters, 
            ArrayList<Show> shows) {
        this.movies = movies;
        this.theaters = theaters;
        this.shows = shows;
    }

    /**
     * Gets the list of movies being shown in this complex
     * @return A list of movies being shown
     */
    public ArrayList<Movie> getMovies() {
        return this.movies;
    }

    /**
     * Adds a movie to the complex
     * @param movie The movie to be added
     */
    public void addMovie(Movie movie) {
        this.movies.add(movie);
    }

    /**
     * Gets the shows being shown in the complex
     * @return A list of the shows being shown
     */
    public ArrayList<Show> getShows() {
        return this.shows;
    }

    /**
     * Add a show to the theater
     * @param show The show to be added
     */
    public void addShow(Show show) {
        this.shows.add(show);
    }

    /**
     * Get the list of theaters in this complex
     * @return An arraylist of theaters in the complex
     */
    public ArrayList<Theater> getTheaters() {
        return this.theaters;
    }

    /**
     * Add a theater to the complex
     * @param t The theater to be added
     */
    public void addTheater(Theater t) {
        this.theaters.add(t);
    }

    /**
     * Return Show formatted for an order (without ticket info)
     * @param index The index of the show to get a report for
     * @return A string, duh i don't feel like writting javadocs anymore
     */
    public String getShowReportForOrder(int index) {
        String result = "";
        Show s = this.getShows().get(index);
        int movie = this.getMovies().indexOf(s.getMovie()) + 1;
        int theater = this.getTheaters().indexOf(s.getTheater()) + 1;
        int showTime = s.getShowTime();

        result = movie + "," + theater + "," + showTime;
        return result;
    }

    /**
     * Gets the shows being shown properly formatted for a report
     * @return a string with nicely formatted show reports
     */
    public String getShowsReport() {
        String report = "";
        for (Show s: shows) {
            String curShow = "";
            int movie = this.getMovies().indexOf(s.getMovie()) + 1;
            int theater = this.getTheaters().indexOf(s.getTheater()) + 1;
            int showTime = s.getShowTime();
            int seats = s.getAvailSeats();

            //parse ticket sales array and remove '[' , ']' and ' '
            String ticketSales = Arrays.toString(s.getTickets()).replace(
                    "[", "").replace("]", "").replace(" ", "");
            curShow = movie + "," + theater + "," + showTime + 
                    "," + ticketSales + "," + seats + "\n";
            report = report + curShow;
        }
        return report;
    }

    /**
     * Checks equality of complexes
     * 2 Theaters are equal if they have the same shows
     * @param o The other object
     * @return T if equal, F otherwise
     */
    public boolean equals(Object o) {
        if (o instanceof Complex) {
            Complex x = (Complex) o;

            return this.shows.equals(x.shows);
        }
        else {
            return false;
        }

    }

    /**
     * Adds an order to the complex properly
     * @param orderStr the orderString "Movie, Theater, Showtime, {Tix}"
     * @return The total price of this order
     */
    public int order(String orderStr) {

        //Split up the order
        String[] orderArr = orderStr.split(",");
        int[] order = new int[orderArr.length];

        if (orderArr.length < 4) {
            throw new RuntimeException("Error - No tickets in this order: "
                    + orderStr);
        }

        try {
            for (int i = 0; i < order.length; i++) {
                order[i] = Integer.parseInt(orderArr[i]);
            }
        }
        catch (Exception e) {
            System.out.println("Error - Order formatted Incorrectly:" 
                    + orderStr);
        }

        //Get The correct Movie, Theater and showtime for this order
        Movie movie = this.getMovies().get(order[0] - 1);
        Theater theater = this.getTheaters().get(order[1] - 1);
        Show show = new Show(movie, theater, order[2]);

        //The show that we're buying tickets for. 
        //This is the show that's modified
        Show showToGetTix = this.getShows().get(this.getShows().indexOf(show));

        //To buy tickets, get a subarray containing how many of each
        //type of ticket to buy in order. i.e. 4 adult, 4 child -> [4, 4]
        int[] ticketsToBuy = Arrays.copyOfRange(order, 3, order.length);

        //Init. the # of total tickets to buy
        int totalTixToBuy = 0;

        //Find the actual total # of tix to buy
        for (int x : ticketsToBuy) {
            totalTixToBuy += x;
        }

        //the total price of this order
        int totalPrice = 0;

        //Buy the tickets
        if (totalTixToBuy <= showToGetTix.getAvailSeats()) {
            totalPrice = totalPrice(showToGetTix, ticketsToBuy);
            showToGetTix.buyTickets(ticketsToBuy, totalTixToBuy);
        }
        return totalPrice;
    }

    /**
     * Gets the total price of an order
     * @param show The show in this order
     * @param tickets An array of how many of each ticket is going to be bought
     * @return An int of the total price of the order
     */
    public int totalPrice(Show show, int[] tickets) {
        int sum = 0;

        for (int i = 0; i < tickets.length; i++) {
            int priceOfOne = show.getTicketsList().get(i).getPrice();
            sum += tickets[i] * priceOfOne;
        }
        return sum;
    }
}

