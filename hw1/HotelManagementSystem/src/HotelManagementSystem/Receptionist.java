package HotelManagementSystem;

import java.io.IOException;
import java.util.Date;

/**
 * Receptionist class holds helper method like checkIn and CheckOut make receptionist's work more easy
 */
public class Receptionist extends HotelManagement {

    private String receptionistName;

    /**
     * Instantiates a new Receptionist.
     *
     * @param rName the receptionist's name
     */
    public Receptionist(String rName){

        receptionistName =rName;
    }


    /**
     * Check in.
     * Write the result to the register file
     * @param name        the guest name
     * @param bookingDate the booking date
     * @throws IOException throws if file can't open or written
     */
    public void checkIn(String name, Date bookingDate) throws IOException {

        String line;

        for(int i=0;i<reservationList.size();++i){
            line =reservationList.get(i);
            if(line.contains(name.toUpperCase()) && line.contains(dateFormat().format(bookingDate).toUpperCase()) && !line.contains(",checkIn".toUpperCase())){
                String temp =reservationList.remove(i);
                temp =temp.concat(",checkIn");
                reservationList.add(temp.toUpperCase());
                updateRegisterFile();
                break;
            }
        }
    }

    /**
     * Check out.
     * Write the result to the register file.
     * @param name        the guest name
     * @param bookingDate the booking date
     * @throws IOException throws if file can't open or written
     */
    public void checkOut(String name, Date bookingDate) throws IOException {

        String line; int roomNo;

        for(int i=0;i<reservationList.size();++i){
            line =reservationList.get(i);
            if(line.contains(name.toUpperCase()) && line.contains(dateFormat().format(bookingDate).toUpperCase()) &&
                    line.contains("checkIn".toUpperCase()) && !line.contains("checkOut".toUpperCase())){
                String[] splitLine=line.split(",");
                roomNo =Integer.parseInt(splitLine[1]);
                returnRoom(roomNo);
                String temp =reservationList.remove(i);
                temp =temp.concat(",checkOut");
                reservationList.add(temp.toUpperCase());
                updateRegisterFile();
                break;
            }
        }
    }

}
