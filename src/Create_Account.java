
import java.sql.*;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Asus
 */
public class Create_Account extends javax.swing.JFrame {

    /**
     * Creates new form Create_Account
     */
    String url = "jdbc:mysql://localhost:3306/busmania";
    String username = "root";
    String pass = "admin";

    public Create_Account() {
        initComponents();
        setLocationRelativeTo(null); //for making the frame center of the screen
        setResizable(false);
        scaleJLabel_11();
    }

    public void scaleJLabel_11() {
        ImageIcon icon = new ImageIcon("D:\\Java\\BusMania\\src\\img\\back.jpg");
        Image img = icon.getImage();
        Image imgScale = img.getScaledInstance(jLabel11.getWidth(), jLabel11.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon newScaled = new ImageIcon(imgScale);
        jLabel11.setIcon(newScaled);
    }

    public void Clear_Fields() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jComboBox1.setSelectedIndex(0);
    }

    public void SigninActionPerformed_Work() {
        String FIRST_NAME = jTextField1.getText();
        String LAST_NAME = jTextField2.getText();
        String CELL = jTextField3.getText();
        String NID = jTextField4.getText();
        String DISTRICT = (String) jComboBox1.getSelectedItem();
        String EMAIL = jTextField5.getText();
        String USER_ID = jTextField6.getText();
        String PASS = jTextField7.getText();

        if (FIRST_NAME.length() == 0 || LAST_NAME.length() == 0 || CELL.length() == 0 || NID.length() == 0
                || EMAIL.length() == 0 || USER_ID.length() == 0 || PASS.length() == 0) {
            JOptionPane.showMessageDialog(null, "Fields can't be empty!");
        } else if (DISTRICT == "Select") {
            JOptionPane.showMessageDialog(null, "Please select a district!");
        } else {
            try {
                String query = "SELECT nid FROM Users WHERE user_id = '" + USER_ID + "';";
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = (Connection) DriverManager.getConnection(url, username, pass); //2
                Statement st = (Statement) con.createStatement();
                ResultSet rs = st.executeQuery(query);
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "User id already exists!\nEnter a new one!", "Error", 0);
                } else {
                    query = "INSERT INTO users VALUES('" + FIRST_NAME + "','" + LAST_NAME
                            + "','" + CELL + "','" + NID + "','" + DISTRICT + "','" + EMAIL + "','" + USER_ID + "','" + PASS + "');";
                    //System.out.println(query);
                    st.executeUpdate(query);
                    JOptionPane.showMessageDialog(null, "Account has been created successfully!");
                }

            } catch (ClassNotFoundException | SQLException ex) {
                JOptionPane.showMessageDialog(null, ex, "Error!", 0);
            }
            Clear_Fields();

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

        jSpinner1 = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        reset_button = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        submit_button = new javax.swing.JButton();
        prev_page_button = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();

        jLabel8.setText("jLabel8");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Create Account");
        setBackground(new java.awt.Color(48, 188, 67));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 450));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel1.setText("First Name");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 79, -1, -1));

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(224, 75, 350, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel2.setText("Last Name");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 125, 78, -1));
        getContentPane().add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(224, 121, 350, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel3.setText("Cell No.");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 164, 78, -1));

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(224, 160, 350, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel4.setText("NID No.");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 210, 78, -1));

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(224, 206, 350, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel5.setText("District");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 250, 78, -1));

        jComboBox1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Dhaka", "Chittagong", "Khulna", "Rajshahi", "Sylhet", "Comilla", "Mymensingh", "Barishal", "Noakhali", "Feni", "Jessore" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(224, 246, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel6.setText("Fill Up Your Information");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(236, 6, 325, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel7.setText("Email");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 289, 78, -1));
        getContentPane().add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(224, 285, 350, -1));

        reset_button.setBackground(new java.awt.Color(0, 204, 0));
        reset_button.setFont(new java.awt.Font("Britannic Bold", 0, 18)); // NOI18N
        reset_button.setText("Reset");
        reset_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reset_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(reset_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 420, 92, 41));

        jLabel9.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel9.setText("User Id");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 340, 78, -1));

        jTextField6.setToolTipText("Must be unique.");
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(224, 331, 350, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel10.setText("Password");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 375, 78, -1));

        jTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField7KeyPressed(evt);
            }
        });
        getContentPane().add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(224, 371, 350, -1));

        submit_button.setBackground(new java.awt.Color(0, 204, 0));
        submit_button.setFont(new java.awt.Font("Britannic Bold", 0, 18)); // NOI18N
        submit_button.setText("Submit");
        submit_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submit_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(submit_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 420, 92, 41));

        prev_page_button.setBackground(new java.awt.Color(153, 255, 0));
        prev_page_button.setFont(new java.awt.Font("Britannic Bold", 0, 14)); // NOI18N
        prev_page_button.setText("Go to previous page");
        prev_page_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prev_page_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(prev_page_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 470, 230, -1));
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 580));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void reset_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reset_buttonActionPerformed
        // TODO add your handling code here:        
        Clear_Fields();
    }//GEN-LAST:event_reset_buttonActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void submit_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submit_buttonActionPerformed
        // TODO add your handling code here:
        SigninActionPerformed_Work();
    }//GEN-LAST:event_submit_buttonActionPerformed

    private void prev_page_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prev_page_buttonActionPerformed
        // TODO add your handling code here:
        User_Login ob = new User_Login();
        dispose();
        ob.setVisible(true);
    }//GEN-LAST:event_prev_page_buttonActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField7KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            SigninActionPerformed_Work();
        }
    }//GEN-LAST:event_jTextField7KeyPressed

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
            java.util.logging.Logger.getLogger(Create_Account.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Create_Account.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Create_Account.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Create_Account.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Create_Account().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JButton prev_page_button;
    private javax.swing.JButton reset_button;
    private javax.swing.JButton submit_button;
    // End of variables declaration//GEN-END:variables
}