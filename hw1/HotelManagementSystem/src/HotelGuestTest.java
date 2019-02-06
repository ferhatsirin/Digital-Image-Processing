import HotelManagementSystem.HotelGuest;
import HotelManagementSystem.HotelManagement;

import java.io.IOException;
import java.text.ParseException;

public class HotelGuestTest {

    void testBooking() throws ParseException, IOException {
        System.out.println("Test booking");
        HotelGuest guest =new HotelGuest("ferhat");

        guest.booking( HotelManagement.dateFormat().parse("18.07.2018 15:00"));
        guest.printReservations();
        guest.cancelReservation(HotelManagement.dateFormat().parse("18.07.2018 15:00"));
    }

    void testCancelReservation() throws ParseException, IOException{
        System.out.println("Test cancelReservation");
        HotelGuest guest =new HotelGuest("ferhat");

        guest.booking(HotelManagement.dateFormat().parse("18.07.2018 15:00"));
        guest.printReservations();
        guest.cancelReservation(HotelManagement.dateFormat().parse("18.07.2018 15:00"));
        guest.printReservations();
    }

    public static void main(String[] args) throws IOException, ParseException {
        HotelGuestTest test =new HotelGuestTest();
        test.testBooking();
        test.testCancelReservation();

    }

}
