
import java.awt.Color;
import static java.awt.Color.red;
import static java.awt.Color.yellow;
import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.sql.*;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import static java.awt.Color.red;
import static java.awt.Color.yellow;
import java.awt.Font;
import java.awt.Image;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Types.NULL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public final class TicketBooking extends javax.swing.JFrame {

//    public TicketBooking() {
//        initComponents();
//        ScaleImage1();
//        ScaleImage2();
//        ScaleImage3();
//        Set_Table_Prop();
//        setResizable(false);
//
//        bus_name.removeAllItems();
//        bus_name.addItem("Select");
//        setTitle("Ticket Booking");
//    }
    //Variable declaration
    String url = "jdbc:mysql://localhost:3306/busmania";
    String username = "root";
    String pass = "admin";
    Connection con;
    Statement stmt;
    Integer Nseat = 0;
    String USERID, aTime;
    String Price, BUS_NAME = "", pName = "", pContact = "", selectedFrom = "", selectedTo = "";
    String selectedDate = "", selectedTime = "", selectedSeat = "";
    boolean ok = false;

    Date next_day = new Date(new Date().getTime() + 86400000);
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
    String tommorrow = formatter.format(next_day);
    ArrayList<String> seat_list = new ArrayList<String>();

    public TicketBooking(String userid) {
        initComponents();
        Setup_Database_Connection();

        USERID = userid;
        ScaleImage1();
        ScaleImage2();
        ScaleImage3();
        Set_Table_Prop();
        setResizable(false);

        setTitle("Ticket Booking");

        Add_Seat_Names();
        Update_Seats();

        busnameComboBox.setSelectedIndex(0);
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

    public void setNseat() {
        if (BUS_NAME.compareTo("Select") != 0 && BUS_NAME.isEmpty() == false) {
            try {
                String query = "SELECT * FROM my_booking WHERE user = '" + USERID + "' AND bus = '"
                        + BUS_NAME + "' AND journeyDate = '" + tommorrow + "';";
                ResultSet rset = stmt.executeQuery(query);
                if (rset.next()) {
                    Nseat = Integer.valueOf(rset.getString("seatBooked"));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dateComboBox, ex, "getInputs", 0);
            }
        }
    }

    public void getInputs() {
        selectedFrom = (String) fromComboBox.getSelectedItem();
        selectedTo = (String) toComboBox.getSelectedItem();
        selectedDate = ((JTextField) dateComboBox.getDateEditor().getUiComponent()).getText();
        BUS_NAME = (String) busnameComboBox.getSelectedItem();
        pName = (String) nameTF.getText();
        pContact = (String) contactTF.getText();
        Price = (String) totalPriceLabel.getText();

        if (BUS_NAME == null) {
            BUS_NAME = "";
        }
        setNseat();
    }

    public void setInputs() {
        fromLabel.setText(selectedFrom);
        toLabel.setText(selectedTo);
        dateLabel.setText(selectedDate);

    }

    public void Set_Seats_To_Green() {
        A1.setBackground(Color.green);
        A2.setBackground(Color.green);
        A3.setBackground(Color.green);
        A4.setBackground(Color.green);
        B1.setBackground(Color.green);
        B2.setBackground(Color.green);
        B3.setBackground(Color.green);
        B4.setBackground(Color.green);
        C1.setBackground(Color.green);
        C2.setBackground(Color.green);
        C3.setBackground(Color.green);
        C4.setBackground(Color.green);
        D1.setBackground(Color.green);
        D2.setBackground(Color.green);
        D3.setBackground(Color.green);
        D4.setBackground(Color.green);
        E1.setBackground(Color.green);
        E2.setBackground(Color.green);
        E3.setBackground(Color.green);
        E4.setBackground(Color.green);
        F1.setBackground(Color.green);
        F2.setBackground(Color.green);
        F3.setBackground(Color.green);
        F4.setBackground(Color.green);
        G1.setBackground(Color.green);
        G2.setBackground(Color.green);
        G3.setBackground(Color.green);
        G4.setBackground(Color.green);
        H1.setBackground(Color.green);
        H2.setBackground(Color.green);
        H3.setBackground(Color.green);
        H4.setBackground(Color.green);
        I1.setBackground(Color.green);
        I2.setBackground(Color.green);
        I3.setBackground(Color.green);
        I4.setBackground(Color.green);
        J1.setBackground(Color.green);
        J2.setBackground(Color.green);
        J3.setBackground(Color.green);
        J4.setBackground(Color.green);
    }

    public boolean Route_Exists(String FROM, String TO) {
        if (FROM == "Select") {
//            JOptionPane.showMessageDialog(null, "Invalid 'from' location!", "Error", 2);
            Set_Seats_To_Green();
            return false;
        } else if (TO == "Select") {
//            JOptionPane.showMessageDialog(null, "Invalid 'to' location!", "Error", 2);
            Set_Seats_To_Green();
            return false;
        } else if (FROM == TO) {
//            JOptionPane.showMessageDialog(null, "Please select route!", "Error", 2);
            Set_Seats_To_Green();
            return false;
        }
        boolean ok = false;
        try {
            String query = "SELECT * FROM routes WHERE from_location = '" + FROM + "' AND to_location = '" + TO + "';";
            ResultSet rset = stmt.executeQuery(query);
            if (rset.next()) {
                ok = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Route Exists!", 0);
        }
        return ok;
    }

    public void Search2() {
        if (!ok) {
//            selectedFrom = ((String) fromComboBox.getSelectedItem()).toLowerCase();
//            selectedTo = ((String) toComboBox.getSelectedItem()).toLowerCase();
            getInputs();

            try {
                if (Route_Exists(selectedFrom.toLowerCase(), selectedTo.toLowerCase()) == true) {
                    String query = "SELECT * FROM " + selectedFrom.toLowerCase() + "_" + selectedTo.toLowerCase() + ";";
                    ResultSet rs = stmt.executeQuery(query);

                    ar.clear();
                    while (rs.next()) {
                        bus_info obj = new bus_info(rs.getString("bus_name"), rs.getInt("ticket_fare"), rs.getString("arrival_time"), rs.getString("departure_time"));
                        ar.add(obj);
                    }
                    showTable();
                } else {
                    ar.clear();
                    //        Clearing Table
                    DefaultTableModel dm = (DefaultTableModel) jTable1.getModel();
                    dm.getDataVector().removeAllElements();
                    dm.fireTableDataChanged();

                    busnameComboBox.removeAllItems();
                    busnameComboBox.addItem("Select");
                    busnameComboBox.setSelectedIndex(0);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex, "Error", 0);
            }

        }
    }

    public void Set_Seat_Colors() {

        String s = "Select";
        getInputs();
//        BUS_NAME = (String) busnameComboBox.getSelectedItem();
//        selectedFrom = ((String) fromComboBox.getSelectedItem()).toLowerCase();
//        selectedTo = ((String) toComboBox.getSelectedItem()).toLowerCase();

        if (selectedFrom.compareTo(s) == 0 || selectedTo.compareTo(s) == 0 || BUS_NAME == null
                || BUS_NAME.compareTo(s) == 0 || selectedFrom.compareTo(selectedTo) == 0) {
            Set_Seats_To_Green();
        } else {
            try {

                if (Route_Exists(selectedFrom.toLowerCase(), selectedTo.toLowerCase()) == true) {
                    String table_name = selectedFrom.toLowerCase() + "_" + selectedTo.toLowerCase();
                    String query = "SELECT * FROM " + table_name + " WHERE bus_name = '" + BUS_NAME + "';";

                    ResultSet rset1 = stmt.executeQuery(query);

                    if (rset1.next()) {
                        for (String sname : seat_list) {
                            String val = rset1.getString(sname);
                            int ok = 0;
                            if (val.compareTo("0") == 0) {
                                ok = 0;
                            } else {
                                ok = 1;
                            }

                            if (sname.compareTo("A1") == 0) {
                                if (ok == 1) {
                                    A1.setBackground(Color.red);
                                } else {
                                    A1.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("A2") == 0) {
                                if (ok == 1) {
                                    A2.setBackground(Color.red);
                                } else {
                                    A2.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("A3") == 0) {
                                if (ok == 1) {
                                    A3.setBackground(Color.red);
                                } else {
                                    A3.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("A4") == 0) {
                                if (ok == 1) {
                                    A4.setBackground(Color.red);
                                } else {
                                    A4.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("B1") == 0) {
                                if (ok == 1) {
                                    B1.setBackground(Color.red);
                                } else {
                                    B1.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("B2") == 0) {
                                if (ok == 1) {
                                    B2.setBackground(Color.red);
                                } else {
                                    B2.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("B3") == 0) {
                                if (ok == 1) {
                                    B3.setBackground(Color.red);
                                } else {
                                    B3.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("B4") == 0) {
                                if (ok == 1) {
                                    B4.setBackground(Color.red);
                                } else {
                                    B4.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("C1") == 0) {
                                if (ok == 1) {
                                    C1.setBackground(Color.red);
                                } else {
                                    C1.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("C2") == 0) {
                                if (ok == 1) {
                                    C2.setBackground(Color.red);
                                } else {
                                    C2.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("C3") == 0) {
                                if (ok == 1) {
                                    C3.setBackground(Color.red);
                                } else {
                                    C3.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("C4") == 0) {
                                if (ok == 1) {
                                    C4.setBackground(Color.red);
                                } else {
                                    C4.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("D1") == 0) {
                                if (ok == 1) {
                                    D1.setBackground(Color.red);
                                } else {
                                    D1.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("D2") == 0) {
                                if (ok == 1) {
                                    D2.setBackground(Color.red);
                                } else {
                                    D2.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("D3") == 0) {
                                if (ok == 1) {
                                    D3.setBackground(Color.red);
                                } else {
                                    D3.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("D4") == 0) {
                                if (ok == 1) {
                                    D4.setBackground(Color.red);
                                } else {
                                    D4.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("E1") == 0) {
                                if (ok == 1) {
                                    E1.setBackground(Color.red);
                                } else {
                                    E1.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("E2") == 0) {
                                if (ok == 1) {
                                    E2.setBackground(Color.red);
                                } else {
                                    E2.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("E3") == 0) {
                                if (ok == 1) {
                                    E3.setBackground(Color.red);
                                } else {
                                    E3.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("E4") == 0) {
                                if (ok == 1) {
                                    E4.setBackground(Color.red);
                                } else {
                                    E4.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("F1") == 0) {
                                if (ok == 1) {
                                    F1.setBackground(Color.red);
                                } else {
                                    F1.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("F2") == 0) {
                                if (ok == 1) {
                                    F2.setBackground(Color.red);
                                } else {
                                    F2.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("F3") == 0) {
                                if (ok == 1) {
                                    F3.setBackground(Color.red);
                                } else {
                                    F3.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("F4") == 0) {
                                if (ok == 1) {
                                    F4.setBackground(Color.red);
                                } else {
                                    F4.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("G1") == 0) {
                                if (ok == 1) {
                                    G1.setBackground(Color.red);
                                } else {
                                    G1.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("G2") == 0) {
                                if (ok == 1) {
                                    G2.setBackground(Color.red);
                                } else {
                                    G2.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("G3") == 0) {
                                if (ok == 1) {
                                    G3.setBackground(Color.red);
                                } else {
                                    G3.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("G4") == 0) {
                                if (ok == 1) {
                                    G4.setBackground(Color.red);
                                } else {
                                    G4.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("H1") == 0) {
                                if (ok == 1) {
                                    H1.setBackground(Color.red);
                                } else {
                                    H1.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("H2") == 0) {
                                if (ok == 1) {
                                    H2.setBackground(Color.red);
                                } else {
                                    H2.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("H3") == 0) {
                                if (ok == 1) {
                                    H3.setBackground(Color.red);
                                } else {
                                    H3.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("H4") == 0) {
                                if (ok == 1) {
                                    H4.setBackground(Color.red);
                                } else {
                                    H4.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("I1") == 0) {
                                if (ok == 1) {
                                    I1.setBackground(Color.red);
                                } else {
                                    I1.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("I2") == 0) {
                                if (ok == 1) {
                                    I2.setBackground(Color.red);
                                } else {
                                    I2.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("I3") == 0) {
                                if (ok == 1) {
                                    I3.setBackground(Color.red);
                                } else {
                                    I3.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("I4") == 0) {
                                if (ok == 1) {
                                    I4.setBackground(Color.red);
                                } else {
                                    I4.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("J1") == 0) {
                                if (ok == 1) {
                                    J1.setBackground(Color.red);
                                } else {
                                    J1.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("J2") == 0) {
                                if (ok == 1) {
                                    J2.setBackground(Color.red);
                                } else {
                                    J2.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("J3") == 0) {
                                if (ok == 1) {
                                    J3.setBackground(Color.red);
                                } else {
                                    J3.setBackground(Color.green);
                                }
                            } else if (sname.compareTo("J4") == 0) {
                                if (ok == 1) {
                                    J4.setBackground(Color.red);
                                } else {
                                    J4.setBackground(Color.green);
                                }
                            }

                        }
                    } else {
                        busnameComboBox.removeAllItems();
//                        busnameComboBox.addItem("Select");
//                        busnameComboBox.setSelectedIndex(0);
                        Set_Seats_To_Green();
                    }

                } else {
                    busnameComboBox.removeAllItems();
//                    busnameComboBox.addItem("Select");
//                    busnameComboBox.setSelectedIndex(0);
                    Set_Seats_To_Green();
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(rootPane, ex, "Set Seat Colors", 0);
            }
        }
    }

    class bus_info {

        public String BUS_NAME, ARRIVAL_TIME, DEPARTURE_TIME;
        public int FARE;

        public bus_info(String BUS_NAME, int FARE, String ARRIVAL_TIME, String DEPARTURE_TIME) {
            this.BUS_NAME = BUS_NAME;
            this.FARE = FARE;
            this.ARRIVAL_TIME = ARRIVAL_TIME;
            this.DEPARTURE_TIME = DEPARTURE_TIME;
        }
    }

    ArrayList<bus_info> ar = new ArrayList<>();
    ArrayList<String> ar2 = new ArrayList<>();

    public void showTable() {
        ar2.clear();

        busnameComboBox.removeAllItems();
        busnameComboBox.addItem("Select");

        DefaultTableModel mod = (DefaultTableModel) (jTable1.getModel());
        Object[] col = new Object[4];
        mod.setRowCount(0);
        for (int i = 0; i < ar.size(); i++) {
            col[0] = ar.get(i).BUS_NAME;
            col[1] = ar.get(i).FARE;
            col[2] = ar.get(i).ARRIVAL_TIME;
            col[3] = ar.get(i).DEPARTURE_TIME;
            mod.addRow(col);
            ar2.add(ar.get(i).BUS_NAME);
            busnameComboBox.addItem(ar.get(i).BUS_NAME);
        }
    }

    public void ScaleImage1() {
        ImageIcon ic;
        ic = new ImageIcon("D:\\Netbeans Projects\\BusMania\\src\\img\\steer.png");
        Image im = ic.getImage();
        Image imScale = im.getScaledInstance(Steer.getWidth(), Steer.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledicon = new ImageIcon(imScale);
        Steer.setIcon(scaledicon);
    }

    public void ScaleImage2() {
        ImageIcon ic;
        ic = new ImageIcon("D:\\Netbeans Projects\\BusMania\\src\\img\\SeatBack.jpg");
        Image im = ic.getImage();
        Image imScale = im.getScaledInstance(SeatB.getWidth(), SeatB.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledicon = new ImageIcon(imScale);
        SeatB.setIcon(scaledicon);
    }

    public void ScaleImage3() {
        ImageIcon ic;
        ic = new ImageIcon("D:\\Netbeans Projects\\BusMania\\src\\img\\back.jpg");
        Image im = ic.getImage();
        Image imScale = im.getScaledInstance(Background.getWidth(), Background.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledicon = new ImageIcon(imScale);
        Background.setIcon(scaledicon);
    }

    public void Set_Table_Prop() {
        JTableHeader tableHeader = jTable1.getTableHeader();
        tableHeader.setForeground(Color.black);

        Font headerFont = new Font("Verdana", Font.BOLD, 14);
        tableHeader.setFont(headerFont);
        //jTable1.getColumnModel().getColumn(4).setPreferredWidth(150);

        jTable1.setFont(new Font("Arial", Font.BOLD, 14));
        jTable1.setRowHeight(30);
    }

    public boolean is_valid_contact(String pContact) {
        if (pContact.length() != 11) {
            return false;
        }
        for (int i = 0; i < pContact.length(); i++) {
            if (pContact.charAt(i) >= '0' && pContact.charAt(i) <= '9') {
                continue;
            }
            return false;
        }
        return true;
    }

    public void paysubmit_buttonActionPerformed_work() {
        if (!ok) {
            JOptionPane.showMessageDialog(rootPane, "You should Book first!!!", "Error", 2);
            return;
        }
        getInputs();
//        selectedDate = ((JTextField) dateComboBox.getDateEditor().getUiComponent()).getText();
//        pName = nameTF.getText();
//        pContact = contactTF.getText();
//        Price = totalPriceLabel.getText();
//        BUS_NAME = (String) busnameComboBox.getSelectedItem();

        if (is_valid_contact(pContact)) {
            JOptionPane.showMessageDialog(rootPane, "Enter a valid number", "Error", 2);
        } else {
            int ind = busnameComboBox.getSelectedIndex();
            selectedTime = ar.get(ind - 1).DEPARTURE_TIME;
            aTime = ar.get(ind - 1).ARRIVAL_TIME;
            dispose();

            Payment p_ob = new Payment(pName, pContact, selectedFrom, selectedTo, selectedDate, selectedTime, selectedSeat, Nseat, Price, BUS_NAME, USERID, aTime);
            p_ob.setVisible(true);
        }
    }

    public boolean Check_Fields() {
        boolean ok = false;
        if (fromLabel.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Select your starting place");
        } else if (toLabel.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Select your destination place");
        } else if (ticketPriceLabel.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Select your journey Bus Plz!!");
        } else if (nameTF.getText().isEmpty() || contactTF.getText().isEmpty() || dateLabel.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Fill up required fields");
        } else {
            ok = true;
        }

        return ok;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fromComboBox = new javax.swing.JComboBox<>();
        toComboBox = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Search = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        nameTF = new javax.swing.JTextField();
        contactTF = new javax.swing.JTextField();
        A1 = new javax.swing.JButton();
        A2 = new javax.swing.JButton();
        A4 = new javax.swing.JButton();
        A3 = new javax.swing.JButton();
        B2 = new javax.swing.JButton();
        B4 = new javax.swing.JButton();
        B3 = new javax.swing.JButton();
        B1 = new javax.swing.JButton();
        C2 = new javax.swing.JButton();
        C4 = new javax.swing.JButton();
        C3 = new javax.swing.JButton();
        D2 = new javax.swing.JButton();
        D4 = new javax.swing.JButton();
        D3 = new javax.swing.JButton();
        D1 = new javax.swing.JButton();
        C1 = new javax.swing.JButton();
        E2 = new javax.swing.JButton();
        G1 = new javax.swing.JButton();
        E4 = new javax.swing.JButton();
        E3 = new javax.swing.JButton();
        F2 = new javax.swing.JButton();
        F4 = new javax.swing.JButton();
        F3 = new javax.swing.JButton();
        F1 = new javax.swing.JButton();
        G2 = new javax.swing.JButton();
        G4 = new javax.swing.JButton();
        G3 = new javax.swing.JButton();
        H2 = new javax.swing.JButton();
        H4 = new javax.swing.JButton();
        H3 = new javax.swing.JButton();
        E1 = new javax.swing.JButton();
        H1 = new javax.swing.JButton();
        I2 = new javax.swing.JButton();
        I4 = new javax.swing.JButton();
        I3 = new javax.swing.JButton();
        I1 = new javax.swing.JButton();
        J4 = new javax.swing.JButton();
        J3 = new javax.swing.JButton();
        J1 = new javax.swing.JButton();
        J2 = new javax.swing.JButton();
        Steer = new javax.swing.JLabel();
        jButton41 = new javax.swing.JButton();
        jButton42 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        SeatB = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        fromLabel = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        toLabel = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        totalPriceLabel = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        nseatTF = new javax.swing.JTextField();
        dateLabel = new javax.swing.JTextField();
        Reset = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        ticketPriceLabel = new javax.swing.JLabel();
        Book = new javax.swing.JButton();
        paysubmit_button = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton45 = new javax.swing.JButton();
        jButton46 = new javax.swing.JButton();
        jButton47 = new javax.swing.JButton();
        busnameComboBox = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        dateComboBox = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        fromComboBox.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        fromComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Noakhali", "Dhaka", "Chittagong", "CoxBazar", "Cumilla", "Feni", " " }));
        fromComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                fromComboBoxItemStateChanged(evt);
            }
        });
        fromComboBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fromComboBoxMouseClicked(evt);
            }
        });
        fromComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fromComboBoxActionPerformed(evt);
            }
        });
        getContentPane().add(fromComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 45, 140, 30));

        toComboBox.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        toComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Dhaka", "Cumilla", "Chittagong", "Feni", "CoxBazar", "Noakhali" }));
        toComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                toComboBoxItemStateChanged(evt);
            }
        });
        toComboBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toComboBoxMouseClicked(evt);
            }
        });
        toComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toComboBoxActionPerformed(evt);
            }
        });
        getContentPane().add(toComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 85, 140, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("FROM ");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 46, 40, 20));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("TO ");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 86, 20, 20));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("DATE ");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 126, 40, 20));

        Search.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Search.setText("FIND");
        Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchActionPerformed(evt);
            }
        });
        getContentPane().add(Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 172, 80, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("NAME :");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 70, 50, 20));

        nameTF.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        getContentPane().add(nameTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 70, 160, 30));

        contactTF.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        getContentPane().add(contactTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 110, 160, 30));

        A1.setBackground(new java.awt.Color(0, 255, 0));
        A1.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        A1.setText("A1");
        A1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A1ActionPerformed(evt);
            }
        });
        getContentPane().add(A1, new org.netbeans.lib.awtextra.AbsoluteConstraints(715, 100, 50, 30));

        A2.setBackground(new java.awt.Color(0, 255, 0));
        A2.setText("A2");
        A2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A2ActionPerformed(evt);
            }
        });
        getContentPane().add(A2, new org.netbeans.lib.awtextra.AbsoluteConstraints(765, 100, 50, 30));

        A4.setBackground(new java.awt.Color(0, 255, 0));
        A4.setText("A4");
        A4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A4ActionPerformed(evt);
            }
        });
        getContentPane().add(A4, new org.netbeans.lib.awtextra.AbsoluteConstraints(885, 100, 50, 30));

        A3.setBackground(new java.awt.Color(0, 255, 0));
        A3.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        A3.setText("A3");
        A3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A3ActionPerformed(evt);
            }
        });
        getContentPane().add(A3, new org.netbeans.lib.awtextra.AbsoluteConstraints(835, 100, 50, 30));

        B2.setBackground(new java.awt.Color(0, 255, 0));
        B2.setText("B2");
        B2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B2ActionPerformed(evt);
            }
        });
        getContentPane().add(B2, new org.netbeans.lib.awtextra.AbsoluteConstraints(765, 140, 50, 30));

        B4.setBackground(new java.awt.Color(0, 255, 0));
        B4.setText("B4");
        B4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B4ActionPerformed(evt);
            }
        });
        getContentPane().add(B4, new org.netbeans.lib.awtextra.AbsoluteConstraints(885, 140, 50, 30));

        B3.setBackground(new java.awt.Color(0, 255, 0));
        B3.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        B3.setText("B3");
        B3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B3ActionPerformed(evt);
            }
        });
        getContentPane().add(B3, new org.netbeans.lib.awtextra.AbsoluteConstraints(835, 140, 50, 30));

        B1.setBackground(new java.awt.Color(0, 255, 0));
        B1.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        B1.setText("B1");
        B1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B1ActionPerformed(evt);
            }
        });
        getContentPane().add(B1, new org.netbeans.lib.awtextra.AbsoluteConstraints(715, 140, 50, 30));

        C2.setBackground(new java.awt.Color(0, 255, 0));
        C2.setText("C2");
        C2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C2ActionPerformed(evt);
            }
        });
        getContentPane().add(C2, new org.netbeans.lib.awtextra.AbsoluteConstraints(765, 180, 50, 30));

        C4.setBackground(new java.awt.Color(0, 255, 0));
        C4.setText("C4");
        C4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C4ActionPerformed(evt);
            }
        });
        getContentPane().add(C4, new org.netbeans.lib.awtextra.AbsoluteConstraints(885, 180, 50, 30));

        C3.setBackground(new java.awt.Color(0, 255, 0));
        C3.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        C3.setText("C3");
        C3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C3ActionPerformed(evt);
            }
        });
        getContentPane().add(C3, new org.netbeans.lib.awtextra.AbsoluteConstraints(835, 180, 50, 30));

        D2.setBackground(new java.awt.Color(0, 255, 0));
        D2.setText("D2");
        D2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D2ActionPerformed(evt);
            }
        });
        getContentPane().add(D2, new org.netbeans.lib.awtextra.AbsoluteConstraints(765, 220, 50, 30));

        D4.setBackground(new java.awt.Color(0, 255, 0));
        D4.setText("D4");
        D4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D4ActionPerformed(evt);
            }
        });
        getContentPane().add(D4, new org.netbeans.lib.awtextra.AbsoluteConstraints(885, 220, 50, 30));

        D3.setBackground(new java.awt.Color(0, 255, 0));
        D3.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        D3.setText("D3");
        D3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D3ActionPerformed(evt);
            }
        });
        getContentPane().add(D3, new org.netbeans.lib.awtextra.AbsoluteConstraints(835, 220, 50, 30));

        D1.setBackground(new java.awt.Color(0, 255, 0));
        D1.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        D1.setText("D1");
        D1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D1ActionPerformed(evt);
            }
        });
        getContentPane().add(D1, new org.netbeans.lib.awtextra.AbsoluteConstraints(715, 220, 50, 30));

        C1.setBackground(new java.awt.Color(0, 255, 0));
        C1.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        C1.setText("C1");
        C1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C1ActionPerformed(evt);
            }
        });
        getContentPane().add(C1, new org.netbeans.lib.awtextra.AbsoluteConstraints(715, 180, 50, 30));

        E2.setBackground(new java.awt.Color(0, 255, 0));
        E2.setText("E2");
        E2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E2ActionPerformed(evt);
            }
        });
        getContentPane().add(E2, new org.netbeans.lib.awtextra.AbsoluteConstraints(765, 260, 50, 30));

        G1.setBackground(new java.awt.Color(0, 255, 0));
        G1.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        G1.setText("G1");
        G1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G1ActionPerformed(evt);
            }
        });
        getContentPane().add(G1, new org.netbeans.lib.awtextra.AbsoluteConstraints(715, 340, 50, 30));

        E4.setBackground(new java.awt.Color(0, 255, 0));
        E4.setText("E4");
        E4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E4ActionPerformed(evt);
            }
        });
        getContentPane().add(E4, new org.netbeans.lib.awtextra.AbsoluteConstraints(885, 260, 50, 30));

        E3.setBackground(new java.awt.Color(0, 255, 0));
        E3.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        E3.setText("E3");
        E3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E3ActionPerformed(evt);
            }
        });
        getContentPane().add(E3, new org.netbeans.lib.awtextra.AbsoluteConstraints(835, 260, 50, 30));

        F2.setBackground(new java.awt.Color(0, 255, 0));
        F2.setText("F2");
        F2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F2ActionPerformed(evt);
            }
        });
        getContentPane().add(F2, new org.netbeans.lib.awtextra.AbsoluteConstraints(765, 300, 50, 30));

        F4.setBackground(new java.awt.Color(0, 255, 0));
        F4.setText("F4");
        F4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F4ActionPerformed(evt);
            }
        });
        getContentPane().add(F4, new org.netbeans.lib.awtextra.AbsoluteConstraints(885, 300, 50, 30));

        F3.setBackground(new java.awt.Color(0, 255, 0));
        F3.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        F3.setText("F3");
        F3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F3ActionPerformed(evt);
            }
        });
        getContentPane().add(F3, new org.netbeans.lib.awtextra.AbsoluteConstraints(835, 300, 50, 30));

        F1.setBackground(new java.awt.Color(0, 255, 0));
        F1.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        F1.setText("F1");
        F1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F1ActionPerformed(evt);
            }
        });
        getContentPane().add(F1, new org.netbeans.lib.awtextra.AbsoluteConstraints(715, 300, 50, 30));

        G2.setBackground(new java.awt.Color(0, 255, 0));
        G2.setText("G2");
        G2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G2ActionPerformed(evt);
            }
        });
        getContentPane().add(G2, new org.netbeans.lib.awtextra.AbsoluteConstraints(765, 340, 50, 30));

        G4.setBackground(new java.awt.Color(0, 255, 0));
        G4.setText("G4");
        G4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G4ActionPerformed(evt);
            }
        });
        getContentPane().add(G4, new org.netbeans.lib.awtextra.AbsoluteConstraints(885, 340, 50, 30));

        G3.setBackground(new java.awt.Color(0, 255, 0));
        G3.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        G3.setText("G3");
        G3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G3ActionPerformed(evt);
            }
        });
        getContentPane().add(G3, new org.netbeans.lib.awtextra.AbsoluteConstraints(835, 340, 50, 30));

        H2.setBackground(new java.awt.Color(0, 255, 0));
        H2.setText("H2");
        H2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H2ActionPerformed(evt);
            }
        });
        getContentPane().add(H2, new org.netbeans.lib.awtextra.AbsoluteConstraints(765, 380, 50, 30));

        H4.setBackground(new java.awt.Color(0, 255, 0));
        H4.setText("H4");
        H4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H4ActionPerformed(evt);
            }
        });
        getContentPane().add(H4, new org.netbeans.lib.awtextra.AbsoluteConstraints(885, 380, 50, 30));

        H3.setBackground(new java.awt.Color(0, 255, 0));
        H3.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        H3.setText("H3");
        H3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H3ActionPerformed(evt);
            }
        });
        getContentPane().add(H3, new org.netbeans.lib.awtextra.AbsoluteConstraints(835, 380, 50, 30));

        E1.setBackground(new java.awt.Color(0, 255, 0));
        E1.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        E1.setText("E1");
        E1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E1ActionPerformed(evt);
            }
        });
        getContentPane().add(E1, new org.netbeans.lib.awtextra.AbsoluteConstraints(715, 260, 50, 30));

        H1.setBackground(new java.awt.Color(0, 255, 0));
        H1.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        H1.setText("H1");
        H1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H1ActionPerformed(evt);
            }
        });
        getContentPane().add(H1, new org.netbeans.lib.awtextra.AbsoluteConstraints(715, 380, 50, 30));

        I2.setBackground(new java.awt.Color(0, 255, 0));
        I2.setText("I2");
        I2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                I2ActionPerformed(evt);
            }
        });
        getContentPane().add(I2, new org.netbeans.lib.awtextra.AbsoluteConstraints(765, 420, 50, 30));

        I4.setBackground(new java.awt.Color(0, 255, 0));
        I4.setText("I4");
        I4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                I4ActionPerformed(evt);
            }
        });
        getContentPane().add(I4, new org.netbeans.lib.awtextra.AbsoluteConstraints(885, 420, 50, 30));

        I3.setBackground(new java.awt.Color(0, 255, 0));
        I3.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        I3.setText("I3");
        I3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                I3ActionPerformed(evt);
            }
        });
        getContentPane().add(I3, new org.netbeans.lib.awtextra.AbsoluteConstraints(835, 420, 50, 30));

        I1.setBackground(new java.awt.Color(0, 255, 0));
        I1.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        I1.setText("I1");
        I1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                I1ActionPerformed(evt);
            }
        });
        getContentPane().add(I1, new org.netbeans.lib.awtextra.AbsoluteConstraints(715, 420, 50, 30));

        J4.setBackground(new java.awt.Color(0, 255, 0));
        J4.setText("J4");
        J4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                J4ActionPerformed(evt);
            }
        });
        getContentPane().add(J4, new org.netbeans.lib.awtextra.AbsoluteConstraints(885, 460, 50, 30));

        J3.setBackground(new java.awt.Color(0, 255, 0));
        J3.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        J3.setText("J3");
        J3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                J3ActionPerformed(evt);
            }
        });
        getContentPane().add(J3, new org.netbeans.lib.awtextra.AbsoluteConstraints(835, 460, 50, 30));

        J1.setBackground(new java.awt.Color(0, 255, 0));
        J1.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        J1.setText("J1");
        J1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                J1ActionPerformed(evt);
            }
        });
        getContentPane().add(J1, new org.netbeans.lib.awtextra.AbsoluteConstraints(715, 460, 50, 30));

        J2.setBackground(new java.awt.Color(0, 255, 0));
        J2.setText("J2");
        J2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                J2ActionPerformed(evt);
            }
        });
        getContentPane().add(J2, new org.netbeans.lib.awtextra.AbsoluteConstraints(765, 460, 50, 30));
        getContentPane().add(Steer, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 44, 40, 40));

        jButton41.setBackground(new java.awt.Color(0, 255, 0));
        getContentPane().add(jButton41, new org.netbeans.lib.awtextra.AbsoluteConstraints(718, 40, 30, 20));

        jButton42.setBackground(new java.awt.Color(255, 0, 0));
        getContentPane().add(jButton42, new org.netbeans.lib.awtextra.AbsoluteConstraints(718, 70, 30, 20));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText(":    Available");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(752, 36, 100, 20));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText(":    Booked");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(752, 66, 100, 20));

        SeatB.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        getContentPane().add(SeatB, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 20, 230, 480));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("CONTACT :");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 110, 70, 20));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("FROM :");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 30, 50, 20));

        fromLabel.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        getContentPane().add(fromLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 30, 110, 20));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("TO :");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 60, 30, 20));

        toLabel.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        getContentPane().add(toLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 60, 110, 20));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("BUSNAME :");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 70, 20));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 120, -1, -1));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("DATE :");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 90, 40, 20));

        totalPriceLabel.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        getContentPane().add(totalPriceLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 160, 100, 20));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setText("NO OF SEATS:");
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 150, 80, 20));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setText("TOTAL PRICE :");
        getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 160, 90, 20));

        nseatTF.setEditable(false);
        nseatTF.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        getContentPane().add(nseatTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 150, 160, 30));

        dateLabel.setEditable(false);
        dateLabel.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        dateLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateLabelActionPerformed(evt);
            }
        });
        getContentPane().add(dateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 90, 130, 30));

        Reset.setBackground(new java.awt.Color(0, 0, 0));
        Reset.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        Reset.setForeground(new java.awt.Color(255, 255, 255));
        Reset.setText("RESET");
        Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetActionPerformed(evt);
            }
        });
        getContentPane().add(Reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(302, 203, 80, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("PRICE :");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 130, 40, 20));

        ticketPriceLabel.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        getContentPane().add(ticketPriceLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 130, 90, 20));

        Book.setBackground(new java.awt.Color(0, 0, 0));
        Book.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        Book.setForeground(new java.awt.Color(255, 255, 255));
        Book.setText("BOOK");
        Book.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BookActionPerformed(evt);
            }
        });
        getContentPane().add(Book, new org.netbeans.lib.awtextra.AbsoluteConstraints(422, 203, 80, 30));

        paysubmit_button.setBackground(new java.awt.Color(0, 0, 0));
        paysubmit_button.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        paysubmit_button.setForeground(new java.awt.Color(255, 255, 255));
        paysubmit_button.setText("PAY");
        paysubmit_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paysubmit_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(paysubmit_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(542, 203, 80, 30));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bus Name", "Ticket Fare", "Departure Time", "Arrival Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 710, 250));

        jButton45.setBackground(new java.awt.Color(0, 0, 0));
        jButton45.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton45.setForeground(new java.awt.Color(255, 255, 255));
        jButton45.setText("LOG OUT");
        jButton45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton45ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton45, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 510, 140, 30));

        jButton46.setBackground(new java.awt.Color(0, 0, 0));
        jButton46.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jButton46.setForeground(new java.awt.Color(255, 255, 255));
        jButton46.setText("MyBooking");
        jButton46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton46ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton46, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 510, 120, 30));

        jButton47.setBackground(new java.awt.Color(0, 0, 0));
        jButton47.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jButton47.setForeground(new java.awt.Color(255, 255, 255));
        jButton47.setText("Complain");
        jButton47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton47ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton47, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 510, 100, 30));

        busnameComboBox.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        busnameComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        busnameComboBox.setToolTipText("");
        busnameComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                busnameComboBoxItemStateChanged(evt);
            }
        });
        busnameComboBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                busnameComboBoxMouseClicked(evt);
            }
        });
        busnameComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                busnameComboBoxActionPerformed(evt);
            }
        });
        getContentPane().add(busnameComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, 160, -1));

        jLabel16.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 480, 230));

        dateComboBox.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateComboBoxPropertyChange(evt);
            }
        });
        getContentPane().add(dateComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 140, -1));

        jLabel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 230, 230));
        getContentPane().add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 970, 580));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void fromComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fromComboBoxActionPerformed
        // TODO add your handling code here:

        getInputs();
        Search2();
        Set_Seat_Colors();

