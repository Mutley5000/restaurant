package restaurant.view;

// Declare required imports
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import restaurant.presenter.BookingPresenter;

public class BookingView extends JFrame implements IBookingView {
    
    // The presenter for this view
    private BookingPresenter presenter;
    
    // Create input fields
    JTextField nameInput = new JTextField(10);
    JTextField phoneInput = new JTextField(10);
    JTextField dinersInput = new JTextField(10);
    
    // Create ComboBox for day selection
    String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    JComboBox dayInput = new JComboBox(days);
    
    // Create ComboBox for table size
    String[] sizes = {""};
    JComboBox tableSizeInput = new JComboBox(sizes);
    
    // Create booking button
    JButton book = new JButton("Book");
    
    // Create output display area
    static JTextArea outputDisplay = new JTextArea();
    
    // Constructor
    public BookingView() {
	super("Restaurant Booking System");
        
        // Create borders
		Border space;
		{

			space = BorderFactory.createEmptyBorder(10, 10, 10, 10);
			
		}
        
        // Window dimensions
	int FRAME_WIDTH = 1000;
	int FRAME_HEIGHT = 300; 
        
        
        // Create windowContent panel and set the layout manager
        JPanel windowContent = new JPanel();
	BorderLayout windowLayout = new BorderLayout();
	windowContent.setLayout(windowLayout);
        windowContent.setBorder(space);
        
        
        // Create westPanel and set the layout manager
	JPanel westPanel = new JPanel();
        westPanel.setBorder ( new TitledBorder ( new EtchedBorder (), "Input:" ) );
        GridLayout westLayout = new GridLayout(6,2);
	westPanel.setLayout(westLayout);
        
        // Create content for westPanel
	JLabel nameLabel = new JLabel("Name:");
        JLabel phoneLabel = new JLabel("Phone:");
        JLabel dinersLabel = new JLabel("Diners:");
        JLabel dayLabel = new JLabel("Day:");
        JLabel tableSizeLabel = new JLabel("Table Size:");
        JLabel fill = new JLabel("");
        
        // Add content to westPanel
        westPanel.add(nameLabel);
        westPanel.add(nameInput);
        westPanel.add(phoneLabel);
        westPanel.add(phoneInput);
        westPanel.add(dinersLabel);
        westPanel.add(dinersInput);
        westPanel.add(dayLabel);
        westPanel.add(dayInput);
        
            // Set the default selected day of the dayInput ComboBox to the current day
            Calendar calendar = Calendar.getInstance();
            int today = calendar.get(Calendar.DAY_OF_WEEK);
            dayInput.setSelectedIndex(today - 1);
        
        westPanel.add(tableSizeLabel);
        westPanel.add(tableSizeInput);
        westPanel.add(fill);
        westPanel.add(book);
        
        
        // Create centerPanel and set the layout manager
	JPanel centerPanel = new JPanel();
        centerPanel.setBorder ( new TitledBorder ( new EtchedBorder (), "Output:" ) );
        BoxLayout centerLayout = new BoxLayout(centerPanel, BoxLayout.Y_AXIS);
	centerPanel.setLayout(centerLayout);
        
        // Create content for centerPanel
	outputDisplay.setEditable(false);
	outputDisplay.setLineWrap(true);
	outputDisplay.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
	JScrollPane outputScroll = new JScrollPane ( outputDisplay );
	outputScroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        
        // Add content to centerPanel
        centerPanel.add(outputScroll);
        
        
        // Create southPanel and set the layout manager
	JPanel southPanel = new JPanel();
        southPanel.setBorder ( new TitledBorder ( new EtchedBorder (), "Options:" ) );
        
        // Create content for southPanel
        JButton displayAllBookings = new JButton("Display All Bookings");
        JButton bookingsForDay = new JButton("Bookings for Day");
        JButton totalDinersForDay = new JButton("Total Diners for Day");
        JButton exit = new JButton("Exit");
        
        // Add content to southPanel
        southPanel.add(displayAllBookings);
        southPanel.add(bookingsForDay);
        southPanel.add(totalDinersForDay);
        southPanel.add(exit);
        
        
        // Add content to windowContent
        windowContent.add("West", westPanel);
        windowContent.add("Center", centerPanel);
        windowContent.add("South", southPanel);
        
        
        // Add content to frame
        add(windowContent);
	pack();
	setVisible(true);
        nameInput.requestFocusInWindow();
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
        
        
        // Add ActionListeners
        book.addActionListener(
            new ActionListener() {
            @Override
                public void actionPerformed( ActionEvent evt ) {
                    bookActionPerformed( evt );
                } // End method actionPerformed
            } // End anonymous inner class
        ); // End call to addActionListener
        
        displayAllBookings.addActionListener(
            new ActionListener() {
            @Override
                public void actionPerformed( ActionEvent evt ) {
                    allBookingsActionPerformed( evt );
                } // End method actionPerformed
            } // End anonymous inner class
        ); // End call to addActionListener
        
        bookingsForDay.addActionListener(
            new ActionListener() {
            @Override
                public void actionPerformed( ActionEvent evt ) {
                    dayBookingsActionPerformed( evt );
                } // End method actionPerformed
            } // End anonymous inner class
        ); // End call to addActionListener
        
        totalDinersForDay.addActionListener(
            new ActionListener() {
            @Override
                public void actionPerformed( ActionEvent evt ) {
                    dayTotalActionPerformed( evt );
                } // End method actionPerformed
            } // End anonymous inner class
        ); // End call to addActionListener
        
        exit.addActionListener(
            new ActionListener() {
            @Override
                public void actionPerformed( ActionEvent evt ) {
                    exitActionPerformed( evt );
                } // End method actionPerformed
            } // End anonymous inner class
        ); // End call to addActionListener
       
        
        // Set close operation
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            int response = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?", "Confirm exit",
            JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null, new String[] {
                "Yes", "No" }, "No");
                
