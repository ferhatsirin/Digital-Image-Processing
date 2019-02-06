import HotelManagementSystem.HotelGuest;
import HotelManagementSystem.HotelManagement;
import HotelManagementSystem.Receptionist;

import java.io.*;
import java.text.ParseException;

public class HotelManagementTest {

    public static void main(String[] args){


        try {
            Receptionist receptionist = new Receptionist("r1");

            receptionist.booking("ferhat sirin", HotelManagement.dateFormat().parse("10.05.2018 16:00"));

            receptionist.checkIn("Ferhat Sirin", HotelManagement.dateFormat().parse("10.05.2018 16:00"));
            receptionist.checkOut("Ferhat Sirin", HotelManagement.dateFormat().parse("10.05.2018 16:00"));

            HotelGuest guest1 = new HotelGuest("Ahmet Can");

            guest1.booking(HotelManagement.dateFormat().parse("20.05.2018 17:00"));

            HotelGuest guest2 = new HotelGuest("Mehmet Ali");

            guest2.booking(HotelManagement.dateFormat().parse("13.06.2018 12:00"));

            guest1.booking(HotelManagement.dateFormat().parse("23.06.2018 12:00"));

            receptionist.booking("Murat Yilmaz", HotelManagement.dateFormat().parse("16.07.2018 11:00"));
            guest1.printReservations();
            guest2.printReservations();
            receptionist.printReservations();

            receptionist.checkIn(guest1.getName(), HotelManagement.dateFormat().parse("20.05.2018 17:00"));
            guest1.booking(HotelManagement.dateFormat().parse("15.07.2018 12:00"));
            guest1.printReservations();


            receptionist.cancelReservation(guest2.getName(), HotelManagement.dateFormat().parse("13.06.2018 12:00"));
            receptionist.booking(guest2.getName(), HotelManagement.dateFormat().parse("15.06.2018 12:00"));

            guest2.printReservations();
            guest1.cancelReservation(HotelManagement.dateFormat().parse("15.07.2018 12:00"));
            receptionist.checkOut(guest1.getName(), HotelManagement.dateFormat().parse("20.05.2018 17:00"));
            guest1.printReservations();

        }
        catch(IOException e){
            System.err.println("File error!!! ");
        }
        catch(ParseException e){

            System.err.println(e.getMessage() + " Invalid date!!! Date should be " +HotelManagement.dateFormat().toPattern());
        }

    }
}
