/**
 * Class for a generic movie ticket
 * Contains
 *  Price of the ticket
 * @author Tevin
 * @version 1.0
 */
public class Ticket {
    /**
     * The type of ticket
     */
    private String name;

    /**
     * The price of this ticket
     */
    private int price;

    /**
     * Number of tickets of this type sold
     */
    private int totalSold;

    /**
     * The constructor for a ticket
     * @param name The name of the ticket
     * @param price The dollar price of the ticket
     */
    public Ticket(String name, int price) {
        this.name = name;
        this.price = price;
        this.totalSold = 0;
    }

    /**
     * Gets the name of this ticket
     * @return this.name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the price of one of these tickets
     * @return this.price
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Get the total # of tix of this type sold for its show
     * @return this.totalSold
     */
    public int getTotalSold() {
        return this.totalSold;
    }

    /**
     * Updates the # of total tickets sold by adding moreTix
     * @param moreTix The # of tickets to add to total
     */
    public void setTotalSold(int moreTix) {
        this.totalSold += moreTix;
    }

    /**
     * Overrides equals for tickets
     * 2 Tickets are equal if their name and price are equal
     * @param o blah blah blah i hate webcat this is the object
     * @return Whether the ticket and Object o are equal
     */
    public boolean equals(Object o) {
        if (o instanceof Ticket) {
            Ticket x = (Ticket) o;
            return this.getName().equals(x.getName()) &&
                    this.getPrice() == x.getPrice();
        }
        else {
            return false;
        }
    }

    /**
     * Prints the ticket as a string
     * @return the title of the movie
     */
    public String toString() {
        return this.getName();
    }
}
