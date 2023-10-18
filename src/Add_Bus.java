
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Add_Bus extends javax.swing.JFrame {

    String url = "jdbc:mysql://localhost:3306/busmania";
    String username = "root";
    String pass = "admin";
    Connection con;
    Statement stmt;

    Date next_day = new Date(new Date().getTime() + 86400000);
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
    String tommorrow = formatter.format(next_day);

    ArrayList<String> seat_list = new ArrayList<String>();

    public Add_Bus() {
        initComponents();
        ScaleImage1();
        setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        Setup_Database_Connection();
        Add_Seat_Names();
        Update_Seats();
    }

    public void Add_Seat_Names() {
        for (char ch = 'A'; ch <= 'J'; ch++) {
            for (char i = '1'; i <= '4'; i++) {
                String sname = String.valueOf(ch) + String.valueOf(i);
                seat_list.add(sname);
            }
        }
    }

    public void Update_Seats() {
        try {
            String query = "SELECT * FROM routes;";
            ResultSet rset = stmt.executeQuery(query);
            Connection tmp_con = (Connection) DriverManager.getConnection(url, username, pass); //2
            Statement tmp_stmt = (Statement) tmp_con.createStatement();
            while (rset.next()) {
                String FROM = rset.getString("from_location");
                String TO = rset.getString("to_location");
                String table_name = FROM + "_" + TO;
                query = "SELECT * FROM " + table_name + ";";
                ResultSet tmp_rset = tmp_stmt.executeQuery(query);
                String dt = "";
                
                if (tmp_rset.next()) {
                    dt = tmp_rset.getString("date");
                }
                int is_equal = dt.compareTo(tommorrow);
                
                if (is_equal != 0) {
                    query = "UPDATE " + table_name + " SET date = '" + tommorrow + "';";
                    tmp_stmt.executeUpdate(query);
//                    System.out.println(query);
                    for (String sname : seat_list) {
                        query = "UPDATE " + table_name + " SET " + sname + " = '0';";
                        tmp_stmt.executeUpdate(query);
                    }
                }

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "Database Error!", 0);
        }
    }

    public void ScaleImage1() {
        ImageIcon ic;
        ic = new ImageIcon("D:\\Java\\BusMania\\src\\img\\add_bus2.jpg");
        Image im = ic.getImage();
        Image imScale = im.getScaledInstance(jLabel7.getWidth(), jLabel7.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledicon = new ImageIcon(imScale);
        jLabel7.setIcon(scaledicon);
    }

    public void Clear_Fields() {
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
    }

    public void Setup_Database_Connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(url, username, pass); //2
            stmt = (Statement) con.createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Database Error!", 0);
        }
    }

    public boolean Bus_Exists(String BUS_NAME) {
        boolean ok = false;
        try {
            String query = "SELECT * FROM routes WHERE bus_name = '" + BUS_NAME + "';";
            ResultSet rset = stmt.executeQuery(query);
            if (rset.next()) {
                ok = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Database Error!", 0);
        }
        return ok;
    }

    public boolean Route_Exists(String FROM, String TO) {
        boolean ok = false;
        try {
            String query = "SELECT * FROM routes WHERE from_location = '" + FROM + "' AND to_location = '" + TO + "';";
            ResultSet rset = stmt.executeQuery(query);
            if (rset.next()) {
                ok = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Database Error!", 0);
        }
        return ok;
    }

    public void Create_Route(String FROM, String TO) {
        try {
            String query = "CREATE TABLE " + FROM + "_" + TO + "( bus_name VARCHAR(255) PRIMARY KEY, ticket_fare VARCHAR(255), "
                    + "arrival_time VARCHAR(255), departure_time VARCHAR(255), date VARCHAR(255) );";
            stmt.executeUpdate(query);
            for (String sname : seat_list) {
                // add column
                query = "ALTER TABLE " + FROM + "_" + TO + " ADD ";
                String q_temp = query + sname + " varchar(255);";
                stmt.executeUpdate(q_temp);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex, "Error", 2);
        }
    }

    public void Add_Values_to_Route(String BUS_NAME, String FROM, String TO, String TICKET_FARE, String ARRIVAL_TIME, String DEPT_TIME) {
        try {
            // Insert values in routes table
            String query = "INSERT INTO routes VALUES('" + BUS_NAME + "','" + FROM + "','" + TO + "');";
            stmt.executeUpdate(query);

            // Insert values in the desired route (i.e. noakhali_chittagong)
            query = "INSERT INTO " + FROM + "_" + TO + " (bus_name,ticket_fare,arrival_time,departure_time,date) "
                    + "VALUES ('" + BUS_NAME + "'," + Integer.valueOf(TICKET_FARE)
                    + ",'" + ARRIVAL_TIME + "','" + DEPT_TIME + "','" + tommorrow + "');";

            stmt.executeUpdate(query);

            for (String sname : seat_list) {
                // add value in sname column
                query = "UPDATE " + FROM + "_" + TO + " SET " + sname + " = '0' WHERE bus_name = '" + BUS_NAME + "';";
                stmt.executeUpdate(query);
            }

            JOptionPane.showMessageDialog(null, "Bus has been added successfully!");
            Clear_Fields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex, "Error", 2);
        }
    }

    public void submit_buttonActionPerformed_work() {
        String FROM = (String) jComboBox1.getSelectedItem();
        String TO = (String) jComboBox2.getSelectedItem();
        String BUS_NAME = jTextField1.getText();
        String ARRIVAL_TIME = jTextField2.getText();
        String DEPT_TIME = jTextField3.getText();
        String TICKET_FARE = jTextField4.getText();

        String msg = "";

        if (FROM == "Select") {
            msg = "Select source district!";
            JLabel label = new JLabel(msg);
            label.setFont(new Font("sensserif", Font.BOLD, 18));
            JOptionPane.showMessageDialog(null, label, "Error", 2);
        } else if (TO == "Select") {
            msg = "Select destination district!";
            JLabel label = new JLabel(msg);
            label.setFont(new Font("sensserif", Font.BOLD, 18));
            JOptionPane.showMessageDialog(null, label, "Error", 2);
        } else if (FROM == TO) {
            msg = "Source and destination locations can't be same!";
            JLabel label = new JLabel(msg);
            label.setFont(new Font("sensserif", Font.BOLD, 18));
            JOptionPane.showMessageDialog(null, label, "Error", 2);
        } else if (BUS_NAME.length() == 0) {
            msg = "Enter valid bus name!";
            JLabel label = new JLabel(msg);
            label.setFont(new Font("sensserif", Font.BOLD, 18));
            JOptionPane.showMessageDialog(null, label, "Error", 2);
        } else if (ARRIVAL_TIME.length() == 0) {
            msg = "Enter valid arrival time!";
            JLabel label = new JLabel(msg);
            label.setFont(new Font("sensserif", Font.BOLD, 18));
            JOptionPane.showMessageDialog(null, label, "Error", 2);
        } else if (DEPT_TIME.length() == 0) {
            msg = "Enter valid depature time!";
            JLabel label = new JLabel(msg);
            label.setFont(new Font("sensserif", Font.BOLD, 18));
            JOptionPane.showMessageDialog(null, label, "Error", 2);
        } else if (TICKET_FARE.length() == 0 || Integer.valueOf(TICKET_FARE) < 10) {
            msg = "Enter valid ticket fare!";
            JLabel label = new JLabel(msg);
            label.setFont(new Font("sensserif", Font.BOLD, 18));
            JOptionPane.showMessageDialog(null, label, "Error", 2);
        } else {
            try {
                String query = "";
                FROM = FROM.toLowerCase();
                TO = TO.toLowerCase();

                //if the route doesn't exist create the route
                query = "SET GLOBAL sql_safe_updates=0;";
                stmt.executeUpdate(query);

                if (Bus_Exists(BUS_NAME) == false) {
                    if (Route_Exists(FROM, TO) == false) {
                        Create_Route(FROM, TO);
                    }
                    Add_Values_to_Route(BUS_NAME, FROM, TO, TICKET_FARE, ARRIVAL_TIME, DEPT_TIME);

                } else {
                    JOptionPane.showMessageDialog(rootPane, "Bus already exist!", "Error", 2);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(rootPane, ex, "Error", 2);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        submit_button = new javax.swing.JButton();
        reset_button = new javax.swing.JButton();
        go_back_button = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Add Bus");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("From");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 60, 54, 24));

        jComboBox1.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Noakhali", "Dhaka", "Chittagong", "Cumilla" }));
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 60, 141, -1));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("To");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 100, 41, -1));

        jComboBox2.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Noakhali", "Dhaka", "Chittagong", "Cumilla" }));
        getContentPane().add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 100, 141, -1));

        jLabel3.setBackground(new java.awt.Color(0, 51, 51));
        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Bus Name");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 140, -1, -1));

        jTextField1.setFont(new java.awt.Font("sansserif", 0, 15)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 140, 141, -1));

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Arrival Time");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 180, -1, -1));

        jTextField2.setFont(new java.awt.Font("sansserif", 0, 15)); // NOI18N
        getContentPane().add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 180, 141, -1));

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Departure Time");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 220, -1, -1));

        jTextField3.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        getContentPane().add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 220, 141, -1));

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Ticket Fare");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 260, -1, -1));

        jTextField4.setFont(new java.awt.Font("sansserif", 0, 15)); // NOI18N
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField4KeyPressed(evt);
            }
        });
        getContentPane().add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 260, 141, -1));

        submit_button.setBackground(new java.awt.Color(0, 0, 0));
        submit_button.setFont(new java.awt.Font("Gill Sans Ultra Bold", 0, 14)); // NOI18N
        submit_button.setForeground(new java.awt.Color(0, 255, 0));
        submit_button.setText("Submit");
        submit_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submit_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(submit_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 320, 149, 40));

        reset_button.setBackground(new java.awt.Color(0, 0, 0));
        reset_button.setFont(new java.awt.Font("Gill Sans Ultra Bold", 0, 14)); // NOI18N
        reset_button.setForeground(new java.awt.Color(0, 255, 0));
        reset_button.setText("Reset");
        reset_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reset_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(reset_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 320, 154, 40));

        go_back_button.setBackground(new java.awt.Color(0, 0, 0));
        go_back_button.setFont(new java.awt.Font("Gill Sans Ultra Bold", 0, 14)); // NOI18N
        go_back_button.setForeground(new java.awt.Color(0, 255, 0));
        go_back_button.setText("Previous Page");
        go_back_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                go_back_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(go_back_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 380, 195, 40));
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 770, 460));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
        jTextField1.setBackground(Color.WHITE);
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void submit_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submit_buttonActionPerformed
        // TODO add your handling code here:
        submit_buttonActionPerformed_work();
    }//GEN-LAST:event_submit_buttonActionPerformed

    private void reset_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reset_buttonActionPerformed
        // TODO add your handling code here:

        Clear_Fields();

    }//GEN-LAST:event_reset_buttonActionPerformed

    private void go_back_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_go_back_buttonActionPerformed
        // TODO add your handling code here:
        Admin_Page ob = new Admin_Page();
        dispose();
        ob.setVisible(true);
    }//GEN-LAST:event_go_back_buttonActionPerformed

    private void jTextField4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            submit_buttonActionPerformed_work();
        }
    }//GEN-LAST:event_jTextField4KeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Add_Bus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Add_Bus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Add_Bus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Add_Bus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Add_Bus().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton go_back_button;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JButton reset_button;
    private javax.swing.JButton submit_button;
    // End of variables declaration//GEN-END:variables
}
