import java.util.ArrayList;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

/**
 * Mandatory class that allows testing of the program
 * @author Tevin
 * @version 1.0
 *
 */
public class TicketSales {
    /**
     * Complex for sales. contains a list of all the movies currently showing
     */
    Complex complex;

    /**
     * String with all the sales reports
     * Sales reports include every order with each order's total price
     */
    private String salesReport = "";

    /**
     * String with all the manager reports
     * Manager reports include how many of each 
     * type of ticket have been sold for a show
     */
    private String managerReport = "";
    
    /**
     * Number of manager reports
     */
    private int reportsCt = 0;
    
    /**
     * String with a log of all the errors
     */
    private String logReport = "";
    
    /**
     * Console for this TicketSales
     */
    private Console console;

    /**
     * Constructor for TicketSales
     * Initializes an empty complex
     */
    public TicketSales() {
        this.complex = new Complex(
                new ArrayList<Movie>(), 
                new ArrayList<Theater>(), 
                new ArrayList<Show>());
        this.console = new Console(this);
    }

    /**
     * Initializes a Complex with a list of moves and theaters
     * @param fileName The name of the file to initialize the Cinema with
     */
    void initCinema(String fileName) {
        try {
            initCinema(readData(fileName));
        } 
        catch (Exception e) {
            System.out.print("Error during reading input file:\n" + e);
        }
    }

    /**
     * Initializes the cinema using the data as an arraylist
     * @param data The ArrayList that the data comes in
     */
    private void initCinema(ArrayList<String> data) {
        int moviesStart;
        int moviesEnd;
        int theatersStart;
        int theatersEnd;
        int showsStart;
        int showsEnd;
        int ticketsStart;
        int ticketsEnd;

        moviesStart = data.indexOf("Movies") + 1;
        moviesEnd = data.indexOf("Theaters");

        theatersStart = data.indexOf("Theaters") + 1;
        theatersEnd = data.indexOf("Shows");

        showsStart = data.indexOf("Shows") + 1;
        showsEnd = data.indexOf("Prices");

        ticketsStart = data.indexOf("Prices") + 1;
        ticketsEnd = data.indexOf("End");

        if(moviesStart > theatersStart ||
                theatersStart  > showsStart ||
                showsStart > ticketsStart) {
            throw new RuntimeException("File not organized correctly");
        }

        initMovies(data, moviesStart, moviesEnd);
        initTheaters(data, theatersStart, theatersEnd);
        initShows(data, showsStart, showsEnd, ticketsStart, ticketsEnd);
    }

    /**
     * Reads the cinema initialization file and 
     * stores every line as an array of strings
     * @param fileName The name of the file to initialize the cinema with
     * @return An ArrayList<String> of the lines
     * @throws IOException 
     */
    private ArrayList<String> readData(String fileName) throws IOException {
        //Initialize readers
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);

        int numLines = numLines(fileName);
        ArrayList<String> fileData = new ArrayList<String>(numLines);

