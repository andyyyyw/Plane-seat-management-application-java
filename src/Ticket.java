import java.io.IOException;
import java.io.FileWriter;
import java.io.File;
public class Ticket {
    private char row;
    private int seat;
    private double price;
    private Person person;

    public Ticket(char row, int seat, double price, Person person){
        this.row=row;
        this.seat=seat;
        this.price=price;
        this.person=person;
    }

    public char getRow(){
        return row;
    }
    public void setRow(char row) {
        this.row = row;
    }

    public int getSeat(){
        return seat;
    }
    public void setSeat(int seat){
        this.seat=seat;
    }

    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price=price;
    }

    public Person getPerson(){
        return person;
    }
    public void setPerson(Person person){
        this.person=person;
    }

    //Method to print ticket information
    public void printInfo() {
        System.out.println("Row: " + row);
        System.out.println("Seat : " + seat);
        System.out.println("Price: $" + price);

        person.printInfo();
    }
    //Method to save the ticket information to a file
    public void save() {

        String filename = row + String.valueOf(seat) + ".txt";
        try {
            FileWriter saveTicket = new FileWriter(filename);
            saveTicket.write("Ticket Information:\n");
            saveTicket.write("Row: " + row + "\n");
            saveTicket.write("Seat : " + seat + "\n");
            saveTicket.write("Price: $" + price + "\n");
            saveTicket.write("Passenger Information-\n");
            saveTicket.write("Passenger name: " + person.getName() + "\n");
            saveTicket.write("Passenger surname: " + person.getSurname() + "\n");
            saveTicket.write("Passenger email address: " + person.getEmail() + "\n");
            saveTicket.close();
            System.out.println("Ticket information saved to file: " + filename);
        } catch (IOException e) {
            System.out.println("An error occurred while saving the ticket information to file.");
            e.printStackTrace();
        }
    }
    //Method to delete the ticket file

    public void deleteFile() {
        String filename = row + String.valueOf(seat) + ".txt";
        File file = new File(filename);
        if (file.exists()) {
            if (file.delete()){
            System.out.println("Ticket file deleted." );
            } else {
                System.out.println("Failed to delete the ticket file." );
            }
        }else{
            System.out.println("Ticket file does not exist." );
        }
    }
}




