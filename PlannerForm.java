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

public class PlannerForm extends JFrame implements ActionListener {
    JTextField plannerIdField = new JTextField();
    JTextField nameField = new JTextField();
    JTextField emailField = new JTextField();
    JTextField contactField = new JTextField();
    JButton submitButton = new JButton("Submit");
    JButton viewButton = new JButton("View");
    private DefaultTableModel table=new DefaultTableModel();
    private JTable plannerTable=new JTable(table);
    JScrollPane scroll = new JScrollPane(plannerTable);
    Connection conn;
    public PlannerForm() {
        setTitle("Planner Form - Lavender Sky Hall");
        setSize(1000, 1000);
        setLocationRelativeTo(null); // center the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(230, 230, 250)); // Lavender

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Serif", Font.BOLD, 16);

        // Planner ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel idLabel = new JLabel("Planner ID:");
        idLabel.setFont(labelFont);
        add(idLabel, gbc);

        gbc.gridx = 1;
        plannerIdField.setPreferredSize(new Dimension(220, 25));
        add(plannerIdField, gbc);

        // Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(labelFont);
        add(nameLabel, gbc);

        gbc.gridx = 1;
        nameField.setPreferredSize(new Dimension(220, 25));
        add(nameField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        add(emailLabel, gbc);

        gbc.gridx = 1;
        emailField.setPreferredSize(new Dimension(220, 25));
        add(emailField, gbc);

        // Contact Number
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel contactLabel = new JLabel("Contact No.:");
        contactLabel.setFont(labelFont);
        add(contactLabel, gbc);

        gbc.gridx = 1;
        contactField.setPreferredSize(new Dimension(220, 25));
        add(contactField, gbc);

        // Submit Button
        gbc.gridx = 0;
        gbc.gridy = 4;
        submitButton.setPreferredSize(new Dimension(100, 30));
        submitButton.addActionListener(this);
        add(submitButton, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        viewButton.setPreferredSize(new Dimension(100, 30));
        viewButton.addActionListener(this);
        add(viewButton, gbc);
        
        plannerTable.setFillsViewportHeight(true);

        gbc.gridx = 0;
        gbc.gridy = 5;          // next free row
        gbc.gridwidth = 2;      // span both columns
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(scroll, gbc);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String id = plannerIdField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String contact = contactField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || email.isEmpty() || contact.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
        } else if (!contact.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid 10-digit contact number.");
        } else if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
        } 
        if(e.getSource()==submitButton) {
        	
        	try {
            	conn = WeddingHall.getConnection();
        try (PreparedStatement insertUser = conn.prepareStatement("INSERT INTO planner (planner_id,name,e_mail,contact_no) VALUES (?, ?, ?, ?)"))
        		{ 
        	    insertUser.setString(1, id);
                insertUser.setString(2, name); 
                insertUser.setString(3, email);
                insertUser.setString(4, contact);
                insertUser.executeUpdate();
                JOptionPane.showMessageDialog(null, this, "Planner recorded successfully!", 0);
                dispose();
                new MainMenu(); 
            }
        }
         catch (Exception ex) {
            JOptionPane.showMessageDialog(null, this, "Error recording planner: " + ex.getMessage(), 0);
        }
        }
        if(e.getSource()==viewButton) {
        	try (PreparedStatement selectStmt= conn.prepareStatement(
                    "SELECT * FROM Planner_view");
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

                        table.setDataVector(rows, colNames);   // instant refresh

                    }
                		
                	
                	catch(Exception ex) {
                        JOptionPane.showMessageDialog(null, this, "Error viewing booking: " + ex.getMessage(), 0);

                	}
                	}
        }
    

	public static void main(String[] args) {
		// TODO Auto-generated method stub
  PlannerForm plan=new PlannerForm();
	}

}
