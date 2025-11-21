/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data;

import business.Consultant;
import java.util.ArrayList;
import java.sql.*;
/**
 *
 * @author icbt1
 */
public class ConsultantDB {
    private Connection cn;

    public ConsultantDB() {
        try {
            String url = "jdbc:mysql://localhost:3306/hospitaldb";
            String user = "root";
            String password = "";
            cn = DriverManager.getConnection(url, user, password);
            if (cn != null) {
                System.out.println("Database connected successfully");
            } else {
                System.out.println("Database connection failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean add(Consultant c) {
        String insert = "INSERT INTO Consultant (cID, firstName, lastName, category, hospital, telephone) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = cn.prepareStatement(insert);
            ps.setInt(1, c.getcID());
            ps.setString(2, c.getFirstName());
            ps.setString(3, c.getLastName());
            ps.setString(4, c.getCategory());
            ps.setString(5, c.getHospital());
            ps.setString(6, c.getTelephone());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public ArrayList<Consultant> getAll() {
        ArrayList<Consultant> cList = new ArrayList<>();
        String select = "SELECT * FROM Consultant";
        try {
            PreparedStatement ps = cn.prepareStatement(select);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int cID = rs.getInt("cID");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String category = rs.getString("category");
                String hospital = rs.getString("hospital");
                String telephone = rs.getString("telephone");
                Consultant c = new Consultant(cID, firstName, lastName, category, hospital, telephone);
                cList.add(c);
            }
            rs.close();
            ps.close();
            return cList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean delete(int cID) {
        String delete = "DELETE FROM Consultant WHERE cID = ?";
        try {
            PreparedStatement ps = cn.prepareStatement(delete);
            ps.setInt(1, cID);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Consultant get(int cID) {
        Consultant c = null;
        String select = "SELECT * FROM Consultant WHERE cID = ?";
        try {
            PreparedStatement ps = cn.prepareStatement(select);
            ps.setInt(1, cID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String category = rs.getString("category");
                String hospital = rs.getString("hospital");
                String telephone = rs.getString("telephone");
                c = new Consultant(cID, firstName, lastName, category, hospital, telephone);
            }
            rs.close();
            ps.close();
            return c;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean update(Consultant c) {
        String update = "UPDATE Consultant SET firstName = ?, lastName = ?, category = ?, hospital = ?, telephone = ? WHERE cID = ?";
        try {
            PreparedStatement ps = cn.prepareStatement(update);
            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getCategory());
            ps.setString(4, c.getHospital());
            ps.setString(5, c.getTelephone());
            ps.setInt(6, c.getcID());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}