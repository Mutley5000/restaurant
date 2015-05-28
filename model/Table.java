package restaurant.model;

public class Table {
    
    int seats;
    int quantity;
    
    // Constructor
    public Table( int seats, int quantity ) {
        this.seats = seats;
        this.quantity = quantity;
    }
    
    // Set and get methods
    public int getSeats() {
        return seats;
    }
    
    public void setSeats(int seats) {
        this.seats = seats;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
