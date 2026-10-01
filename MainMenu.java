package wedding_hall_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu implements ActionListener {
	JFrame fr=new JFrame();
    JButton bookingButton = new JButton("BOOKING");
    JButton menuButton = new JButton("MENU");
    JButton paymentButton = new JButton("PAYMENT");
    JButton plannerButton = new JButton("PLANNER");
    JLabel title=new JLabel("LAVENDER SKY HALL");
    JPanel p=new JPanel();
    public MainMenu() {
        fr.setTitle("Lavender Sky Hall - Main Menu");
        fr.setSize(1500, 1500);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setLocationRelativeTo(null); // center window
        JComponent image3= new JComponent() {
            Image img = new ImageIcon("image11.jpg").getImage();   

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); 
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        JComponent image4= new JComponent() {
            Image img = new ImageIcon("image12.jpg").getImage();   

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); 
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        JComponent image5= new JComponent() {
            Image img = new ImageIcon("image17.jpg").getImage();   

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); 
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        JComponent image6= new JComponent() {
            Image img = new ImageIcon("image14.jpg").getImage();   

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); 
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        JComponent image8= new JComponent() {
            Image img = new ImageIcon("image16.png").getImage();   

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); 
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        JComponent image9= new JComponent() {
            Image img = new ImageIcon("image16.png").getImage();   

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); 
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        
        Container con=fr.getContentPane();
       	con.setBackground(new Color(230,230,250));
        con.setLayout(null);
        
        image3.setBounds(0, 0, 250, 250);
        con.add(image3);
        
        image4.setBounds(0,255, 200, 300);
        con.add(image4);
        
        image5.setBounds(0,560, 250, 250);
        con.add(image5);
        
        image6.setBounds(205,255, 50, 300);
        con.add(image6);
               
        image8.setBounds(845,440, 500, 400);
        con.add(image8);
        
        image9.setBounds(300,440, 560, 400);
        con.add(image9);
        
        
        title.setBounds(465,40,590,100);
        con.add(title);
        Font customFont2 = new Font("Serif", Font.BOLD|Font.ITALIC,55); 
        title.setFont(customFont2);
        title.setForeground(new Color(100, 50, 150));
       
        
        // Customize buttons: larger size, white background, bigger font
        Font buttonFont = new Font("Gabriola", Font.BOLD, 35);
        
        bookingButton.setBounds(500,200,200,100);
        bookingButton.setBackground(new Color(255,240,245));
        bookingButton.setFont(buttonFont);
        con.add(bookingButton);
        bookingButton.addActionListener(this);
        
        menuButton.setBounds(800,200,200,100);
        menuButton.setBackground(new Color(255,240, 245));
        menuButton.setFont(buttonFont);
        con.add(menuButton);
        menuButton.addActionListener(this);
        
        paymentButton.setBounds(500,400,200,100);
        paymentButton.setBackground(new Color(255, 240, 245));
        paymentButton.setFont(buttonFont);
        con.add(paymentButton);
        paymentButton.addActionListener(this);

        
        plannerButton.setBounds(800,400,200,100);
        plannerButton.setBackground(new Color(255, 240, 245));
        plannerButton.setFont(buttonFont);
        con.add(plannerButton);
        plannerButton.addActionListener(this);

        fr.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bookingButton) {
            new BookingForm();
        } else if (e.getSource() == paymentButton) {
            new PaymentForm();
        } else if (e.getSource() == plannerButton) {
            new PlannerForm();
        }
        else if(e.getSource()==menuButton) {
        	new MenuForm();
        }
    }
    public static void main (String[] args) {
    	MainMenu m=new MainMenu();
    }
}
