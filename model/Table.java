package restaurant.model;

public class Table {
    
    int id;
    int seats;
    
    // Constructor
    public Table( int id, int seats) {
        this.id = id;
        this.seats = seats;
    }
    
    // Set and get methods
    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }
    
    public int getSeats() {
        return seats;
    }
    
    public void setSeats(int seats) {
        this.seats = seats;
    }
    
    // Specifying modifications to toString method
    @Override
    public String toString() {
        return String.format("%d, %d\n",id, seats);
    }
}