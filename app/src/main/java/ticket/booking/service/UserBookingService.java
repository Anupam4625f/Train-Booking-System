package ticket.booking.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.util.UserServiceUtil;
import java.util.UUID;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class UserBookingService
{
    private User user; //Global

    private List<User> userList;


    private ObjectMapper objectMapper =  new ObjectMapper();

    // Constructor call

    private static final String USERS_PATH = "C:\\Java_Projects\\IRCTC\\app\\src\\main\\java\\ticket\\booking\\localDb\\user.json";

    public UserBookingService(User user1) throws IOException
    {
        this.user = user1;
        loadUsers();
    }

    //Default Constructor
    public UserBookingService() throws IOException{
         loadUsers();
    }

    public List<User> loadUsers() throws IOException {
        File users = new File(USERS_PATH);

        if (users.length() == 0) {
            userList = new ArrayList<>();
            return userList;
        }

        userList = objectMapper.readValue(users, new TypeReference<List<User>>() {});
        return userList;
    }

    //Login_signUp
    public Boolean loginUser(){
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equalsIgnoreCase(user.getName()) &&
                    UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();

        if(foundUser.isPresent()){
            this.user = foundUser.get();   // 🔥 IMPORTANT
            return true;
        }
        return false;
    }

    //Sign Up
    public Boolean signUp(User user1){
        try{
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        }catch (Exception ex){
            ex.printStackTrace();  // 🔥 SHOW ERROR
            return Boolean.FALSE;
        }
    }

    private void saveUserListToFile() throws IOException {
        File usersFile = new File(USERS_PATH);
        objectMapper.writeValue(usersFile, userList);
    }

    //Object (user) --> json --> Deserialize
    //Object (User) --> json --> Serialize

    public void fetchBookings(){
        if (user == null) {
            System.out.println("Please login first.");
            return;
        }

        user.printTickets();
    }

    // todo: Complete this function
    public Boolean cancelBooking(String ticketId){

        Scanner s = new Scanner(System.in);
        System.out.println("Enter the ticket id to cancel");
        ticketId = s.next();

        if (ticketId == null || ticketId.isEmpty()) {
            System.out.println("Ticket ID cannot be null or empty.");
            return Boolean.FALSE;
        }

        String finalTicketId1 = ticketId;  //Because strings are immutable
        boolean removed = user.getTicketsBooked().removeIf(ticket -> ticket.getTicketId().equals(finalTicketId1));

        String finalTicketId = ticketId;
        user.getTicketsBooked().removeIf(Ticket -> Ticket.getTicketId().equals(finalTicketId));
        if (removed) {
            System.out.println("Ticket with ID " + ticketId + " has been canceled.");
            return Boolean.TRUE;
        }else{
            System.out.println("No ticket found with ID " + ticketId);
            return Boolean.FALSE;
        }
    }

    public List<Train> getTrains(String source, String destination) {
        try{
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source, destination);
        } catch(IOException ex){
            return new ArrayList<>();
        }
    }

    public List<List<Integer>> fetchSeats(Train train){
        return train.getSeats();
    }

    public Boolean bookTrainSeat(
            Train train,
            int row,
            int seat,
            String source,
            String dest,
            String travelDate
    ) {
        try {
            TrainService trainService = new TrainService();
            List<List<Integer>> seats = train.getSeats();

            if (row >= 0 && row < seats.size() &&
                    seat >= 0 && seat < seats.get(row).size()) {

                if (seats.get(row).get(seat) == 0) {

                    // ✅ Book seat
                    seats.get(row).set(seat, 1);
                    train.setSeats(seats);
                    trainService.addTrain(train);

                    // 🔥 CREATE TICKET
                    Ticket ticket = new Ticket(
                            UUID.randomUUID().toString(),
                            user.getUserId(),
                            user.getName(),
                            source,
                            dest,
                            travelDate,
                            train
                    );

                    if (user.getTicketsBooked() == null) {
                        user.setTicketsBooked(new ArrayList<>());
                    }

                    user.getTicketsBooked().add(ticket);

                    // 🔥 Update user list
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getUserId().equals(user.getUserId())) {
                            userList.set(i, user);
                            break;
                        }
                    }

                    // 🔥 Save to JSON
                    saveUserListToFile();

                    return true;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

}
