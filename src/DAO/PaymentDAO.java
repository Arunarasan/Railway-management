package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import Connection.ConnectDB;
import Models.Payment;

public class PaymentDAO {
    
   

    // Method to add a new payment
    public boolean addPayment(Payment payment) throws ClassNotFoundException {
        String sql = "INSERT INTO Payment (payment_id, booking_id, pay_amt, paid_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = ConnectDB.getConnect().prepareStatement(sql)) {
            stmt.setInt(1, payment.getPaymentId());
            stmt.setInt(2, payment.getBookingId());
            stmt.setDouble(3, payment.getPayAmt());
            stmt.setTimestamp(4, payment.getPaidDate());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to retrieve a payment by ID
    public Payment getPaymentById(int paymentId) throws ClassNotFoundException {
        String sql = "SELECT * FROM Payment WHERE booking_id = ?";
        try (PreparedStatement stmt = ConnectDB.getConnect().prepareStatement(sql)) {
            stmt.setInt(1, paymentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Payment(
                    rs.getInt("payment_id"),
                    rs.getInt("booking_id"),
                    rs.getDouble("pay_amt"),
                    rs.getTimestamp("paid_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to retrieve all payments
    public List<Payment> getAllPayments() throws ClassNotFoundException {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM Payment";
        try (PreparedStatement stmt = ConnectDB.getConnect().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                payments.add(new Payment(
                    rs.getInt("payment_id"),
                    rs.getInt("booking_id"),
                    rs.getDouble("pay_amt"),
                    rs.getTimestamp("paid_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    // Method to update a payment
    public boolean updatePayment(Payment payment) throws ClassNotFoundException {
        String sql = "UPDATE Payment SET booking_id = ?, pay_amt = ?, paid_date = ? WHERE payment_id = ?";
        try (PreparedStatement stmt = ConnectDB.getConnect().prepareStatement(sql)) {
            stmt.setInt(1, payment.getBookingId());
            stmt.setDouble(2, payment.getPayAmt());
            stmt.setTimestamp(3, payment.getPaidDate());
            stmt.setInt(4, payment.getPaymentId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete a payment
    public boolean deletePayment(int paymentId) throws ClassNotFoundException {
        String sql = "DELETE FROM Payment WHERE payment_id = ?";
        try (PreparedStatement stmt = ConnectDB.getConnect().prepareStatement(sql)) {
            stmt.setInt(1, paymentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
