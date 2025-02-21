import java.util.*;
class Room {
    private String roomNumber;
    private String category;
    private boolean isAvailable;
    private double pricePerNight;
    public Room(String roomNumber, String category, double pricePerNight){
        this.roomNumber = roomNumber;
        this.category = category;
        this.isAvailable = true;  // rooms are available initially
        this.pricePerNight = pricePerNight;
    }
    public String getRoomNumber() {
        return roomNumber;
    }
    public String getCategory() {
        return category;
    }
    public double getPricePerNight() {
        return pricePerNight;
    }
    public boolean isAvailable() {
        return isAvailable;
    }
    public void bookRoom() {
        this.isAvailable = false;
    }
    public void releaseRoom() {
        this.isAvailable = true;
    }
}
class Customer {
    private String name;
    private String contactDetails;

    public Customer(String name, String contactDetails) {
        this.name = name;
        this.contactDetails = contactDetails;
    }
    public String getName() {
        return name;
    }
    public String getContactDetails() {
        return contactDetails;
    }
}
class Reservation {
    private Room room;
    private Customer customer;
    private Date checkInDate;
    private Date checkOutDate;
    public Reservation(Room room,Customer customer,Date checkInDate,Date checkOutDate) {
        this.room=room;
        this.customer=customer;
        this.checkInDate=checkInDate;
        this.checkOutDate=checkOutDate;
    }
    public Room getRoom() {
        return room;
    }
    public Customer getCustomer() {
        return customer;
    }
    public Date getCheckInDate() {
        return checkInDate;
    }
    public Date getCheckOutDate() {
        return checkOutDate;
    }
    public double getTotalPrice() {
        long diffInMillies = Math.abs(checkOutDate.getTime()-checkInDate.getTime());
        long diff = diffInMillies/(1000*60*60*24); // Convert milliseconds to days
        return room.getPricePerNight()*diff;
    }
}
class Payment {
    public boolean processPayment(double amount) {
        //payment processing
        System.out.println("Processing payment of rs" + amount);
        return true;  //Assume payment is always successful
    }}
class Hotel {
    private List<Room> rooms;
    private List<Reservation> reservations;
    public Hotel() {
        rooms = new ArrayList<>();
        reservations = new ArrayList<>();
        // Add some rooms to the hotel for demonstration
        rooms.add(new Room("101", "Standard", 500.00));
        rooms.add(new Room("102", "Deluxe", 550.00));
        rooms.add(new Room("103", "Suite", 200.00));
        rooms.add(new Room("104", "Standard", 500.00));
        rooms.add(new Room("105", "Deluxe", 550.00));
    }
    public void searchAvailableRooms(String category) {
        System.out.println("Available " + category + " rooms:");
        for (Room room : rooms) {
            if (room.isAvailable() && room.getCategory().equalsIgnoreCase(category)) {
                System.out.println("Room " + room.getRoomNumber() + " - Price: Rs" + room.getPricePerNight());
            }
        }
    }
    public void makeReservation(Customer customer, String roomNumber, Date checkInDate, Date checkOutDate) {
        Room selectedRoom = null;
        for (Room room : rooms) {
            if (room.getRoomNumber().equals(roomNumber) && room.isAvailable()) {
                selectedRoom = room;
                break;
            }
        }
        if (selectedRoom != null) {
            Reservation reservation = new Reservation(selectedRoom, customer, checkInDate, checkOutDate);
            reservations.add(reservation);
            selectedRoom.bookRoom();
            System.out.println("Reservation successful for " + customer.getName() + " in room " + roomNumber);
            System.out.println("Total Price: Rs" + reservation.getTotalPrice());
        } else {
            System.out.println("Room not available.");
        }
    }
    public void viewReservationDetails(Customer customer) {
        System.out.println("Reservations for " + customer.getName() + ":");
        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)) {
                System.out.println("Room: " + reservation.getRoom().getRoomNumber() +
                        ", Check-in: " + reservation.getCheckInDate() +
                        ", Check-out: " + reservation.getCheckOutDate() +
                        ", Total Price: Rs" + reservation.getTotalPrice());
            }
        }
    }
}
public class HotelReservationSystem {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter customer name:");
        String name = scanner.nextLine();
        System.out.println("Enter customer contact details:");
        String contactDetails = scanner.nextLine();
        Customer customer = new Customer(name, contactDetails);
        while (true) {
            System.out.println("\nHotel Reservation System");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. View Reservation Details");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            switch (choice) {
                case 1:
                    System.out.println("Enter room category (Standard/Deluxe/Suite):");
                    String category = scanner.nextLine();
                    hotel.searchAvailableRooms(category);
                    break;
                case 2:
                    System.out.println("Enter room number to reserve:");
                    String roomNumber = scanner.nextLine();
                    System.out.println("Enter check-in date (yyyy-mm-dd):");
                    String checkInStr = scanner.nextLine();
                    System.out.println("Enter check-out date (yyyy-mm-dd):");
                    String checkOutStr = scanner.nextLine();
                    try {
                        Date checkInDate = new java.text.SimpleDateFormat("yyyy-mm-dd").parse(checkInStr);
                        Date checkOutDate = new java.text.SimpleDateFormat("yyyy-mm-dd").parse(checkOutStr);
                        hotel.makeReservation(customer, roomNumber, checkInDate, checkOutDate);
                    } catch (Exception e) {
                        System.out.println("Invalid date format.");
                    }
                    break;
                case 3:
                    hotel.viewReservationDetails(customer);
                    break;
                case 4:
                    System.out.println("Exiting system...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                scanner.close();
            }
        }
    }
}
