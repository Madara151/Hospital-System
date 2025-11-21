/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import business.Booking;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author User
 */
public class BookingDB {
    private Connection cn;

    public BookingDB() {
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

    // Add a new booking
    public boolean add(Booking booking) {
        String insert = "INSERT INTO Booking (bookingID, patientID, consultantID, bookingDate, time, room, paid) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = cn.prepareStatement(insert);
            ps.setInt(1, booking.getBookingID());
            ps.setInt(2, booking.getPatientID());
            ps.setInt(3, booking.getConsultantID());
            ps.setDate(4, booking.getBookingDate());
            ps.setString(5, booking.getTime());
            ps.setInt(6, booking.getRoom());
            ps.setString(7, booking.getPaid());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Get all bookings
    public ArrayList<Booking> getAll() {
        ArrayList<Booking> bookingList = new ArrayList<>();
        String select = "SELECT * FROM Booking";
        try {
            PreparedStatement ps = cn.prepareStatement(select);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int bookingID = rs.getInt("bookingID");
                int patientID = rs.getInt("patientID");
                int consultantID = rs.getInt("consultantID");
                Date bookingDate = rs.getDate("bookingDate");
                String time = rs.getString("time");
                int room = rs.getInt("room");
                String paid = rs.getString("paid");
                Booking booking = new Booking(bookingID, patientID, consultantID, bookingDate, time, room, paid);
                bookingList.add(booking);
            }
            return bookingList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Delete a booking
    public boolean delete(int bookingID) {
        String delete = "DELETE FROM Booking WHERE bookingID=?";
        try {
            PreparedStatement ps = cn.prepareStatement(delete);
            ps.setInt(1, bookingID);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Update a booking
    public boolean update(Booking booking) {
        String update = "UPDATE Booking SET patientID=?, consultantID=?, bookingDate=?, time=?, room=?, paid=? WHERE bookingID=?";
        try {
            PreparedStatement ps = cn.prepareStatement(update);
            ps.setInt(1, booking.getPatientID());
            ps.setInt(2, booking.getConsultantID());
            ps.setDate(3, booking.getBookingDate());
            ps.setString(4, booking.getTime());
            ps.setInt(5, booking.getRoom());
            ps.setString(6, booking.getPaid());
            ps.setInt(7, booking.getBookingID());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Get a booking by ID
    public Booking get(int bookingID) {
        Booking booking = null;
        String select = "SELECT * FROM Booking WHERE bookingID=?";
        try {
            PreparedStatement ps = cn.prepareStatement(select);
            ps.setInt(1, bookingID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int patientID = rs.getInt("patientID");
                int consultantID = rs.getInt("consultantID");
                Date bookingDate = rs.getDate("bookingDate");
                String time = rs.getString("time");
                int room = rs.getInt("room");
                String paid = rs.getString("paid");
                booking = new Booking(bookingID, patientID, consultantID, bookingDate, time, room, paid);
            }
            rs.close();
            ps.close();
            return booking;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
