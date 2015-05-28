package restaurant.presenter;

import java.util.LinkedList;
import javax.swing.JOptionPane;
import restaurant.model.Booking;
import restaurant.model.IBookingQueries;
import restaurant.model.Table;
import restaurant.view.IBookingView;

public class BookingPresenter {
    
    IBookingView view;
    IBookingQueries queries;
    LinkedList<Booking> results;
    int total;
    int option;
    
    public BookingPresenter(IBookingView ibv, IBookingQueries ibq) {
        view = ibv;
        queries = ibq;
        results = null;
        total = 0;
        option = 0;
    }
    
    private void updateOutputDisplay() {
        view.setOutputDisplay("");
        String header;
        String message;
        
        // Switch used to create to options of the updateOutputDisplay() method to used used with different buttons
        switch (option) {
            
            // Option for when the Book button is pressed.
            case 1:
                message = String.format("Booking has been added.");
                view.appendOutputDisplay(message);
            break;    
                
            // Option for when the Display All Bookings button is pressed    
            case 2: 
                header = String.format("BookingID      Name\t\t   Phone\t  Diners\t Day\n");
                view.appendOutputDisplay(header);
                for(int i = 0; results.size() > i; i++) {
                    Object line = results.get(i);
                    view.appendOutputDisplay(line.toString());
                }
            break;
            
            // Option for when the Bookings for Day button is pressed    
            case 3: 
                header = String.format("BookingID      Name\t\t   Phone\t  Diners\t Day\n");
                view.appendOutputDisplay(header);
                for(int i = 0; results.size() > i; i++) {
                    Object line = results.get(i);
                    view.appendOutputDisplay(line.toString());
                }    
            break;
            
            // Option for when the Total Diners for Day button is pressed    
            case 4: 
                header = String.format("Total Diners Booked for Day:\n");
                view.appendOutputDisplay(header);
                view.appendOutputDisplay(""+total);
            break;
                
            // Option for when it is attempted to add a booking when the there is not enough capacity for the booking    
            case 5:
                message = String.format("There isn't enough seats for this booking on the selected day.\n");
                view.appendOutputDisplay(message);
                message = String.format("Please select another day.");
                view.appendOutputDisplay(message);
            break;
            
            // Option for when it is attempted to add a booking but there is a entry field empty    
            case 6:
                message = String.format("A required field is empty.  Please fill the empty field/s.");
                view.appendOutputDisplay(message);
            break;
        }
    }    
    
    public void addBooking() {
        option = 1;
            
            // Check if any field is empty
            if (view.getNameInput().equals("") || view.getPhoneInput().equals("")
                || view.getDinersInput().equals("")) {
                
                option = 6;
                updateOutputDisplay();
                
                int missingInput = 0;
                
                // Find the first empty field
                loop:    
                for(int i = 0; i < 1; i++){
                    if (view.getNameInput().equals("")) {
                    missingInput = 1;
                    break;
                    }
                    
                    if (view.getPhoneInput().equals("")) {
                    missingInput = 2;
                    break;
                    }
                    
                    if (view.getDinersInput().equals("")) {
                    missingInput = 3;
                    break;
                    }
                }
                
                // Set the focus to the first empty field
                switch (missingInput) {
                    
                    case 1: view.nameInputRequestFocus();
                        break;
                        
                    case 2: view.phoneInputRequestFocus();
                        break;
                        
                    case 3: view.getDinersInput();
                        break;
                }
            }
            
            // When there are no empty fields preceed to the next check
            else {
                int capacity = queries.getRestaurantCapacity();
                total = queries.getTotalDinersForDay(view.getDayInput());
            
                // Check to see if there is space for the new booking and if so add the booking
                if (total + Integer.parseInt(view.getDinersInput()) <= capacity) {
                    queries.addBooking(view.getNameInput(), view.getPhoneInput(), 
                                              Integer.parseInt(view.getDinersInput()), view.getDayInput());
                    option = 1;
                    updateOutputDisplay();
                }
            
                // When there is no space on the selected day, suggest chosing another day and set focus to dayInput ComboBox
                else {
                    option = 5;
                    updateOutputDisplay();
                    view.dayInputRequestFocus();
                }
            }
        }
    
    public void allBookings() {
        option = 2;
        results = queries.getAllBookings();
        updateOutputDisplay();
    }
    
    public void dayBookings() {
        option = 3;
        results = queries.getBookingsForDay(view.getDayInput());
        updateOutputDisplay();
    }
    
    public void dayTotal() {
        option = 4;
        total = queries.getTotalDinersForDay(view.getDayInput());
        updateOutputDisplay();
    }
    
    // handles window closure
    public void close() {
        queries.close();
    }
    
    public void exit() {
        int response = view.showOptionDialog("Are you sure you want to exit?", "Confirm exit");
        // Exit if Yes is selected
        if (response == JOptionPane.YES_OPTION) {
            queries.close();
            System.exit(0);
        }
    }
}