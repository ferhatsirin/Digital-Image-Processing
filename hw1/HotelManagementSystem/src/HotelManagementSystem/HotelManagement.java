package HotelManagementSystem;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Hotel management is an abstract class for HotelManagementSystem.
 * Holds the helper method to make booking and cancelReservation etc.
 */
public abstract class HotelManagement implements HotelManagementSystem{

    private static List<Integer> emptyRooms;
    private  static List<Integer> reservedRooms;
    /**
     * The Reservation list.
     */
    protected static List<String> reservationList;
    private static final int numOfRooms =100;
    private static final File registerFile =new File("RegisterFile.cvs");
    private static final SimpleDateFormat datePattern =new SimpleDateFormat("dd.MM.yyyy HH:mm");

    static{

        try {
            initRooms();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initRooms() throws IOException { //When program starts this method transfer the old date from
                                                         // register file to the program
        emptyRooms =new LinkedList<>();
        reservedRooms =new LinkedList<>();
        reservationList =new LinkedList<>();

        for(int i=0;i<numOfRooms;++i){
            emptyRooms.add(100+i);
        }

        if(registerFile.createNewFile()){

            FileWriter writer =new FileWriter(registerFile);
            writer.write("Guest Name,Room No,Date\n");
            writer.close();
        }

        BufferedReader reader =new BufferedReader(new FileReader(registerFile));
        String line; int roomNo;
        line =reader.readLine();
        while((line =reader.readLine()) != null){

            reservationList.add(line.toUpperCase());
            String[] splitLine=line.split(",");
            roomNo =Integer.parseInt(splitLine[1]);
            emptyRooms.remove(new Integer(roomNo));
            reservedRooms.add(roomNo);
        }

        reader.close();
    }

    /**
     * Helper method for giving Date to the methods of HotelManagement.
     * The return object can be used for parse and format.
     * @return SimpleDateFormat object to use parse and format method
     */
    public static SimpleDateFormat dateFormat(){

        return datePattern;
    }

    /**
     * method to book a room.
     * Write the result to the register file
     *
     * @param name        the guest name
     * @param bookingDate the booking date
     * @return if successful return true otherwise false
     * @throws IOException throws if file can't open or written
     */
    public boolean booking(String name,Date bookingDate) throws IOException {

        int roomNo;
        FileWriter writer = new FileWriter(registerFile,true);


        if(bookingDate.compareTo(new Date()) < 1 ){
            return false;
        }
        for(int i=0;i<reservationList.size();++i){
            String temp =reservationList.get(i);
            if(temp.contains(name.toUpperCase()) && temp.contains(dateFormat().format(bookingDate).toUpperCase()))
                return false;
        }

        roomNo =getRoom();
        if(roomNo == -1)
            return false;

        String line =String.format(name + ","+ roomNo + "," + dateFormat().format(bookingDate)).toUpperCase();

        reservationList.add(line);
        writer.write(line + "\n");

        writer.close();

        return true;
    }

    /**
     * method to cancel reservation.
     * write the result to the register file
     * @param name        the guest name
     * @param bookingDate the booking date
     * @throws IOException throws if file can't open or written
     */
    public void cancelReservation(String name,Date bookingDate) throws IOException {

        String line; int roomNo;
        for(int i=0;i<reservationList.size();++i){
            line =reservationList.get(i);
            if(line.contains(name.toUpperCase()) && line.contains(dateFormat().format(bookingDate).toUpperCase())){

                String[] splitLine=line.split(",");
                roomNo =Integer.parseInt(splitLine[1]);
                returnRoom(roomNo);
                reservationList.remove(i);
                break;
            }
        }

        updateRegisterFile();
    }

    /**
     * If file is directly written in then updateRegisterFile method takes those input to system inside
     *
     * @throws IOException throws if file cant open or written
     */
    public void updateRegisterFile() throws IOException {

        FileWriter writer =new FileWriter(registerFile);
        writer.write("Guest Name,Room No,Date\n");
        String line;
        for(int i=0;i<reservationList.size();++i){

            line =reservationList.get(i);
            writer.write(line + "\n");
        }

        writer.close();
    }

    /**
     * Print all reservations.
     */
    public void printReservations() {

        System.out.println("Hotel Reservations List :");
        for(int i=0;i<reservationList.size();++i){

            System.out.println((i+1)+ " " + reservationList.get(i));
        }
        System.out.println();
    }


    /**
     * Return an empty room.
     *
     * @return the number of the room
     */
    protected int getRoom(){

        int room;

        if( emptyRooms.size() != 0) {
            Random rand = new Random();
            int i = rand.nextInt(emptyRooms.size());
            room = emptyRooms.get(i);
            reservedRooms.add(room);
            emptyRooms.remove(i);
        }
        else{

            room =-1;
        }

        return room;
    }

    /**
     * The method to give the room back
     *
     * @param roomNo the room taken before
     */
    protected void returnRoom(int roomNo){

        if(100 <= roomNo && roomNo <100+numOfRooms && reservedRooms.contains(roomNo)){

            emptyRooms.add(roomNo);
            reservedRooms.remove((Integer)roomNo);
        }
    }


}
