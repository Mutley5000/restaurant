package restaurant.model;

public class Booking {
    
    // Declare variables
    int id;
    String name;
    String phone;
    int diners = 0;
    String day;
    
    // Constructor
    public Booking( int id, String name, String phone, int diners, String day ) {
        
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.diners = diners;
        this.day = day;
    }

    // Set and get methods for variables
    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public int getDiners() {
        return diners;
    }

    public void setDiners(int diners) {
        this.diners = diners;
    }
    
     public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
    
    // Specifying modifications to toString method
    @Override
    public String toString() {
        return String.format("%-15s%-20s%-15s%-15s%-10s\n",id, name,phone, diners,day);
    }
}