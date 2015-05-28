package restaurant.model;

import java.util.LinkedList;

public interface IBookingQueries {
    void addBooking(String name, String phone, int diners, String day);
    void close();
    LinkedList< Booking > getAllBookings();
    LinkedList< Booking > getBookingsForDay(String day);
    int getRestaurantCapacity(String day);
    int getTotalDinersForDay(String day);
    LinkedList< Table > getUnoccupiedTables(String day);
}
