import java.util.*;

public class w2051518_PlaneManagement {
    //constants for the program
    private static final int ROWS = 4;
    private static final int[] SEATS_PER_ROW = {14, 12, 12, 14};

    private static final int AVAILABLE = 0;
    private static final int SOLD = 1;

    private static final int YELLOW_SEAT_PRICE = 200;
    private static final int BLUE_SEAT_PRICE = 150;
    private static final int GREEN_SEAT_PRICE = 180;

    //Array to store the status of the seats
    private static int[][] seats = new int[ROWS][];
    // Array to store sold tickets
    private static Ticket[] soldTickets = new Ticket[ROWS * SEATS_PER_ROW.length];
    private static int ticketCount = 0;
    private static double totalSales=0;
    private static Scanner pm = new Scanner(System.in);

    // Method to print user menu
    public static void printUserMenu() {
        for (int i = 0; i < 50; i++) {
            System.out.print("*");
        }
        System.out.println();
        System.out.println("*                  MENU OPTIONS                  *");
        for (int i = 0; i < 50; i++) {
            System.out.print("*");
        }
        System.out.println();
        System.out.println("1) Buy a seat");
        System.out.println("2) Cancel a seat");
        System.out.println("3) Find first available seat");
        System.out.println("4) Show seating plan");
        System.out.println("5) Print tickets information and total sales");
        System.out.println("6) Search ticket");
        System.out.println("0) Quit");
        for (int i = 0; i < 50; i++) {
            System.out.print("*");
        }
        System.out.println();
    }
    // Method to store the seat layout
    public static void seat_status() {
        for (int i = 0; i < ROWS; i++) {
            seats[i] = new int[SEATS_PER_ROW[i]];
            for (int j = 0; j < SEATS_PER_ROW[i]; j++) {
                seats[i][j] = AVAILABLE;
            }
        }
    }
    // Method to buy a seat
    public static void buy_seat() {
        int row = input_row();
        if (row == -1) {
            return;
        }int seatNum = input_seatNum(row);
        if (seatNum == -1) {
            return;
        }
        if (!seat_availability(row, seatNum)) {
            System.out.println("Seat is already reserved. Please select a different seat.");
            return;
        }
        //Ask for Person information
        System.out.print("Enter passenger name: ");
        String name = pm.next();
        System.out.print("Enter passenger surname: ");
        String surname = pm.next();
        System.out.print("Enter passenger email: ");
        String email = pm.next();


        Person person = new Person(name, surname, email);
        int price = calculate_price(row, seatNum);

        Ticket ticket = new Ticket((char) ('A' + row), seatNum + 1, price, person);
        // Add the ticket to the  soldTickets array
        soldTickets[ticketCount++] = ticket;
        // Update seat status
        seats[row][seatNum] = SOLD;
        totalSales += price;

        System.out.println("Seat successfully reserved. Total price: £" + price);
        ticket.save();
    }
    //Get a valid row letter
    public static int input_row() {
        while (true) {
            System.out.println("Please select the row (A, B, C, D): ");
            String input = pm.next().toUpperCase(); // Convert to uppercase
            if (input.length() != 1 || input.charAt(0) < 'A' || input.charAt(0) > 'D') {
                System.out.println("Invalid row. Please try again.");
            } else {
                return input.charAt(0) - 'A'; // Convert row letter to index
            }
        }
    }
    //Get a valid seat number
    public static int input_seatNum(int row) {
        while (true) {
            System.out.print("Enter seat number: ");
            if (!pm.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid integer.");
                pm.next(); // Discard the invalid input
            } else {
                int seatNum = pm.nextInt() - 1;
                if (seatNum < 0 || seatNum >= SEATS_PER_ROW[row]) {
                    System.out.println("Invalid seat number. Please try again.");
                } else {
                    return seatNum;
                }
            }
        }
    }
    // Method to check seat availability
    public static boolean seat_availability(int row, int seatNum) {
        return seats[row][seatNum] == AVAILABLE;
    }
    // Method to cancel a seat
    public static void cancel_seat() {
        System.out.println("Please select the seat to cancel:");
        int row = input_row();
        if (row == -1) {
            return;
        }int seatNum = input_seatNum(row);
        if (seatNum == -1) {
            return;
        }
        if (seat_availability(row, seatNum)) {
            System.out.println("Seat is already available. No need to cancel.");
            return;
        }
        seats[row][seatNum] = AVAILABLE;// Resetting the seat status to available after canceling a reservation
        System.out.println("Seat successfully cancelled.");

        for (int i = 0; i < ticketCount; i++) {
            Ticket ticket = soldTickets[i];
            if (ticket.getRow() == ('A' + row) && ticket.getSeat() == seatNum + 1) {
                for (int j = i; j < ticketCount - 1; j++) {
                    soldTickets[j] = soldTickets[j + 1];
                }
                soldTickets[ticketCount - 1] = null; // Remove the last element
                ticketCount--; 
                ticket.deleteFile();
                break;
            }
        }
    }
    //Method to calculate the price of a seat according to the respective colour
    public static int calculate_price(int row, int seatNum) {
        if (seatNum >= 0 && seatNum < 5) {
            return YELLOW_SEAT_PRICE;
        } else if (seatNum >= 5 && seatNum < 9) {
            return BLUE_SEAT_PRICE;
        } else {
            return GREEN_SEAT_PRICE;
        }
    }
    //Method to find the first available seat
    public static void find_first_available() {
        for (int row = 0; row < ROWS; row++) {
            for (int seatNum = 0; seatNum < SEATS_PER_ROW[row]; seatNum++) {
                if (seat_availability(row, seatNum)) {
                    char rowLetter = (char) ('A' + row);
                    System.out.println("First available seat: " + rowLetter + (seatNum + 1));
                    return;
                }
            }
        }System.out.println("No available seats found.");
    }
    //Method to show the seating plan
    public static void show_seating_plan() {
        System.out.println();
        for (int row = 0; row < ROWS; row++) {
            System.out.print(" ");
            for (int seatNum = 0; seatNum < SEATS_PER_ROW[row]; seatNum++) {
                if (seat_availability(row, seatNum)) {
                    System.out.print('O');
                } else {
                    System.out.print('X');
                }
            }System.out.println();
        }
    }
    //Method to print the information of the tickets
    public static void print_tickets_info() {
        System.out.println("Ticket Information:");
        System.out.println("Total number of tickets purchased: " + ticketCount);
        double totalPrice = 0;
        for (int i = 0; i < ticketCount; i++) {
            Ticket ticket = soldTickets[i];
            System.out.println("Ticket " + (i + 1) + "-");
            System.out.println("Row: " + ticket.getRow());
            System.out.println("Seat: " + ticket.getSeat());
            System.out.println("Price: $" + ticket.getPrice());
            ticket.getPerson().printInfo();
            System.out.println();
        }System.out.println("Total amount for tickets sold: £" + totalSales);
        for (int i = 0; i < ticketCount; i++) {
            Ticket ticket = soldTickets[i];
        }
    }
    //Method to search for a ticket
    public static void search_ticket(){
        System.out.println("Enter seat to search: ");
        int row = input_row();
        if (row == -1) {
            return;
        }int seatNum = input_seatNum(row);
        if (seatNum == -1) {
            return;
        }
        if (!seat_availability(row, seatNum)) {
            for (Ticket ticket : soldTickets) {
                if (ticket != null && ticket.getRow() == ('A' + row) && ticket.getSeat() == seatNum + 1) {
                    System.out.println("Sorry,selected seat has already been purchased.");
                    System.out.println("Ticket Information of the purchased seat-");
                    ticket.printInfo();
                }return;
            }
        }System.out.println("This seat is available.");
    }
    //Main method
    public static void main(String[] args) {
        seat_status();
        System.out.println("* Welcome to the Plane Management application. *");
        while (true) {
            printUserMenu();
            System.out.print("Please select an option: ");
            try {
                int option = pm.nextInt();
                switch (option) {
                    case 0:
                        System.out.println("Thank you for using our plane management application. Safe travels!");
                        return;
                    case 1:
                        buy_seat();
                        break;
                    case 2:
                        cancel_seat();
                        break;
                    case 3:
                        find_first_available();
                        break;
                    case 4:
                        show_seating_plan();
                        break;
                    case 5:
                        print_tickets_info();
                        break;
                    case 6:
                        search_ticket();
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid option. Please try again.");
            }pm.nextLine(); // Clear the input buffer
        }
    }
}