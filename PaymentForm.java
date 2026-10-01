package wedding_hall_management_system;


	import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
	import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

	public class PaymentForm extends JFrame implements ActionListener {
	    JTextField paymentIdField = new JTextField();
	    JTextField amountField = new JTextField();
	    JTextField dateField = new JTextField(); // Simple text field for date
	    JRadioButton cash=new JRadioButton("CASH");
	    JRadioButton card=new JRadioButton("CARD");
	    JRadioButton other=new JRadioButton("OTHER");
	    ButtonGroup bgroup=new ButtonGroup();
	    JButton submitButton = new JButton("Submit");
	    JButton viewButton = new JButton("View");
	    private DefaultTableModel table2=new DefaultTableModel();
	    private JTable paymentTable=new JTable(table2);
	    JScrollPane scroll = new JScrollPane(paymentTable);
	    Connection conn;

	    public PaymentForm() {
	        setTitle("Payment Form - Lavender Sky Hall");
	        setSize(1000, 1000);
	        setLocationRelativeTo(null); // center the window
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	        getContentPane().setBackground(new Color(230, 230, 250)); // lavender background
	        setLayout(new GridBagLayout());
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.insets = new Insets(12, 12, 12, 12);
	        gbc.anchor = GridBagConstraints.WEST;

	        Font labelFont = new Font("Serif", Font.BOLD, 16);

	        // Payment ID
	        gbc.gridx = 0;
	        gbc.gridy = 0;
	        JLabel idLabel = new JLabel("Payment ID:");
	        idLabel.setFont(labelFont);
	        add(idLabel, gbc);

	        gbc.gridx = 1;
	        paymentIdField.setPreferredSize(new Dimension(200, 25));
	        gbc.fill   = GridBagConstraints.HORIZONTAL;
	        add(paymentIdField, gbc);
	        
	        gbc.fill = GridBagConstraints.NONE;
	        
	        
	        // Amount
	        gbc.gridx = 0;
	        gbc.gridy = 1;
	        JLabel amountLabel = new JLabel("Amount:");
	        amountLabel.setFont(labelFont);
	        add(amountLabel, gbc);

	        gbc.gridx = 1;
	        amountField.setPreferredSize(new Dimension(200, 25));
	        gbc.fill   = GridBagConstraints.HORIZONTAL;
	        add(amountField, gbc);
	        
	        gbc.fill = GridBagConstraints.NONE;
	       
	        
	        // Date
	        gbc.gridx = 0;
	        gbc.gridy = 2;
	        JLabel dateLabel = new JLabel("Date:");
	        dateLabel.setFont(labelFont);
	        add(dateLabel, gbc);

	        gbc.gridx = 1;
	        dateField.setPreferredSize(new Dimension(200, 25));
	        dateField.setToolTipText("Format: YYYY-MM-DD");
	        gbc.fill   = GridBagConstraints.HORIZONTAL;
	        add(dateField, gbc);
	        
	        gbc.fill = GridBagConstraints.NONE;
	        
	        gbc.gridx = 0;
	        gbc.gridy = 3;
	        JLabel typeLabel = new JLabel("Payment_Type:");
	        typeLabel.setFont(labelFont);
	        add(typeLabel, gbc);
	        
	        gbc.gridx=1;
	        gbc.gridy=3;
	        bgroup.add(cash);
	        add(cash,gbc); 
	        gbc.gridy=4;
	        bgroup.add(card);
	        add(card,gbc);
	        gbc.gridy=5;
	        bgroup.add(other); 
	        add(other,gbc);
	        
	        // Submit button
	        gbc.gridx = 0;
	        gbc.gridy = 6;
	        submitButton.setPreferredSize(new Dimension(100, 30));
	        submitButton.addActionListener(this);
	        add(submitButton, gbc);

	        gbc.gridx = 1;
	        gbc.gridy = 6;
	        viewButton.setPreferredSize(new Dimension(100, 30));
	        viewButton.addActionListener(this);
	        add(viewButton, gbc);
	        
	        paymentTable.setFillsViewportHeight(true);

	        gbc.gridx = 0;
	        gbc.gridy = 7;          // next free row
	        gbc.gridwidth = 2;      // span both columns
	        gbc.weightx = 1;
	        gbc.weighty = 1;
	        gbc.fill = GridBagConstraints.BOTH;
	        add(scroll, gbc);

	        
	        setVisible(true);
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {
	        String id = paymentIdField.getText().trim();
	        String amount = amountField.getText().trim();
	        String date = dateField.getText().trim();
	        String type="default";
            if(cash.isSelected()) {
            	type="cash";
            }
            else if(card.isSelected()) {
            	type="female";       
            	}
            else if(other.isSelected()) {
            	type="other";
            }      
	        if (id.isEmpty() || amount.isEmpty() || date.isEmpty()||type.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
	            return;
	        }

	        try {
	            double amt = Double.parseDouble(amount); // Validate amount
	            JOptionPane.showMessageDialog(this,
	                    "Payment amount is valid");
	            
	        } catch (NumberFormatException ex) {
	            JOptionPane.showMessageDialog(this, "Please enter a valid numeric amount.");
	        }
	        if(e.getSource()==submitButton) {
	            try {
	            	conn = WeddingHall.getConnection();
	    try 
            (PreparedStatement checkUser = conn.prepareStatement("SELECT PAYMENT_ID FROM PAYMENT WHERE PAYMENT_ID = ?")){
            checkUser.setString(1, id);
            ResultSet rs = checkUser.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, this, "id already exists.", 0);
            } 
	    }
            try(
                PreparedStatement insertUser = conn.prepareStatement("INSERT INTO payment (payment_id,amount,date,paymeny_type) VALUES (?, ?, ?, ?,)"))
            { insertUser.setString(1, id);
                insertUser.setString(2, amount); 
                insertUser.setDate(3, java.sql.Date.valueOf(date));
                insertUser.setString(4, type);
                insertUser.executeUpdate();
                JOptionPane.showMessageDialog(null, this, "Payment recorded successfully!", 0);
                dispose();
                new MainMenu(); 
            }
	         
	            }   
	    catch (Exception ex) {
            JOptionPane.showMessageDialog(null, this, "Error recording payment: " + ex.getMessage(), 0);
        }
	            
	            }
	    if(e.getSource()==viewButton) { try
	    
	    ( PreparedStatement selectStmt= conn.prepareStatement("SELECT * FROM Booking_view");
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

            table2.setDataVector(rows, colNames);   // instant refresh

        }
    		
    	
    	catch(Exception ex) {
            JOptionPane.showMessageDialog(null, this, "Error viewing booking: " + ex.getMessage(), 0);

    	}
    	
	    }

	        }
		public static void main(String[] args) {
			 PaymentForm pay=new PaymentForm();
		}
}
