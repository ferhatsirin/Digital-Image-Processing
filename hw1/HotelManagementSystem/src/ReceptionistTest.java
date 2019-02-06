import HotelManagementSystem.HotelManagement;
import HotelManagementSystem.Receptionist;

import java.io.IOException;
import java.text.ParseException;

public class ReceptionistTest {

    void testCheckIn() throws ParseException, IOException {
        System.out.println("Test CheckIn");

        Receptionist receptionist =new Receptionist("ferhat");

        receptionist.booking("Ahmet Can", HotelManagement.dateFormat().parse("18.07.2018 15:00"));
        receptionist.checkIn("Ahmet Can",HotelManagement.dateFormat().parse("18.07.2018 15:00"));

        receptionist.printReservations();
        receptionist.cancelReservation("Ahmet Can",HotelManagement.dateFormat().parse("18.07.2018 15:00"));
    }

    void testCheckOut() throws ParseException, IOException {
        System.out.println("Test checkOut");
        Receptionist receptionist =new Receptionist("ferhat");

        receptionist.booking("Ahmet Can",HotelManagement.dateFormat().parse("18.07.2018 15:00"));
        receptionist.checkIn("Ahmet Can",HotelManagement.dateFormat().parse("18.07.2018 15:00"));
        receptionist.checkOut("Ahmet Can",HotelManagement.dateFormat().parse("18.07.2018 15:00"));
        receptionist.printReservations();
        receptionist.cancelReservation("Ahmet Can",HotelManagement.dateFormat().parse("18.07.2018 15:00"));

    }

    void testBooking() throws ParseException, IOException{
        System.out.println("Test booking");
        Receptionist receptionist =new Receptionist("ferhat");

        receptionist.booking("Ahmet Can",HotelManagement.dateFormat().parse("18.07.2018 15:00"));
        receptionist.printReservations();
        receptionist.cancelReservation("Ahmet Can",HotelManagement.dateFormat().parse("18.07.2018 15:00"));
    }

    void testCancelReservation() throws ParseException, IOException{
        System.out.println("Test cancelReservation");
        Receptionist receptionist =new Receptionist("ferhat");

        receptionist.booking("Ahmet Can",HotelManagement.dateFormat().parse("18.07.2018 15:00"));

        receptionist.cancelReservation("Ahmet Can",HotelManagement.dateFormat().parse("18.07.2018 15:00"));
        receptionist.printReservations();
    }

    public static void main(String[] args) throws IOException, ParseException {

        ReceptionistTest test =new ReceptionistTest();
        test.testBooking();
        test.testCancelReservation();
        test.testCheckIn();
        test.testCheckOut();
    }
}
