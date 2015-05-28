package restaurant.view;

public interface IBookingView {
    
    void setOutputDisplay(String s);
    void appendOutputDisplay(String s);
    String getNameInput();
    String getPhoneInput();
    String getDinersInput();
    String getDayInput();
    String getTableSizeInput();
    void setSizes(String[] s);
    
    void nameInputRequestFocus();
    void phoneInputRequestFocus();
    void dinersInputRequestFocus();
    void dayInputRequestFocus();
    
    int showOptionDialog( String s1, String s2 );
}
