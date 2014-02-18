import java.util.ArrayList;
import java.util.Scanner;

/**
 * Console class. Allows user/manager to interact with TicketSales system
 * @author Tevin
 * @version 1.0
 */
public class Console {
    /**
     * Instance of TicketSales that we're working on
     */
    TicketSales sales;

    /**
     * Console constructor.
     * @param sales The ticket sales class in this console
     */
    Console(TicketSales sales) {
        this.sales = sales;
        System.out.println("Welcome to PIRATED MOVIES 24.7 BIATCHHHH");
    }
    
    /**
     * Scanner for this console. 
     * So we don't have to declare it over and over again
     */
    Scanner read;

    /**
     * Initializes the console
     */
    void initConsole() {
        read = new Scanner(System.in);
        System.out.println("Enter # to choose User Type:");
        System.out.println("1. Manager");
        System.out.println("2. Customer");
        System.out.print(">>> ");
        int useCase = 0;

        try {
            useCase = read.nextInt();

            if (useCase == 1) {
                System.out.println("\nMANAGER INTERFACE");
                System.out.println("*****************");
                initManager();
            }
            else if (useCase == 2) {
                System.out.println("\nCUSTOMER INTERFACE");
                System.out.println("******************");
                System.out.println("\nSo you wanna buy a ticket...");
                System.out.println("Here's what we've got:");
                buyTicket();
            }
            else {
                throw new IllegalArgumentException(useCase 
                        + " is not a valid option");
            }
        }
        catch (Exception e) {
            System.out.println("Error reading input: " + e);
            System.out.println("TRY AGAIN");
            System.out.println("");
            initConsole();
        }

    }

    /**
     * Buys a ticket
     */
    void buyTicket() {
        System.out.println();
        int response = 0;

        //Print out the shows all nice looking
        System.out.println(String.format("%-30s %-10s %-10s %s", 
                "   Movie", "Theater", "   Time", "Seats Remaining"));
        ArrayList<Show> showsOffered = this.sales.complex.getShows();

        for (int i = 0; i < showsOffered.size(); i++) {
            Show s = showsOffered.get(i);
            int option = i + 1;
            System.out.println(option + ". " + s.getShow());
        }

        //Pick a Show
        try {
            read = new Scanner(System.in);
            System.out.print("\nChoose Show: ");
            response = read.nextInt();

        } 
        catch (Exception e) {
            System.out.println("Error reading input: " + e);
            System.out.println("TRY AGAIN");
            System.out.println("");
            buyTicket();
        }
        
        int showIndex = response - 1;
        Show showToBuy = showsOffered.get(showIndex);
        ArrayList<Ticket> tix = showToBuy.getTicketsList();
        String tixBought = pickTickets(tix);
        String order = this.sales.complex.getShowReportForOrder(
                showIndex) + tixBought;
        int totalPrice = this.sales.complex.order(order);

        if (totalPrice == 0) {
            System.out.println("\nNot Enough Seats In Theater");
            System.out.println("Try Again");
            buyTicket();
        }
        else {
            System.out.print("Total Price For Your Order: $" + totalPrice);
        }

    }

    /**
     * Queries user for how many of each type of ticket they want to buy
     * @param tix How many tickets to buy
     * @return A String with the tickets to buy in order, separated by commas
     */
    String pickTickets(ArrayList<Ticket> tix) {
        System.out.println("Choose Number Of Tickets");
        String tickets = "";
        read = new Scanner(System.in);


        for (int x = 0; x < tix.size(); x++) {

            //How many of this type of ticket are we buying
            int howManyOfThis = 0;

            try {
                System.out.print(tix.get(x).getName() + ": ");
                howManyOfThis = read.nextInt();
            } 

            catch (Exception e) {
                System.out.print("Error! Try Again :" + e);
                x -= 1;
                continue;
            }

            if (howManyOfThis < 0) {
                x -= 1;
                System.out.println("Can't buy negative tickets. "
                        + "Enter 0 for no tickets");
                continue;

            }
            //There's going to be a preceeding ',' for the first #
            tickets = tickets + "," + Integer.toString(howManyOfThis);
        }

        return tickets;
    }

    /**
     * Initializes manager interface
     */
    void initManager() {
        read = new Scanner(System.in);
        System.out.println("Pick an option");
        System.out.println("1. Add Show");
        System.out.println("2. Print Sales Log");
        System.out.println("3. Print Manager Report");
        System.out.print(">>> ");

        int response = 0;

        try {
            response = read.nextInt();

            if (response == 1) {
                System.out.println("\nADD A SHOW");
                addShow();
                initConsole();
            }
            else if (response == 2) {
                this.sales.makeReport();
                System.out.println(this.sales.reportSales());
                System.out.println("Report done");
                initManager();
            }
            else if (response == 3) {
                this.sales.makeReport();
                System.out.println(this.sales.managerReport());
                System.out.println("Report done");
                initManager();
            }
            else {
                throw new IllegalArgumentException(response 
                        + " is not a valid option");
            }
        }
        catch (Exception e) {
            System.out.println("Error reading input: " + e);
            System.out.println("TRY AGAIN");
            System.out.println("");
            initManager();
        }


    }

    /**
     * Adds a new show to the list of shows
     */
    void addShow() {

        Show showToAdd = new Show(pickMovie(), 
                pickTheater(), 
                pickShowTime(), 
                addTickets());

        this.sales.complex.addShow(showToAdd);
    }

