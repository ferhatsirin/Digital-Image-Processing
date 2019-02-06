package HotelManagementSystem;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * HotelGuest class helps hotel guests to make reservation and cancel reservation.
 */
public class HotelGuest {

    private LinkedList<String> reservations;
    private String name;
    private Receptionist helper;

    /**
     * Instantiates a new Hotel guest.
     *
     * @param guestName the guest name
     */
    public HotelGuest(String guestName){

        name =guestName;
        helper =new Receptionist(name);
        updateReservations();
    }

    /**
     * Get the guest's name
     *
     * @return the guest name
     */
    public String getName(){

        return name;
    }

    /**
     * Update reservations.
     * if there was reservation made before the program opened, transfers those data to the system
     */
    public void updateReservations() {

        reservations =new LinkedList<>();
        String line;

        for(int i = 0; i< HotelManagement.reservationList.size(); ++i){

            line = HotelManagement.reservationList.get(i);
            if(line.contains(getName().toUpperCase())) {
                reservations.add(line.toUpperCase());
            }
        }
    }

    /**
     * Method for the guest to make a book.
     *
     * @param bookingDate the booking date
     * @return true if successful otherwise false
     * @throws IOException throws if file can't open or written
     */
    public boolean booking(Date bookingDate) throws IOException {
        return helper.booking(name, bookingDate);
    }

    /**
     * Method for the guest to cancel reservation.
     *
     * @param bookingDate the booking date
     * @throws IOException throws if file can't open or written
     */
    public void cancelReservation(Date bookingDate) throws IOException {
        helper.cancelReservation(name, bookingDate);
    }

    /**
     * Cancel reservation.
     * Ask user which reservation is to be cancelled
     *
     * @throws ParseException the parse exception
     * @throws IOException    the io exception
     */
    public void cancelReservation() throws ParseException, IOException {

        if(0 <reservations.size()) {
            Scanner scan = new Scanner(System.in);
            System.out.println("Your reservations : Which one do you want to cancel Enter the number");
            printReservations();
            int delete = scan.nextInt() - 1;

            while(delete < 0 || delete > reservations.size()-1){
                System.out.println("Invalid input!!");
                System.out.println("Your reservations : Which one do you want to cancel Enter the number");
                printReservations();
                delete =scan.nextInt() -1;
            }

            String reservation = reservations.remove(delete);
            String[] split = reservation.split(",");
            Date bookingDate;
            bookingDate = HotelManagement.dateFormat().parse(split[2]);

            helper.cancelReservation(name,bookingDate);
        }
        else{
            System.out.println("You do not have any reservations");
        }
    }

    /**
     * Print guest's reservations.
     */
    public void printReservations(){

        updateReservations();
        System.out.println(name +"'s reservations");
        for(int i=0;i<reservations.size();++i){

            System.out.println((i+1)+ " " + reservations.get(i));
        }
        System.out.println();

    }
}
