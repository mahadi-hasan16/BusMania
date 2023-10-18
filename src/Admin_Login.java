
import java.sql.*;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public final class Admin_Login extends javax.swing.JFrame {

    String url = "jdbc:mysql://localhost:3306/busmania";
    String username = "root";
    String pass = "admin";

    public Admin_Login() {
        setResizable(false);
        initComponents();
        ScaleImage1();
        ScaleImage2();
        ScaleImage3();
        jButton1.setOpaque(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setBorderPainted(false);
        
    }

    public void ScaleImage1() {
        ImageIcon ic;
        ic = new ImageIcon("D:\\Java\\BusMania\\src\\img\\user icon.png");
        Image im = ic.getImage();
        Image imScale = im.getScaledInstance(label1.getWidth(), label1.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledicon = new ImageIcon(imScale);
        label1.setIcon(scaledicon);
    }

    public void ScaleImage2() {
        ImageIcon ic;
        ic = new ImageIcon("D:\\Java\\BusMania\\src\\img\\password icon.png");
        Image im = ic.getImage();
        Image imScale = im.getScaledInstance(label2.getWidth(), label2.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledicon = new ImageIcon(imScale);
        label2.setIcon(scaledicon);
    }

    public void ScaleImage3() {
        ImageIcon ic;
        ic = new ImageIcon("D:\\Java\\BusMania\\src\\img\\admin1.jpg");
        Image im = ic.getImage();
        Image imScale = im.getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledicon = new ImageIcon(imScale);
        jLabel1.setIcon(scaledicon);
    }

    public void SigninActionPerformed_Work() {
        String USERID = admin_id.getText();
        String PASS = admin_pass.getText();
        if (USERID.isEmpty() || PASS.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username or password cannot be empty!", "Error", 2);
        } else {
            try {
                String query = "SELECT * FROM admin WHERE id = '" + USERID + "' AND password = '" + PASS + "';";
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = (Connection) DriverManager.getConnection(url, username, pass); //2
                Statement st = (Statement) con.createStatement();
                ResultSet rs = st.executeQuery(query);

                if (rs.next()) {
                    Admin_Page ap = new Admin_Page();
                    ap.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong user id or password!\nTry again!", "Alert", 0);
                }

            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(User_Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        admin_id = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        Signin = new javax.swing.JButton();
        go_back_button = new javax.swing.JButton();
        label1 = new javax.swing.JLabel();
        label2 = new javax.swing.JLabel();
        admin_pass = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Admin Login");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        admin_id.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        admin_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                admin_idActionPerformed(evt);
            }
        });
        getContentPane().add(admin_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 460, 30));

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setFont(new java.awt.Font("Cooper Black", 1, 48)); // NOI18N
        jButton1.setForeground(new java.awt.Color(51, 204, 0));
        jButton1.setText("Admin Login");
        jButton1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 690, 60));

        Signin.setBackground(new java.awt.Color(0, 0, 0));
        Signin.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        Signin.setForeground(new java.awt.Color(0, 204, 0));
        Signin.setText("Sign In");
        Signin.setPreferredSize(new java.awt.Dimension(90, 30));
        Signin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SigninActionPerformed(evt);
            }
        });
        getContentPane().add(Signin, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 250, 200, 40));

        go_back_button.setBackground(new java.awt.Color(0, 0, 0));
        go_back_button.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        go_back_button.setForeground(new java.awt.Color(0, 204, 0));
        go_back_button.setText("Go back");
        go_back_button.setPreferredSize(new java.awt.Dimension(90, 30));
        go_back_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                go_back_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(go_back_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 300, 200, -1));
        getContentPane().add(label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 40, 30));
        getContentPane().add(label2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, 40, 30));

        admin_pass.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        admin_pass.setToolTipText("");
        admin_pass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                admin_passKeyPressed(evt);
            }
        });
        getContentPane().add(admin_pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, 460, 30));
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 460));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void admin_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_admin_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_admin_idActionPerformed

    private void SigninActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SigninActionPerformed
        // TODO add your handling code here:
        SigninActionPerformed_Work();

    }//GEN-LAST:event_SigninActionPerformed

    private void go_back_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_go_back_buttonActionPerformed
        // TODO add your handling code here:
        Admin_Welcome ob = new Admin_Welcome();
        ob.setVisible(true);
        dispose();
    }//GEN-LAST:event_go_back_buttonActionPerformed

    private void admin_passKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_admin_passKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            SigninActionPerformed_Work();
        }
    }//GEN-LAST:event_admin_passKeyPressed

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
            java.util.logging.Logger.getLogger(User_Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(User_Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(User_Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(User_Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        Admin_Login adm = new Admin_Login();
        adm.setVisible(true);
        //log.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Signin;
    private javax.swing.JTextField admin_id;
    private javax.swing.JPasswordField admin_pass;
    private javax.swing.JButton go_back_button;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel label2;
    // End of variables declaration//GEN-END:variables
}
