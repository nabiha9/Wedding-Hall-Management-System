package wedding_hall_management_system;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class WeddingHall implements ActionListener {
     JPanel p=new JPanel();
     JFrame f=new JFrame("wedding hall management system");
	 JLabel username=new JLabel("USERNAME"); 
	 JLabel password=new JLabel("PASSWORD");
	 JLabel label=new JLabel("WELCOME");
	 JLabel label1=new JLabel("TO");
	 JLabel label2=new JLabel("LAVENDER SKY HALL");
	 JLabel l=new JLabel("If you don't have an account then SIGN UP");
	 JTextField name=new JTextField();
	 JPasswordField pas=new JPasswordField();
	 JButton blogin=new JButton("LOGIN");
	 JButton bsignup=new JButton("SIGN UP");
	 JButton bexit=new JButton("EXIT");
	 JPanel panel=new JPanel();
	 Container con=f.getContentPane();
	 private static Connection conn;	
	 
	 
     public static Connection getConnection(){
    	 if (conn != null) {                 // already open
	            return conn;
	        }
     
    	 String URL="jdbc:oracle:thin:@10.11.0.22:1521:XE";
	     String user="FA24CS100";
	     String PASSWORD="oracle";
	     
	     try {
	    	
          Class.forName("oracle.jdbc.OracleDriver");
          conn = DriverManager.getConnection(URL,user,PASSWORD);
          System.out.println("database connection created");      }
	     
	     catch (Exception ex) {
	    	   System.out.println("database connection failed");    
          JOptionPane.showMessageDialog(null, "Database connection failed: " + ex.getMessage());
      }
	     return conn;
     }	
     
	   WeddingHall(){
		
		   con.setLayout(null);
		   Color lavender = new Color(230, 230, 250);
		   con.setBackground(new Color(230, 230, 250)); 
		   JComponent image1= new JComponent() {
	            Image img = new ImageIcon("image10.jpg").getImage();   

	            @Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g); 
	                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	            }
	        };
	        JComponent image2 = new JComponent() {
	            Image img = new ImageIcon("image8.jpg").getImage();   

	            @Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	            }
	        };
	        f.setSize(1500,1500);
	        image1.setBounds(700, 0, 670, 750);
	        con.add(image1);
	        image2.setBounds(72, 10, 520, 300);
	        con.add(image2);
	        
	        label.setBounds(210,20,500,100);
	        con.add(label);
	        Font customFont = new Font("Serif", Font.BOLD, 40); 
	        label.setFont(customFont);
	        label.setForeground(new Color(100, 50, 150));
	        con.setComponentZOrder(label, 0);
	        
	        label1.setBounds(290,80,500,100);
	        con.add(label1);
	        Font customFont1 = new Font("Serif", Font.BOLD, 40); 
	        label1.setFont(customFont1);
	        label1.setForeground(new Color(100, 50, 150));
	        con.setComponentZOrder(label1, 0);
	        
	        label2.setBounds(125,150,500,100);
	        con.add(label2);
	        Font customFont2 = new Font("Serif", Font.BOLD|Font.ITALIC, 40); 
	        label2.setFont(customFont2);
	        label2.setForeground(new Color(100, 50, 150));
	        con.setComponentZOrder(label2, 0);
	        
		   username.setBounds(20,390,200,30);
		   con.add(username);
		   
		   password.setBounds(20, 460, 200, 30);
		   con.add(password);
		   
		   name.setBounds(250,390,300,40);
		   con.add(name);
		   name.addActionListener(this);
		   
		   pas.setBounds(250,460,300,40);
		   con.add(pas);
		   pas.addActionListener(this);
		   
		   l.setBounds(180,530,400,40);
		   con.add(l);
		   
		   Font bFont = new Font("Serif", Font.BOLD, 20);
		   
		   bexit.setBounds(20,600,150,30);
		   bexit.setBackground(new Color(255, 240, 245));
		   bexit.setFont(bFont);
		   con.add(bexit);
		   bexit.addActionListener(this);
		   
		   bsignup.setBounds(210,600,150,30);
		   bsignup.setBackground(new Color(255, 240, 245));
		   bsignup.setFont(bFont);
		   con.add(bsignup);
		   bsignup.addActionListener(this);
		   
		   blogin.setBounds(400,600,150,30);
		   blogin.setBackground(new Color(255, 240, 245));
		   blogin.setFont(bFont);
		   con.add(blogin);
		   blogin.addActionListener(this);
		   
		   f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   f.setVisible(true);
		   
	   }
	
	public static void main(String[] args) {
		WeddingHall wed=new WeddingHall();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==blogin) {
			String uname=name.getText();
			String pswd= new String(pas.getPassword());
			try {
                PreparedStatement pst = conn.prepareStatement(
                    "SELECT * FROM users WHERE username = ? AND password = ?"
                );
                pst.setString(1, uname);
                pst.setString(2, pswd);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Login Successful!");
                    new MainMenu();
                    f.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Login failed: " + ex.getMessage());
            }
        }

			
	else if (e.getSource() == bsignup) {
			        JOptionPane.showMessageDialog(null, "Redirecting to sign up...");
			        new SignUp();  // open the sign-up window
			        f.dispose();
			    }
	else  if (e.getSource() == bexit) {
		        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
		        if (confirm == JOptionPane.YES_OPTION) {
		            System.exit(0);
		        }
		  }

		}
		
	}


