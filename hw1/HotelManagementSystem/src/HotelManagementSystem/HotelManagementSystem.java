package HotelManagementSystem;

import java.io.IOException;
import java.util.Date;

public interface HotelManagementSystem {

    boolean booking(String name,Date bookingDate) throws IOException;
    void cancelReservation(String name,Date bookingDate) throws IOException;
    void checkIn(String name, Date bookingDate) throws IOException;
    void checkOut(String name, Date bookingDate) throws IOException;
    void printReservations();
}