        for (int i = 0; i < numLines; i++ ) {
            fileData.add(br.readLine());

        }
        br.close();
        return fileData;
    }

    /**
     * Returns the # of lines of the input file
     * @param fileName the name of the file to read from
     * @return An integer that reps the # of lines in fileName
     * @throws IOException 
     */
    private int numLines(String fileName) throws IOException {
        //Initialize readers
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);

        int numLines = 0;

        while (br.readLine() != null) {
            numLines++;
        }
        br.close();

        return numLines;
    }

    /**
     * Reads all the movies in a file and adds them to the complex
     * Method is protected so that i can easily test exceptions
     * @param data The name of the file to read from
     * @param start The index of the first movie
     * @param end The index where movies stop (theaters start)
     */
    protected void initMovies(ArrayList<String> data, int start, int end){
        String curStr;
        String[] parts;


        for (int i = start; i < end; i++) {
            curStr = data.get(i);   //get the movie
            parts = curStr.split(":");  //Split the movie into name and length

            if (parts.length != 2) {
                throw new RuntimeException("Movie informatted correctly");
            }

            //Add movie to complex
            this.complex.addMovie(new Movie(parts[0],
                    Integer.parseInt(parts[1])));
        }
    }

    /**
     * Reads all the theaters in a file and adds them to the complex
     * Method is protected so that i can easily test exceptions
     * @param data data
     * @param start The index of the first theater
     * @param end The index where theaters stop (theaters start)
     */
    protected void initTheaters(ArrayList<String> data, int start, int end) {
        String curStr;
        String[] parts;

        for (int i = start; i < end; i++) {
            curStr = data.get(i);
            parts = curStr.split(":");

            if (parts.length != 2) {
                throw new RuntimeException("Theaters formatted incorrectly");
            }

            this.complex.addTheater(new Theater(parts[0], 
                    Integer.parseInt(parts[1])));
        }
    }

    /**
     * Reads all the shows in a file and adds them to the complex
     * Method is protected so that i can easily test exceptions
     * @param data The name of the file to read from
     * @param start The index of the first show
     * @param end The index where theaters stop (theaters start)
     * @param startTix start for tix
     * @param endTix end for tix
     */
    protected void initShows(ArrayList<String> data, int start, int end, 
            int startTix, int endTix) {
        String curStr;
        String[] parts;
        int[] partsInt = new int[3];

        for (int i = start; i < end; i++) {
            curStr = data.get(i);
            parts = curStr.split(",");

            if(parts.length != 3) {
                throw new RuntimeException("Show formatted incorrectly");
            }

            //Parse the parts array to an array of ints to make life easier
            for (int c = 0; c < partsInt.length; c++) {
                partsInt[c] = Integer.parseInt(parts[c]);

            }

            this.complex.addShow(new Show(
                    this.complex.getMovies().get(partsInt[0] - 1), 
                    this.complex.getTheaters().get(partsInt[1] - 1),
                    partsInt[2],
                    initTickets(data, startTix, endTix)));
        }
    }

    /**
     * Reads all the tickets in a file and parses them into an arraylist of tickets
     * Method is protected so that i can easily test exceptions
     * @param data The file(list) to read data from
     * @param start The index to start reading
     * @param end The index to stop reading
     * @return an ArrayList<Ticket> with all the tickets
     */
    protected ArrayList<Ticket> initTickets(
            ArrayList<String> data, int start, int end) {
        String curStr;
        String[] parts;
        ArrayList<Ticket> result = new ArrayList<Ticket>();

        for (int i = start; i < end; i++) {
            curStr = data.get(i);
            parts = curStr.split(":");

            if (parts.length != 2) {
                throw new RuntimeException("Tickets formatted incorrectly");
            }

            result.add(new Ticket(parts[0], Integer.parseInt(parts[1])));
        }
        return result;
    }
    /**
     * Processes a list of orders given within a file
     * @param fileName The name of the file to process orders from
     */
    void processOrders(String fileName) {
        try {
            processOrders(readData(fileName));
        }
        catch (Exception e) {
            System.out.println("Error during processing orders: \n" + e);
        }
    }

    /**
     * Processes a list of orders from an arraylist
     * @param orders The ArrayList to process orders from
     */
    void processOrders(ArrayList<String> orders) {
        for (String curLine : orders) {

            if (curLine.equals("report")) {
                makeReport();
            }
            else {
                int price = this.complex.order(curLine);
                addSale(curLine, price);
            }
        }
    }

    /**
     * Generates a report of all the sales up to this point
     * @return The String salesReport
     */
    String reportSales() {
        return this.salesReport;
    }
    
    /**
     * Adds a sale to the sales report
     * @param order The order request with # of tickets
     * @param price The price of the order
     */
    
    private void addSale(String order, int price) {
        this.salesReport = this.salesReport + order + "," + price + "\n";
    }
    
    /**
     * Generates a manager report, which is how many types of each ticket
     * been sold for each show
     * @return The String managerReport
     */
    String managerReport() {
        return this.managerReport;
    }
    
    /**
     * Generates manager reports
     */
    void makeReport() {
        reportsCt++;
        String thisReport = "";
        thisReport += "Report " + this.reportsCt + "\n";
        for (Show s: this.complex.getShows()) {
            thisReport += s.toString();
        }
        managerReport += thisReport;
    }
    /**
     * Generates a log of all the errors so far in the complex
     * @return The String logReport
     */
    String logReport() {
        return logReport;
    }
    
    /**
     * initializes GUI (console)
     */
    void initGUI() {
        this.console.initConsole();
    }
    
    /**
     * Main method
     * @param args main method bleh
     */
    public static void main(String[] args) {
        TicketSales x = new TicketSales();
        x.initCinema("cinema.txt");
        x.processOrders("ordersNoReports.txt");
        x.initGUI();
        
        /*
        System.out.println(x.reportSales());
        System.out.println("");
        System.out.println(x.managerReport);
        */
        
    }
}
