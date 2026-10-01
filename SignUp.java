package wedding_hall_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SignUp implements ActionListener {
    JFrame f=new JFrame();
    JPanel p=new JPanel();
    JTextField username = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton signUpButton = new JButton("SIGN UP");
    JButton backButton = new JButton("BACK");
    JRadioButton male=new JRadioButton("MALE");
    JRadioButton female=new JRadioButton("FEMALE");
    JRadioButton other=new JRadioButton("OTHER");
    ButtonGroup bgroup=new ButtonGroup();
    Container c=f.getContentPane();
    Connection conn;

    public SignUp() {
        f.setTitle("Sign Up - Lavender Sky Hall");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);
        f.setSize(600,600);
        f.setLocationRelativeTo(null);
        c.setLayout(null);
        c.setBackground(new Color(230, 230, 250));
        
        JLabel titleLabel = new JLabel("CREATE A NEW ACCOUNT");
        titleLabel.setBounds(70, 30, 400, 30);
        titleLabel.setFont(new Font("Baskerville", Font.BOLD|Font.ITALIC, 25));
        titleLabel.setForeground(new Color(148, 112, 196));
        c.add(titleLabel);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 100, 100, 30);
        c.add(userLabel);

        username.setBounds(160, 100, 250, 30);
        c.add(username);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 150, 100, 30);
        c.add(passLabel);
        
        JLabel genderLabel=new JLabel("Gender:");
        genderLabel.setBounds(50,190,100,30);
        c.add(genderLabel);
        
        passwordField.setBounds(160, 150, 250, 30);
        c.add(passwordField);

        signUpButton.setBounds(160, 270, 100, 30);
        backButton.setBounds(280, 270, 100, 30);
        c.add(signUpButton);
        c.add(backButton);
        
        male.setBounds(160,190,100,50); 
        female.setBounds(270,190,100,50); 
        other.setBounds(380,190,100,50); 
        
        bgroup.add(male);
        bgroup.add(female);
        bgroup.add(other);
        
        c.add(male); 
        c.add(female); 
        c.add(other);
        
        signUpButton.addActionListener(this);
        backButton.addActionListener(this);
        
        male.addActionListener(this); 
        female.addActionListener(this);
        other.addActionListener(this); 
        
        f.setVisible(true);
    }

   
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signUpButton) {
        
            String uname = username.getText().trim();
            String pswd = new String(passwordField.getPassword());
            String gender="default";
            if(male.isSelected()) {
            	gender="male";
            }
            else if(female.isSelected()) {
            	gender="female";       
            	}
            else if(other.isSelected()) {
            	gender="other";
            }      
            if (uname.isEmpty() || pswd.isEmpty()||gender.isEmpty()) {
                JOptionPane.showMessageDialog(backButton, this, "Please fill in all fields.", 0);
                return;
            }

            try {
                 	conn = WeddingHall.getConnection();
                PreparedStatement checkUser = conn.prepareStatement("SELECT username FROM users WHERE username = ?");
                checkUser.setString(1, uname);
                ResultSet rs = checkUser.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(backButton, this, "Username already exists.", 0);
                } else {
                    PreparedStatement insertUser = conn.prepareStatement("INSERT INTO users (username, password,gender) VALUES (?, ?, ?)");
                    insertUser.setString(1, uname);
                    insertUser.setString(2, pswd); // Replace with hashed password in production
                    insertUser.setString(3, gender);
                    insertUser.executeUpdate();
                    JOptionPane.showMessageDialog(backButton, this, "Account created successfully!", 0);
                   f. dispose();
                    new WeddingHall(); // Go back to login
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(backButton, this, "Error creating account: " + ex.getMessage(), 0);
            }

        } else if (e.getSource() == backButton) {
            f.dispose();
            new WeddingHall(); // Back to login screen
        }
    }
}

