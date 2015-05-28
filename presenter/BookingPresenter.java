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
    LinkedList<Table> tableResults;
    int total;
    int option;
    
    public BookingPresenter(IBookingView ibv, IBookingQueries ibq) {
        view = ibv;
        queries = ibq;
        results = null;
        tableResults = null;
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
                header = String.format("BookingID      Name\t\t   Phone\t  Diners\t Day\t\t Table\n");
                view.appendOutputDisplay(header);
                for(int i = 0; results.size() > i; i++) {
                    Object line = results.get(i);
                    view.appendOutputDisplay(line.toString());
                }
            break;
            
            // Option for when the Bookings for Day button is pressed    
            case 3: 
                header = String.format("BookingID      Name\t\t   Phone\t  Diners\t Day\t\t Table\n");
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
                
            // Option for when it is attempted to add a booking when the there is not any unoccupied tables    
            case 5:
                message = String.format("There isn't any tables unoccupied on the selected day.\n");
                view.appendOutputDisplay(message);
                message = String.format("Please select another day.");
                view.appendOutputDisplay(message);
            break;
            
            // Option for when it is attempted to add a booking when the there is not enough capacity for the booking
            case 6:
                message = String.format("There isn't enough seats for this booking on the selected day.\n");
                view.appendOutputDisplay(message);
                message = String.format("Please select another day.");
                view.appendOutputDisplay(message);
            break;    
                
            // Option for when it is attempted to add a booking but there is a entry field empty    
            case 7:
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
                
                option = 7;
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
            
            else {
                tableResults = queries.getUnoccupiedTables(view.getDayInput());
                int size = tableResults.size();
                
                if (size <= 0) {
                    option = 5;
                    updateOutputDisplay();
                    view.dayInputRequestFocus();
                }
                
                else {
                    
                    int capacity = queries.getRestaurantCapacity(view.getDayInput());
                    int diners = Integer.parseInt(view.getDinersInput());
                    
                    if (diners > capacity) {
                        option = 6;
                        updateOutputDisplay();
                        view.dayInputRequestFocus();
                    }
                    
                    else {
                        int seats;
                        
                        for(int i = 0; tableResults.size() > i; i++) {
                            Table table = tableResults.get(i);
                            seats = table.getSeats();
                            int seatedDiners;
                            if (diners > seats) {
                                seatedDiners = seats;
                            }
                            
                            else {
                                seatedDiners = diners;
                            }
                            
                            queries.addBooking(view.getNameInput(), view.getPhoneInput(), 
                                              seatedDiners, view.getDayInput(), table.getID());
                            
                            diners = diners - seats;
                            
                            if (diners <= 0 ) {
                                option = 1;
                                updateOutputDisplay();
                                break;
                            }
                        }
                    }
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