//        selectedFrom = fromComboBox.getSelectedItem().toString();
//        System.out.println(selectedFrom);
        if (selectedFrom.compareTo("Select") == 0) {
            fromLabel.setText("");
        } else
            fromLabel.setText(selectedFrom);
    }//GEN-LAST:event_fromComboBoxActionPerformed

    private void toComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toComboBoxActionPerformed
        // TODO add your handling code here:

        getInputs();
        Search2();
        Set_Seat_Colors();

//        selectedTo = toComboBox.getSelectedItem().toString();
//        System.out.println(selectedTo);
        if (selectedTo.compareTo("Select") == 0) {
            toLabel.setText("");
        } else
            toLabel.setText(selectedTo);
    }//GEN-LAST:event_toComboBoxActionPerformed

    private void A1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A1ActionPerformed

        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (A1.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (A1.getBackground() == yellow) {
                A1.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                A1.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "A1 ";
            }
        }

    }//GEN-LAST:event_A1ActionPerformed

    private void jDateChooser2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser2PropertyChange
        // TODO add your handling code here:
        if ("date".equals(evt.getPropertyName())) {
            Date date = dateComboBox.getDateEditor().getDate();
            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
            String mydate = sf.format(date);
            dateLabel.setText(mydate);
        } else {
            dateLabel.setText(null);
        }
    }//GEN-LAST:event_jDateChooser2PropertyChange

    private void A2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A2ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (A2.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (A2.getBackground() == yellow) {
                A2.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                A2.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "A2 ";
            }
        }

    }//GEN-LAST:event_A2ActionPerformed

    private void A3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A3ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (A3.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (A3.getBackground() == yellow) {
                A3.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                A3.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "A3 ";
            }
        }
    }//GEN-LAST:event_A3ActionPerformed

    private void A4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A4ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (A4.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (A4.getBackground() == yellow) {
                A4.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                A4.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "A4 ";
            }
        }
    }//GEN-LAST:event_A4ActionPerformed

    private void B1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B1ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (B1.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (B1.getBackground() == yellow) {
                B1.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                B1.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "B1 ";
            }
        }
    }//GEN-LAST:event_B1ActionPerformed

    private void B2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B2ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (B2.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (B2.getBackground() == yellow) {
                B2.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                B2.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "B2 ";
            }
        }
    }//GEN-LAST:event_B2ActionPerformed

    private void B3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B3ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (B3.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (B3.getBackground() == yellow) {
                B3.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                B3.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "B3 ";
            }
        }
    }//GEN-LAST:event_B3ActionPerformed

    private void B4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B4ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (B4.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (B4.getBackground() == yellow) {
                B4.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                B4.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "B4 ";
            }
        }
    }//GEN-LAST:event_B4ActionPerformed

    private void C1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C1ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (C1.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (C1.getBackground() == yellow) {
                C1.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                C1.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "C1 ";
            }
        }
    }//GEN-LAST:event_C1ActionPerformed

    private void C2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C2ActionPerformed
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (C2.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (C2.getBackground() == yellow) {
                C2.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                C2.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "C2 ";
            }
        }
    }//GEN-LAST:event_C2ActionPerformed

    private void C3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C3ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (C3.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (C3.getBackground() == yellow) {
                C3.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                C3.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "C3 ";
            }
        }
    }//GEN-LAST:event_C3ActionPerformed

    private void C4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C4ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (C4.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (C4.getBackground() == yellow) {
                C4.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                C4.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "C4 ";
            }
        }
    }//GEN-LAST:event_C4ActionPerformed

    private void jButton45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton45ActionPerformed
        // TODO add your handling code here:
        User_Login lg = new User_Login();
        lg.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton45ActionPerformed

    private void D1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D1ActionPerformed
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (D1.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (D1.getBackground() == yellow) {
                D1.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                D1.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "D1 ";
            }
        }
    }//GEN-LAST:event_D1ActionPerformed

    private void D2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D2ActionPerformed
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (D2.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (D2.getBackground() == yellow) {
                D2.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                D2.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "D2 ";
            }
        }
    }//GEN-LAST:event_D2ActionPerformed

    private void D3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D3ActionPerformed
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (D3.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (D3.getBackground() == yellow) {
                D3.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                D3.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "D3 ";
            }
        }
    }//GEN-LAST:event_D3ActionPerformed

    private void D4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D4ActionPerformed
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (D4.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (D4.getBackground() == yellow) {
                D4.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                D4.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "D4 ";
            }
        }
    }//GEN-LAST:event_D4ActionPerformed

    private void E1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E1ActionPerformed
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (E1.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (E1.getBackground() == yellow) {
                E1.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                E1.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "E1 ";
            }
        }
    }//GEN-LAST:event_E1ActionPerformed

    private void E2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E2ActionPerformed
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (E2.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (E2.getBackground() == yellow) {
                E2.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                E2.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "E2 ";
            }
        }
    }//GEN-LAST:event_E2ActionPerformed

    private void E3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E3ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (E3.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (E3.getBackground() == yellow) {
                E3.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                E3.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "E3 ";
            }
        }
    }//GEN-LAST:event_E3ActionPerformed

    private void E4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E4ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (E4.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (E4.getBackground() == yellow) {
                E4.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                E4.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "E4 ";
            }
        }
    }//GEN-LAST:event_E4ActionPerformed

    private void F1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F1ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (F1.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (F1.getBackground() == yellow) {
                F1.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else {
                F1.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "F1 ";
            }
        }
    }//GEN-LAST:event_F1ActionPerformed

    private void F2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F2ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (F2.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (F2.getBackground() == yellow) {
                F2.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                F2.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "F2 ";
            }
        }
    }//GEN-LAST:event_F2ActionPerformed

    private void F3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F3ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (F3.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (F3.getBackground() == yellow) {
                F3.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                F3.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "F3 ";
            }
        }
    }//GEN-LAST:event_F3ActionPerformed

    private void F4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_F4ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (F4.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (F4.getBackground() == yellow) {
                F4.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                F4.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "F4 ";
            }
        }
    }//GEN-LAST:event_F4ActionPerformed

    private void G1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G1ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (G1.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (G1.getBackground() == yellow) {
                G1.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                G1.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "G1 ";
            }
        }
    }//GEN-LAST:event_G1ActionPerformed

    private void G2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G2ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (G2.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (G2.getBackground() == yellow) {
                G2.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                G2.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "G2 ";
            }
        }
    }//GEN-LAST:event_G2ActionPerformed

    private void G3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G3ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (G3.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (G3.getBackground() == yellow) {
                G3.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                G3.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "G3 ";
            }
        }
    }//GEN-LAST:event_G3ActionPerformed

    private void G4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G4ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (G4.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (G4.getBackground() == yellow) {
                G4.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                G4.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "G4 ";
            }
        }
    }//GEN-LAST:event_G4ActionPerformed

    private void H1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_H1ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (H1.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (H1.getBackground() == yellow) {
                H1.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                H1.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "H1 ";
            }
        }
    }//GEN-LAST:event_H1ActionPerformed

    private void H2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_H2ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (H2.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (H2.getBackground() == yellow) {
                H2.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                H2.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "H2 ";
            }
        }
    }//GEN-LAST:event_H2ActionPerformed

    private void H3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_H3ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (H3.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (H3.getBackground() == yellow) {
                H3.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                H3.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "H3 ";
            }
        }
    }//GEN-LAST:event_H3ActionPerformed

    private void H4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_H4ActionPerformed
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (H4.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (H4.getBackground() == yellow) {
                H4.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                H4.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "H4 ";
            }
        }
    }//GEN-LAST:event_H4ActionPerformed

    private void I1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_I1ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (I1.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (I1.getBackground() == yellow) {
                I1.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                I1.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "I1 ";
            }
        }
    }//GEN-LAST:event_I1ActionPerformed

    private void I2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_I2ActionPerformed
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (I2.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (I2.getBackground() == yellow) {
                I2.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                I2.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "I2 ";
            }
        }
    }//GEN-LAST:event_I2ActionPerformed

    private void I3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_I3ActionPerformed
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (I3.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (I3.getBackground() == yellow) {
                I3.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                I3.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "I3 ";
            }
        }
    }//GEN-LAST:event_I3ActionPerformed

    private void I4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_I4ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (I4.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (I4.getBackground() == yellow) {
                I4.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                I4.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "I4 ";
            }
        }
    }//GEN-LAST:event_I4ActionPerformed

    private void J1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_J1ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (J1.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (J1.getBackground() == yellow) {
                J1.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                J1.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "J1 ";
            }
        }
    }//GEN-LAST:event_J1ActionPerformed

    private void J2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_J2ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (J2.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (J2.getBackground() == yellow) {
                J2.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else {
                J2.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "J2 ";
            }
        }
    }//GEN-LAST:event_J2ActionPerformed

    private void J3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_J3ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (J3.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (J3.getBackground() == yellow) {
                J3.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else {
                J3.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "J3 ";
            }
        }
    }//GEN-LAST:event_J3ActionPerformed

    private void J4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_J4ActionPerformed
        // TODO add your handling code here:
        if (Check_Fields() == true) {
            if (Nseat == 4) {
                JOptionPane.showMessageDialog(rootPane, "You can't buy more than 4 Tickets");
            } else if (J4.getBackground() == red) {
                JOptionPane.showMessageDialog(rootPane, "Seat is already Booked!", "Error", 0);
            } else if (J4.getBackground() == yellow) {
                J4.setBackground(Color.green);
                Nseat--;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat = " ";
            } else {
                J4.setBackground(Color.yellow);
                Nseat++;
                String seat = Nseat.toString();
                nseatTF.setText(seat);
                selectedSeat += "J4 ";
            }
        }
    }//GEN-LAST:event_J4ActionPerformed

    private void BookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BookActionPerformed
        try {
            if (nseatTF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Select your seat first");
            } else {
//            String selectd = (String) jComboBox2.getSelectedItem();
                int ind = busnameComboBox.getSelectedIndex();
                getInputs();
//                BUS_NAME = busnameComboBox.getSelectedItem().toString();

                String table_name = selectedFrom.toLowerCase() + "_" + selectedTo.toLowerCase();
                String query = "";
//            jLabel11.setText(String.valueOf(ar.get(ind-1).FARE));
                totalPriceLabel.setText(String.valueOf(Nseat * ar.get(ind - 1).FARE));
                ok = true;
                if (A1.getBackground() == yellow) {
                    A1.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET A1 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (A2.getBackground() == yellow) {
                    A2.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET A2 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (A3.getBackground() == yellow) {
                    A3.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET A3 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (A4.getBackground() == yellow) {
                    A4.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET A4 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (B1.getBackground() == yellow) {
                    B1.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET B1 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (B2.getBackground() == yellow) {
                    B2.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET B2 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (B3.getBackground() == yellow) {
                    B3.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET B3 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (B4.getBackground() == yellow) {
                    B4.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET B4 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (C1.getBackground() == yellow) {
                    C1.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET C1 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (C2.getBackground() == yellow) {
                    C2.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET C2 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (C3.getBackground() == yellow) {
                    C3.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET C3 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (C4.getBackground() == yellow) {
                    C4.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET C4 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (D1.getBackground() == yellow) {
                    D1.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET D1 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (D2.getBackground() == yellow) {
                    D2.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET D2 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (D3.getBackground() == yellow) {
                    D3.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET D3 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (D4.getBackground() == yellow) {
                    D4.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET D4 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (E1.getBackground() == yellow) {
                    E1.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET E1 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (E2.getBackground() == yellow) {
                    E2.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET E2 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (E3.getBackground() == yellow) {
                    E3.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET E3 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (E4.getBackground() == yellow) {
                    E4.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET E4 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (F1.getBackground() == yellow) {
                    F1.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET F1 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (F2.getBackground() == yellow) {
                    F2.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET F2 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (F3.getBackground() == yellow) {
                    F3.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET F3 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (F4.getBackground() == yellow) {
                    F4.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET F4 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (G1.getBackground() == yellow) {
                    G1.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET G1 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (G2.getBackground() == yellow) {
                    G2.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET G2 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (G3.getBackground() == yellow) {
                    G3.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET G3 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (G4.getBackground() == yellow) {
                    G4.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET G4 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (H1.getBackground() == yellow) {
                    H1.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET H1 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (H2.getBackground() == yellow) {
                    H2.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET H2 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (H3.getBackground() == yellow) {
                    H3.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET H3 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (H4.getBackground() == yellow) {
                    H4.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET H4 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (I1.getBackground() == yellow) {
                    I1.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET I1 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (I2.getBackground() == yellow) {
                    I2.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET I2 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (I3.getBackground() == yellow) {
                    I3.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET I3 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (I4.getBackground() == yellow) {
                    I4.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET I4 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (J1.getBackground() == yellow) {
                    J1.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET J1 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (J2.getBackground() == yellow) {
                    J2.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET J2 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (J3.getBackground() == yellow) {
                    J3.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET J3 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
                if (J4.getBackground() == yellow) {
                    J4.setBackground(Color.red);
                    query = "UPDATE " + table_name + " SET J4 = '1' WHERE bus_name = '" + BUS_NAME + "';";
                    stmt.executeUpdate(query);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex, "Error", 2);
        }
    }//GEN-LAST:event_BookActionPerformed

    private void ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetActionPerformed
        if (ok) {
            JOptionPane.showMessageDialog(rootPane, "Seats were booked,So you can't Reset");
            return;
        }
        Nseat = 0;
        fromComboBox.setSelectedIndex(0);
        toComboBox.setSelectedIndex(0);
        fromLabel.setText("");
        toLabel.setText("");
        dateLabel.setText("");
        nameTF.setText("");
        contactTF.setText("");
        nseatTF.setText("");
        ticketPriceLabel.setText("");
        totalPriceLabel.setText("");
        selectedSeat = " ";
//        Clearing Table
        DefaultTableModel dm = (DefaultTableModel) jTable1.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged();

        //Clearing Date
        try {
            dateComboBox.setCalendar(null);
        } catch (Exception e) {
        }

        //clear seatcolor
        if (A1.getBackground() == yellow) {
            A1.setBackground(Color.green);
        }
        if (A2.getBackground() == yellow) {
            A2.setBackground(Color.green);
        }
        if (A3.getBackground() == yellow) {
            A3.setBackground(Color.green);
        }
        if (A4.getBackground() == yellow) {
            A4.setBackground(Color.green);
        }
        if (B1.getBackground() == yellow) {
            B1.setBackground(Color.green);
        }
        if (B2.getBackground() == yellow) {
            B2.setBackground(Color.green);
        }
        if (B3.getBackground() == yellow) {
            B3.setBackground(Color.green);
        }
        if (B4.getBackground() == yellow) {
            B4.setBackground(Color.green);
        }
        if (C1.getBackground() == yellow) {
            C1.setBackground(Color.green);
        }
        if (C2.getBackground() == yellow) {
            C2.setBackground(Color.green);
        }
        if (C3.getBackground() == yellow) {
            C3.setBackground(Color.green);
        }
        if (C4.getBackground() == yellow) {
            C4.setBackground(Color.green);
        }
        if (D1.getBackground() == yellow) {
            D1.setBackground(Color.green);
        }
        if (D2.getBackground() == yellow) {
            D2.setBackground(Color.green);
        }
        if (D3.getBackground() == yellow) {
            D3.setBackground(Color.green);
        }
        if (D4.getBackground() == yellow) {
            D4.setBackground(Color.green);
        }
        if (E1.getBackground() == yellow) {
            E1.setBackground(Color.green);
        }
        if (E2.getBackground() == yellow) {
            E2.setBackground(Color.green);
        }
        if (E3.getBackground() == yellow) {
            E3.setBackground(Color.green);
        }
        if (E4.getBackground() == yellow) {
            E4.setBackground(Color.green);
        }
        if (F1.getBackground() == yellow) {
            F1.setBackground(Color.green);
        }
        if (F2.getBackground() == yellow) {
            F2.setBackground(Color.green);
        }
        if (F3.getBackground() == yellow) {
            F3.setBackground(Color.green);
        }
        if (F4.getBackground() == yellow) {
            F4.setBackground(Color.green);
        }
        if (G1.getBackground() == yellow) {
            G1.setBackground(Color.green);
        }
        if (G2.getBackground() == yellow) {
            G2.setBackground(Color.green);
        }
        if (G3.getBackground() == yellow) {
            G3.setBackground(Color.green);
        }
        if (G4.getBackground() == yellow) {
            G4.setBackground(Color.green);
        }
        if (H1.getBackground() == yellow) {
            H1.setBackground(Color.green);
        }
        if (H2.getBackground() == yellow) {
            H2.setBackground(Color.green);
        }
        if (H3.getBackground() == yellow) {
            H3.setBackground(Color.green);
        }
        if (H4.getBackground() == yellow) {
            H4.setBackground(Color.green);
        }
        if (I1.getBackground() == yellow) {
            I1.setBackground(Color.green);
        }
        if (I2.getBackground() == yellow) {
            I2.setBackground(Color.green);
        }
        if (I3.getBackground() == yellow) {
            I3.setBackground(Color.green);
        }
        if (I4.getBackground() == yellow) {
            I4.setBackground(Color.green);
        }
        if (J1.getBackground() == yellow) {
            J1.setBackground(Color.green);
        }
        if (J2.getBackground() == yellow) {
            J2.setBackground(Color.green);
        }
        if (J3.getBackground() == yellow) {
            J3.setBackground(Color.green);
        }
        if (J4.getBackground() == yellow)
            J4.setBackground(Color.green);
    }//GEN-LAST:event_ResetActionPerformed

    private void paysubmit_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paysubmit_buttonActionPerformed
        paysubmit_buttonActionPerformed_work();

    }//GEN-LAST:event_paysubmit_buttonActionPerformed

    private void dateLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateLabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dateLabelActionPerformed

    private void jButton47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton47ActionPerformed
        Complain_Box_User cbu = new Complain_Box_User(USERID);
        cbu.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton47ActionPerformed

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchActionPerformed
        // TODO add your handling code here:
        if (!ok) {
            getInputs();
//            selectedFrom = ((String) fromComboBox.getSelectedItem()).toLowerCase();
//            selectedTo = ((String) toComboBox.getSelectedItem()).toLowerCase();

            try {
                if (Route_Exists(selectedFrom.toLowerCase(), selectedTo.toLowerCase()) == true) {
                    String query = "SELECT * FROM " + selectedFrom.toLowerCase() + "_" + selectedTo.toLowerCase() + ";";
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = (Connection) DriverManager.getConnection(url, username, pass); //2
                    Statement st = (Statement) con.createStatement();
                    ResultSet rs = st.executeQuery(query);

                    ar.clear();
                    while (rs.next()) {
                        bus_info obj = new bus_info(rs.getString("bus_name"), rs.getInt("ticket_fare"), rs.getString("arrival_time"), rs.getString("departure_time"));
                        ar.add(obj);
                    }
                    showTable();
                } else {
                    ar.clear();

                    JOptionPane.showMessageDialog(null, "Invalid route!", "Error", 2);

                    DefaultTableModel dm = (DefaultTableModel) jTable1.getModel();
                    dm.getDataVector().removeAllElements();
                    dm.fireTableDataChanged();

                    busnameComboBox.removeAllItems();
                    busnameComboBox.addItem("Select");
                    busnameComboBox.setSelectedIndex(0);
                }

            } catch (ClassNotFoundException | SQLException ex) {
                JOptionPane.showMessageDialog(null, ex, "Error", 0);
            }

        }
    }//GEN-LAST:event_SearchActionPerformed

    private void busnameComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_busnameComboBoxActionPerformed
        // TODO add your handling code here:

        int ind = busnameComboBox.getSelectedIndex();
        getInputs();
//        BUS_NAME = (String) busnameComboBox.getSelectedItem();

        Set_Seat_Colors();

//        System.out.println(BUS_NAME);
        if (ind > 0) {
            ticketPriceLabel.setText(String.valueOf(ar.get(ind - 1).FARE));
        }

    }//GEN-LAST:event_busnameComboBoxActionPerformed

    private void jButton46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton46ActionPerformed
        // TODO add your handling code here:
        Mybooking mb = new Mybooking(USERID);
        mb.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton46ActionPerformed

    private void dateComboBoxPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateComboBoxPropertyChange
        // TODO add your handling code here:
        getInputs();
        setInputs();
//        selectedDate = ((JTextField) dateComboBox.getDateEditor().getUiComponent()).getText();
//        dateLabel.setText(selectedDate);
    }//GEN-LAST:event_dateComboBoxPropertyChange

    private void busnameComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_busnameComboBoxItemStateChanged
        // TODO add your handling code here:
        Set_Seat_Colors();

    }//GEN-LAST:event_busnameComboBoxItemStateChanged

    private void fromComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_fromComboBoxItemStateChanged
        // TODO add your handling code here:
//        Set_Seat_Colors();
    }//GEN-LAST:event_fromComboBoxItemStateChanged

    private void toComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_toComboBoxItemStateChanged
        // TODO add your handling code here:
//        Set_Seat_Colors();
    }//GEN-LAST:event_toComboBoxItemStateChanged

    private void busnameComboBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_busnameComboBoxMouseClicked
        // TODO add your handling code here:
//        Set_Seat_Colors();
    }//GEN-LAST:event_busnameComboBoxMouseClicked

    private void fromComboBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fromComboBoxMouseClicked
        // TODO add your handling code here:
//        Set_Seat_Colors();
    }//GEN-LAST:event_fromComboBoxMouseClicked

    private void toComboBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toComboBoxMouseClicked
        // TODO add your handling code here:
//        Set_Seat_Colors();
    }//GEN-LAST:event_toComboBoxMouseClicked

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
            java.util.logging.Logger.getLogger(TicketBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TicketBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TicketBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TicketBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
//            new TicketBooking().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton A1;
    private javax.swing.JButton A2;
    private javax.swing.JButton A3;
    private javax.swing.JButton A4;
    private javax.swing.JButton B1;
    private javax.swing.JButton B2;
    private javax.swing.JButton B3;
    private javax.swing.JButton B4;
    private javax.swing.JLabel Background;
    private javax.swing.JButton Book;
    private javax.swing.JButton C1;
    private javax.swing.JButton C2;
    private javax.swing.JButton C3;
    private javax.swing.JButton C4;
    private javax.swing.JButton D1;
    private javax.swing.JButton D2;
    private javax.swing.JButton D3;
    private javax.swing.JButton D4;
    private javax.swing.JButton E1;
    private javax.swing.JButton E2;
    private javax.swing.JButton E3;
    private javax.swing.JButton E4;
    private javax.swing.JButton F1;
    private javax.swing.JButton F2;
    private javax.swing.JButton F3;
    private javax.swing.JButton F4;
    private javax.swing.JButton G1;
    private javax.swing.JButton G2;
    private javax.swing.JButton G3;
    private javax.swing.JButton G4;
    private javax.swing.JButton H1;
    private javax.swing.JButton H2;
    private javax.swing.JButton H3;
    private javax.swing.JButton H4;
    private javax.swing.JButton I1;
    private javax.swing.JButton I2;
    private javax.swing.JButton I3;
    private javax.swing.JButton I4;
    private javax.swing.JButton J1;
    private javax.swing.JButton J2;
    private javax.swing.JButton J3;
    private javax.swing.JButton J4;
    private javax.swing.JButton Reset;
    private javax.swing.JButton Search;
    private javax.swing.JLabel SeatB;
    private javax.swing.JLabel Steer;
    private javax.swing.JComboBox<String> busnameComboBox;
    private javax.swing.JTextField contactTF;
    private com.toedter.calendar.JDateChooser dateComboBox;
    private javax.swing.JTextField dateLabel;
    private javax.swing.JComboBox<String> fromComboBox;
    private javax.swing.JLabel fromLabel;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton42;
    private javax.swing.JButton jButton45;
    private javax.swing.JButton jButton46;
    private javax.swing.JButton jButton47;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField nameTF;
    private javax.swing.JTextField nseatTF;
    private javax.swing.JButton paysubmit_button;
    private javax.swing.JLabel ticketPriceLabel;
    private javax.swing.JComboBox<String> toComboBox;
    private javax.swing.JLabel toLabel;
    private javax.swing.JLabel totalPriceLabel;
    // End of variables declaration//GEN-END:variables
}
