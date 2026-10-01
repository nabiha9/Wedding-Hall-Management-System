package wedding_hall_management_system;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import java.sql.*;

public class BookingForm extends JFrame implements ActionListener {
    JTextField bookingIdField = new JTextField();
    JTextField bookingDateField = new JTextField(); // You can use JDatePicker in real projects
    JComboBox<String> bookingTypeCombo = new JComboBox<>(new String[] {
        "Wedding", "Reception", "Engagement", "Birthday", "Other"
    });

    JButton submitButton = new JButton("Submit");
    JButton viewButton = new JButton("View");
    private DefaultTableModel tableModel=new DefaultTableModel();
    private JTable bookingTable=new JTable(tableModel);
    JScrollPane scroll = new JScrollPane(bookingTable);
    Connection conn;
    public BookingForm() {
        setTitle("Booking Form - Lavender Sky Hall");
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Only close this window

        getContentPane().setBackground(new Color(230, 230, 250));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Serif", Font.BOLD, 16);

        // Booking ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel idLabel = new JLabel("Booking ID:");
        idLabel.setFont(labelFont);
        add(idLabel, gbc);

        gbc.gridx = 1;
        bookingIdField.setPreferredSize(new Dimension(200, 25));
        add(bookingIdField, gbc);

        // Booking Date
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel dateLabel = new JLabel("Booking Date:");
        dateLabel.setFont(labelFont);
        add(dateLabel, gbc);

        gbc.gridx = 1;
        bookingDateField.setPreferredSize(new Dimension(200, 25));
        bookingDateField.setToolTipText("Format: YYYY-MM-DD");
        add(bookingDateField, gbc);

        // Booking Type
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel typeLabel = new JLabel("Booking Type:");
        typeLabel.setFont(labelFont);
        add(typeLabel, gbc);

        gbc.gridx = 1;
        bookingTypeCombo.setPreferredSize(new Dimension(200, 25));
        add(bookingTypeCombo, gbc);

        // Submit button
        gbc.gridx = 0;
        gbc.gridy = 3;
        submitButton.setPreferredSize(new Dimension(100, 30));
        submitButton.addActionListener(this);
        add(submitButton, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        viewButton.setPreferredSize(new Dimension(100, 30));
        viewButton.addActionListener(this);
        add(viewButton, gbc);

        bookingTable.setFillsViewportHeight(true);

        gbc.gridx = 0;
        gbc.gridy = 4;          // next free row
        gbc.gridwidth = 2;      // span both columns
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(scroll, gbc);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String id = bookingIdField.getText().trim();
        String date = bookingDateField.getText().trim();
        String type = bookingTypeCombo.getSelectedItem().toString();

        if (id.isEmpty() || date.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
        }     if(e.getSource()==submitButton) {
        	System.out.println("submit button is pressed");
            try {
            	conn = WeddingHall.getConnection();
            	System.out.println("Connection: " + conn);

               try( PreparedStatement checkUser = conn.prepareStatement("SELECT BOOKING_DATE FROM booking WHERE BOOKING_DATE=  ?")){
                checkUser.setDate(1, java.sql.Date.valueOf(date));
                ResultSet rs = checkUser.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, this, "Booking already made at that date.Choose a different date.", 0);
               return;
                } 
               }
               try(PreparedStatement inStmt = conn.prepareStatement("INSERT INTO BOOKING (BOOKING_ID,BOOKING_DATE,BOOKING_TYPE) VALUES (?, ?, ?)"))
               {
                    inStmt.setInt(1,  Integer.parseInt(id));
                    inStmt.setDate(2, java.sql.Date.valueOf(date));
                    inStmt.setString(3, type);
                    inStmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, this, "Booking created successfully!", 0);
                    dispose();
                    new MainMenu(); 
               }
            
           } 
            catch (Exception ex) {
            	ex.printStackTrace();
                JOptionPane.showMessageDialog(null, this, "Error creating booking: " + ex.getMessage(), 0);
            }
            }
           if(e.getSource()==viewButton) {
        	try (PreparedStatement selectStmt= conn.prepareStatement(
            "SELECT * FROM Booking_view");
        	ResultSet rs = selectStmt.executeQuery()) {

                ResultSetMetaData md = rs.getMetaData();
                int cols = md.getColumnCount();

                // Build column list
                Vector<String> colNames = new Vector<>();
                for (int i = 1; i <= cols; i++) {
                    colNames.add(md.getColumnName(i));
                }

                // Build rows
                Vector<Vector<Object>> rows = new Vector<>();
                while (rs.next()) {
                    Vector<Object> row = new Vector<>(cols);
                    for (int i = 1; i <= cols; i++) {
                        row.add(rs.getObject(i));
                    }
                    rows.add(row);
                }

                tableModel.setDataVector(rows, colNames);   // instant refresh

            }
        		
        	
        	catch(Exception ex) {
                JOptionPane.showMessageDialog(null, this, "Error viewing booking: " + ex.getMessage(), 0);

        	}
        	}
        }
        public static void main (String[] args) {
        	BookingForm book=new BookingForm();
        }
    }