                if (response == JOptionPane.YES_OPTION) {
                    presenter.close();
                    System.exit(0);
                }
            }
        });
    } // End of constructor
    
    // Set the presenter for this view
    public void bind( BookingPresenter bp) {
       presenter = bp;
    }
    
    // Event handlers
    
    // Handles call when Book is clicked
    private void bookActionPerformed( ActionEvent evt ) {
        presenter.addBooking();
    }
    
    // Handles call when Display All Bookings is clicked
    private void allBookingsActionPerformed( ActionEvent evt ) {
        presenter.allBookings();
    }
    
    // Handles call when Bookings for Day is clicked
    private void dayBookingsActionPerformed( ActionEvent evt ) {
        presenter.dayBookings();
    }
    
    // Handles call when Total Diners for Day is clicked
    private void dayTotalActionPerformed( ActionEvent evt ) {
        presenter.dayTotal();
    }
    
    // Handles call when Exit is clicked
    private void exitActionPerformed( ActionEvent evt ) {
        presenter.exit();
    }
    
    // IBookingView interface implementation
    @Override
    public void setOutputDisplay(String s) {
        outputDisplay.setText(s);
    }

    @Override
    public void appendOutputDisplay(String s) {
        outputDisplay.append(s);
    }

    @Override
    public String getNameInput() {
        return nameInput.getText();
    }

    @Override
    public String getPhoneInput() {
        return phoneInput.getText();
    }

    @Override
    public String getDinersInput() {
        return dinersInput.getText();
    }    

    @Override
    public String getDayInput() {
        return (String)dayInput.getSelectedItem();
    }
    
    @Override
    public String getTableSizeInput() {
        return (String)tableSizeInput.getSelectedItem();
    }
    
    @Override
    public void setSizes(String[] s) {
        sizes = null;
        sizes = s;
    }

    @Override
    public void nameInputRequestFocus() {
        nameInput.requestFocusInWindow();
    }

    @Override
    public void phoneInputRequestFocus() {
        phoneInput.requestFocusInWindow();
    }

    @Override
    public void dinersInputRequestFocus() {
        dinersInput.requestFocusInWindow();
    }

    @Override
    public void dayInputRequestFocus() {
        dayInput.requestFocusInWindow();
    }
    
    @Override
    public int showOptionDialog(String s1, String s2) {
        int response = JOptionPane.showOptionDialog(null, s1, s2, JOptionPane.YES_NO_OPTION, 
                                     JOptionPane.QUESTION_MESSAGE, null, new String[] {"Yes", "No"}, "No");
        return response;
    }
} // End class BookingView