    /**
     * Lets the user pick a movie from all the movies in the theater
     * @return The movie that was picked
     */
    Movie pickMovie() {

        read = new Scanner(System.in);
        int response = 0;
        Movie movie = null;

        ArrayList<Movie> movies = this.sales.complex.getMovies();

        if (movies.size() == 0) {
            System.out.println("There are no movies currently being shown.");
            movie = addMovie();
        }
        else {
            System.out.println("\nPick a Movie");
            for (int i = 0; i < movies.size(); i++) {
                int a = i + 1;
                System.out.println(a + ". " + movies.get(i));
            }
            System.out.println((movies.size() + 1) + ". Add new movie");
            System.out.print(">>> ");
        }

        try {
            response = read.nextInt() - 1;
            if (response == movies.size()) {
                movie = addMovie();
            }
            else {
                movie = movies.get(response);
            }

        }
        catch (Exception e) {
            System.out.println("Error: " + e);
            System.out.println("TRY AGAIN");
            System.out.println();
            pickMovie();
        }

        return movie;
    }

    /**
     * Adds a new movie to the theater
     * @return the new movie
     */
    Movie addMovie() {
        System.out.println("\nAdding new movie");
        read = new Scanner(System.in);
        String name = "";
        int duration = 0;

        try {
            System.out.print("\nMovie Title: ");
            name = read.nextLine();
            System.out.print("Movie Duration: ");
            duration = read.nextInt();
        }

        catch (Exception e) {
            System.out.println("Error: " + e);
            System.out.println("TRY AGAIN");
            System.out.println();
            addMovie();
        }

        return new Movie(name, duration);
    }

    /**
     * Allows user to pick theater from a list of avail theaters,
     *  or add a new one
     * @return The theater that was picked
     */
    Theater pickTheater() {
        read = new Scanner(System.in);
        int response = 0;
        Theater theater = null;

        ArrayList<Theater> theaters = this.sales.complex.getTheaters();

        if (theaters.size() == 0) {
            System.out.println("There are no theaters in the complex.");
            theater = addTheater();
        }
        else {
            System.out.println("\nPick a Theater");
            for (int i = 0; i < theaters.size(); i++) {
                int a = i + 1;
                System.out.println(a + ". " + theaters.get(i));
            }
            System.out.println((theaters.size() + 1) + ". Add new theater");
            System.out.print(">>> ");
        }

        try {
            response = read.nextInt() - 1;
            if (response == theaters.size()) {
                theater = addTheater();
            }
            else {
                theater =  theaters.get(response);

            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
            System.out.println("TRY AGAIN");
            System.out.println();
            pickMovie();
        }

        return theater;
    }

    /**
     * Add a new theater
     * @return The theater added
     */
    Theater addTheater() {
        System.out.println("\nAdding new theater");
        read = new Scanner(System.in);
        String name = "";
        int maxCapacity = 0;

        try {
            System.out.print("\nTheater Name: ");
            name = read.nextLine();
            System.out.print("Theater Capacity: ");
            maxCapacity = read.nextInt();
        }

        catch (Exception e) {
            System.out.println("Error: " + e);
            System.out.println("TRY AGAIN");
            System.out.println();
            addMovie();
        }

        return new Theater(name, maxCapacity);
    }

    /**
     * Allows user to pick a time
     * @return # of mins since midnight as showtime (english op)
     */
    int pickShowTime() {

        System.out.println("\nPick Time");
        read = new Scanner(System.in);
        int hrs = 0;
        int mins = 0;

        try {
            System.out.print("Enter Hour (24hr format): ");
            hrs = read.nextInt();
            System.out.print("Enter Minutes: ");
            mins = read.nextInt();
        }

        catch (Exception e) {
            System.out.println("Error: " + e);
            System.out.println("TRY AGAIN");
            System.out.println();
            addMovie();
        }
        int time = (hrs * 60) + mins;
        return time;
    }

    /**
     * Adds tickets for this showtime
     * @return the list of tickets
     */
    ArrayList<Ticket> addTickets() {
        ArrayList<Ticket> tickets = new ArrayList<Ticket>();

        read = new Scanner(System.in);
        int response = 0;

        //If there's no tickets
        if (tickets.size() == 0) {
            System.out.println("\nThere are no tickets for this show...");
            tickets.add(addTicket());
        }

        //We already have some tickets. 
        try {
            System.out.println("\nPlease pick an option");
            System.out.println("1. Add more tickets");
            System.out.println("2. Finished adding tickets");
            System.out.print(">>> ");

            response = read.nextInt();
            if (response == 1) {
                tickets.add(addTicket());
            }
            else if (response == 2) {
                return tickets;
            }
            else {
                throw new IllegalArgumentException(response 
                        + " is not a valid response");
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
            System.out.println("TRY AGAIN");
            System.out.println();
            addTickets();
        }


        return tickets;
    }
    
    /**
     * Adds a ticket
     * @return the ticket it made
     */
    Ticket addTicket() {
        System.out.println("\nAdding new ticket");
        read = new Scanner(System.in);
        String name = "";
        int price = 0;

        try {
            System.out.print("\nTicket Name: ");
            name = read.nextLine();
            System.out.print("Ticket Price: ");
            price = read.nextInt();
        }

        catch (Exception e) {
            System.out.println("Error: " + e);
            System.out.println("TRY AGAIN");
            System.out.println();
            addTicket();
        }

        return new Ticket(name, price);
    }
}
