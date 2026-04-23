package ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticket {

    private String ticketId;
    private String userId;
    private String userName;   // 🔥 NEW FIELD

    private String source;
    private String destination;
    private String dateOfTravel;

    private Train train;

    public Ticket(){}

    // ✅ UPDATED CONSTRUCTOR
    public Ticket(String ticketId, String userId, String userName,
                  String source, String destination,
                  String dateOfTravel, Train train){

        this.ticketId = ticketId;
        this.userId = userId;
        this.userName = userName;
        this.source = source;
        this.destination = destination;
        this.dateOfTravel = dateOfTravel;
        this.train = train;
    }

    // ✅ CLEAN OUTPUT FORMAT
    public String getTicketInfo(){
        return "Ticket ID: " + ticketId + "\n" +
                "User : " + userName + "\n" +
                "| Train: " + (train != null ? train.getTrainId() : "N/A") + "\n" +
                "| From: " + source + "\n" +
                "| To: " + destination + "\n" +
                "| Date: " + dateOfTravel + "\n";
    }

    // GETTERS & SETTERS

    public String getTicketId(){
        return ticketId;
    }

    public void setTicketId(String ticketId){
        this.ticketId = ticketId;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getSource(){
        return source;
    }

    public void setSource(String source){
        this.source = source;
    }

    public String getDestination(){
        return destination;
    }

    public void setDestination(String destination){
        this.destination = destination;
    }

    public String getDateOfTravel(){
        return dateOfTravel;
    }

    public void setDateOfTravel(String dateOfTravel){
        this.dateOfTravel = dateOfTravel;
    }

    public Train getTrain(){
        return train;
    }

    public void setTrain(Train train){
        this.train = train;
    }
}