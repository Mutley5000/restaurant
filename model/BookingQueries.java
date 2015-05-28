package restaurant.model;

// Declare required imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BookingQueries implements IBookingQueries {
    
    // Declare Variables
    private static final String URL = "jdbc:derby://localhost:1527/restaurant";
    private static final String USERNAME = "administrator";
    private static final String PASSWORD = "admin";
    
    private Connection connection = null;
    private int capacity = 20;
    
    // Declare SQL statements
    String insertNewBookingSQL = "INSERT INTO BOOKINGS(LASTNAME, PHONE, DINERS, DAYOFWEEK) VALUES(?,?,?,?)";
    String selectAllBookingsSQL =  "SELECT ID, LASTNAME, PHONE, DINERS, DAYOFWEEK " +
                                "FROM BOOKINGS";
    String selectBookingDaySQL =   "SELECT ID, LASTNAME, PHONE, DINERS, DAYOFWEEK FROM BOOKINGS WHERE DAYOFWEEK = ?";
    String selectTotalDinersForDaySQL = "SELECT SUM(DINERS) FROM BOOKINGS WHERE DAYOFWEEK = ?";
    String selectUnoccupiedTablesSQL = "SELECT ID, SEATS FROM TABLES WHERE ID NOT IN (SELECT TABLEID FROM BOOKINGS WHERE DAYOFWEEK = ?)";
    String selectRestaurantCapacitySQL = "SELECT SUM(SEATS) FROM TABLES WHERE ID NOT IN (SELECT TABLEID FROM BOOKINGS WHERE DAYOFWEEK = ?)"; 
    
    // Create prepared statement
    PreparedStatement ps;
    
    // Constructor
    public BookingQueries() {
    
        // Try to create connection with the database
        try {
            connection = DriverManager.getConnection( URL, USERNAME, PASSWORD );
            System.out.println("Database accessed.");
        }
    
        // If a connection can not be made display message and close
        catch ( SQLException err ) {
            System.out.println( err.getMessage( ) );
            
            // Create resourses for error message
            JPanel errorPanel = new  JPanel();
            JLabel errorMessage = new JLabel("There is no connection to the 'restaurant'"  
                                            + " database. Please connect and try again.");
            errorPanel.add(errorMessage);
            Object[] errorOptions = {"OK"};
            
            // Display error message
            JOptionPane.showOptionDialog(	null,
                                                errorPanel,
	                   			"No connection to database",
	                   			JOptionPane.PLAIN_MESSAGE,
	                   			JOptionPane.PLAIN_MESSAGE,
	                   			null,
	                   			errorOptions,
	                   			errorOptions[0]);
            
            // Close 
            close();
        }
    }
    
    // Add booking to the database
    @Override
    public void addBooking( String name, String phone, int reservation, String day ) {
        
        try {
            ps = connection.prepareStatement(insertNewBookingSQL);
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setInt(3, reservation);
            ps.setString(4, day);
            ps.executeUpdate();
        }
        
        catch ( SQLException err ) {
            System.out.println( err.getMessage( ) );
        }
    }
    
    // Close database connection
    @Override
    public void close() {
        
        try {
            connection.close();
            System.out.println("Connection closed.");
        }
        
        catch ( SQLException err ) {
            System.out.println( err.getMessage( ) );
        }
    }
    
    // Get all bookings from the database
    @Override
    public LinkedList< Booking > getAllBookings() {
        
        LinkedList<Booking> results = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(selectAllBookingsSQL);
            rs = ps.executeQuery();
            results = new LinkedList<>();
            
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("LASTNAME");
                String phone = rs.getString("PHONE");
                int diners = rs.getInt("DINERS");
                String day = rs.getString("DAYOFWEEK");
                
                Booking booking = new Booking(id, name, phone, diners, day);
                
                results.add(booking);
            }
        }
        
        catch ( SQLException err ) {
            System.out.println( err.getMessage( ) );
        }
        
        return results;
    }
    
    // Get bookings for selected day from the database
    @Override
    public LinkedList< Booking > getBookingsForDay( String day ) {
        
        LinkedList<Booking> results = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(selectBookingDaySQL);
            ps.setString(1, day);
            rs = ps.executeQuery();
            results = new LinkedList<>();
            
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("LASTNAME");
                String phone = rs.getString("PHONE");
                int diners = rs.getInt("DINERS");
                String daySelected = rs.getString("DAYOFWEEK");
                
                Booking booking = new Booking(id, name, phone, diners, daySelected);
                
                results.add(booking);
            }
        }
        
        catch ( SQLException err ) {
            System.out.println( err.getMessage( ) );
        }
        
        return results;
    }
    
    // Get the restaurant capacity
    @Override
    public int getRestaurantCapacity(String day) {
        
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(selectRestaurantCapacitySQL);
            ps.setString(1, day);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                capacity = rs.getInt(1);
            }
        }
        
        catch ( SQLException err ) {
            System.out.println( err.getMessage( ) );
        }
        
        return capacity;
    }
    
    // Get the total number of diners for selected day from the database
    @Override
    public int getTotalDinersForDay( String day ) {
        
        ResultSet rs = null;
        int total = 0;
        
        try {
            ps = connection.prepareStatement(selectTotalDinersForDaySQL);
            ps.setString(1, day);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                total = rs.getInt(1);
            }
        }
        
        catch ( SQLException err ) {
            System.out.println( err.getMessage( ) );
        }
        return total;
    }
    
    // Check if there are unoccupied tables
    @Override
    public LinkedList< Table > getUnoccupiedTables(String day) {
        
        LinkedList<Table> results = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(selectUnoccupiedTablesSQL);
            ps.setString(1, day);
            rs = ps.executeQuery();
            results = new LinkedList<>();
            
            while (rs.next()) {
                int id = rs.getInt("ID");
                int seats = rs.getInt("SEATS");
                
                Table table = new Table(id, seats);
                
                results.add(table);
            }
        }
        
        catch ( SQLException err ) {
            System.out.println( err.getMessage( ) );
        }
        
        return results;
    }
}