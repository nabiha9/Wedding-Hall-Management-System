package wedding_hall_management_system;
 import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;


public class MenuForm extends JFrame implements ActionListener {
  JFrame frame=new JFrame();
  JPanel panel=new JPanel();
	JTextField menu_id=new JTextField();
    JTextField menu_course=new JTextField();
    JButton saveB=new JButton("SAVE");
    JButton view=new JButton("VIEW");
    JLabel menuID=new JLabel("MENU_ID");
    JLabel menuCourse=new JLabel("COURSE:");
    JLabel menuItem=new JLabel("CUISINE:");
    JComboBox<String> menu_item = new JComboBox<>(new String[] {
        "DESI", "CHINESE", "AMERICAN", "ITALIAN","KOREAN","JAPANESE", "MIXED","OTHER"});
    private DefaultTableModel table3=new DefaultTableModel();
    private JTable MenuTable=new JTable(table3);
    JScrollPane scroll = new JScrollPane(MenuTable);
    Connection conn;
    
    
    public MenuForm() {
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
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        menuID.setFont(labelFont);
        add(menuID, gbc);

        gbc.gridx = 1;
        menu_id.setPreferredSize(new Dimension(200, 25));
        add(menu_id, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        menuCourse.setFont(labelFont);
        add(menuCourse, gbc);

        gbc.gridx = 1;
        menu_course.setPreferredSize(new Dimension(200, 25));
        add(menu_course, gbc);

        // Booking Type
        gbc.gridx = 0;
        gbc.gridy = 2;
        menuItem.setFont(labelFont);
        add(menuItem, gbc);

        gbc.gridx = 1;
        menu_item.setPreferredSize(new Dimension(200, 25));
        add(menu_item, gbc);

        // Submit button
        gbc.gridx = 0;
        gbc.gridy = 3;
        saveB.setPreferredSize(new Dimension(100, 30));
        saveB.addActionListener(this);
        add(saveB, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        view.setPreferredSize(new Dimension(100, 30));
        view.addActionListener(this);
        add(view, gbc);

        MenuTable.setFillsViewportHeight(true);

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
        String id = menu_id.getText().trim();
        String course = menu_course.getText().trim();
        String items = menu_item.getSelectedItem().toString();

        if (id.isEmpty() || course.isEmpty()||items.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
        }     if(e.getSource()==saveB) {
            try {
            	conn = WeddingHall.getConnection();
               try( PreparedStatement checkUser = conn.prepareStatement("SELECT MENU_ID FROM MENU WHEE MENU_ID=  ?")){
                checkUser.setString(1, id);
                ResultSet rs = checkUser.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, this, "ID already exists.", 0);
                } 
               }
               try(PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO MENU(MENU_ID,MENU_COURSE,CUISINE) VALUES (?, ?, ?)"))
               {
                    insertStmt.setString(1, id);
                    insertStmt.setString(2, course);
                    insertStmt.setString(3, items);
                    insertStmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, this, "Menu added successfully!", 0);
                    dispose();
                    new MainMenu(); 
               }
            
           } 
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, this, "Error adding menu: " + ex.getMessage(), 0);
            }
            }
           if(e.getSource()==view) {
        	try (PreparedStatement selectStmt= conn.prepareStatement(
            "SELECT * FROM Menu_view");
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

                table3.setDataVector(rows, colNames);   // instant refresh

            }
        		
        	
        	catch(Exception ex) {
                JOptionPane.showMessageDialog(null, this, "Error viewing menu: " + ex.getMessage(), 0);

        	}
        	}
        }
	
	public static void main(String[] args) {
		MenuForm menuobj=new MenuForm();
		
	}
}
