package ticket.booking;

import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.service.UserBookingService;
import ticket.booking.util.UserServiceUtil;

import java.io.IOException;
import java.util.*;

// Upgradation of IRCTC - Train service project
public class App {

    public static void main(String[] args) {

        System.out.println("Running Train Booking System");

        Scanner scanner = new Scanner(System.in);
        int option = 0;

        UserBookingService userBookingService;

        try {
            userBookingService = new UserBookingService();
        } catch (IOException ex) {
            System.out.println("There is something wrong");
            ex.printStackTrace();
            return;
        }

        // 🔥 GLOBAL STATE (VERY IMPORTANT)
        Train trainSelectedForBooking = null;
        String source = null;
        String dest = null;
        String travelDate = null;

        while (option != 7) {

            System.out.println("Choose option");
            System.out.println("1. Sign Up");
            System.out.println("2. Log in");
            System.out.println("3. Fetch Bookings");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a Seat");
            System.out.println("6. Cancel my Booking");
            System.out.println("7. Exit the App");

            option = scanner.nextInt();

            switch (option) {

                // ✅ SIGN UP
                case 1:
                    System.out.println("Enter username:");
                    String nameToSignUp = scanner.next();

                    System.out.println("Enter password:");
                    String passwordToSignUp = scanner.next();

                    User userToSignup = new User(
                            nameToSignUp,
                            passwordToSignUp,
                            UserServiceUtil.hashPassword(passwordToSignUp),
                            new ArrayList<>(),
                            UUID.randomUUID().toString()
                    );

                    userBookingService.signUp(userToSignup);
                    break;

                // ✅ LOGIN
                case 2:
                    System.out.println("Enter username:");
                    String nameToLogin = scanner.next();

                    System.out.println("Enter password:");
                    String passwordToLogin = scanner.next();

                    User userToLogin = new User(
                            nameToLogin,
                            passwordToLogin,
                            "",   // no need to hash here
                            new ArrayList<>(),
                            ""
                    );

                    try {
                        userBookingService = new UserBookingService(userToLogin);

                        Boolean isLoggedIn = userBookingService.loginUser();

                        if (isLoggedIn) {
                            System.out.println("✅ Login Successful");
                        } else {
                            System.out.println("❌ Invalid Credentials");
                        }

                    } catch (IOException ex) {
                        ex.printStackTrace();
                        return;
                    }
                    break;

                // ✅ FETCH BOOKINGS
                case 3:
                    System.out.println("Fetching your bookings");
                    userBookingService.fetchBookings();
                    break;

                // ✅ SEARCH TRAINS
                case 4:
                    System.out.println("Enter source station:");
                    source = scanner.next();

                    System.out.println("Enter destination station:");
                    dest = scanner.next();

                    System.out.println("Enter travel date (yyyy-mm-dd):");
                    travelDate = scanner.next();

                    List<Train> trains = userBookingService.getTrains(source, dest);

                    if (trains.isEmpty()) {
                        System.out.println("❌ No trains found");
                        break;
                    }

                    int index = 1;
                    for (Train t : trains) {
                        System.out.println(index + ". Train id: " + t.getTrainId());

                        for (Map.Entry<String, String> entry : t.getStationTimes().entrySet()) {
                            System.out.println("Station: " + entry.getKey() + " Time: " + entry.getValue());
                        }

                        index++;
                    }

                    System.out.println("Select a train (1,2,3...):");
                    int choice = scanner.nextInt();

                    trainSelectedForBooking = trains.get(choice - 1);
                    break;

                // ✅ BOOK SEAT
                case 5:
                    if (trainSelectedForBooking == null) {
                        System.out.println("❌ Please search and select a train first (Option 4)");
                        break;
                    }

                    List<List<Integer>> seats = userBookingService.fetchSeats(trainSelectedForBooking);

                    System.out.println("Seat Layout:");
                    for (List<Integer> row : seats) {
                        for (Integer val : row) {
                            System.out.print(val + " ");
                        }
                        System.out.println();
                    }

                    System.out.println("Enter row:");
                    int row = scanner.nextInt();

                    System.out.println("Enter column:");
                    int col = scanner.nextInt();

                    Boolean booked = userBookingService.bookTrainSeat(
                            trainSelectedForBooking,
                            row,
                            col,
                            source,
                            dest,
                            travelDate
                    );

                    if (booked) {
                        System.out.println("✅ Booked! Enjoy your journey");
                    } else {
                        System.out.println("❌ Booking failed");
                    }
                    break;

                // ✅ CANCEL BOOKING
                case 6:
                    System.out.println("Cancelling your booking...");
                    Boolean cancelled = userBookingService.cancelBooking(null);

                    if (cancelled) {
                        System.out.println("✅ Booking cancelled");
                    } else {
                        System.out.println("❌ Cancellation failed");
                    }
                    break;

                default:
                    break;
            }
        }
    }
}