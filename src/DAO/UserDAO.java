package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Connection.ConnectDB;
import Models.User;

public class UserDAO {

    public void addUser(User user) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO User (first_name, last_name, email_id, phone_no, adhaar_no, password, gender, language, dob) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
             
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmailId());
            stmt.setString(4, user.getPhoneNo());
            stmt.setString(5, user.getAdhaarNo());
            stmt.setString(6, user.getPassword());
            stmt.setString(7, String.valueOf(user.getGender())); 
            stmt.setString(8, user.getLanguage());
            stmt.setDate(9, new java.sql.Date(user.getDob().getTime())); 
            
            stmt.executeUpdate();
        }
    }
    
    public User getUserById(int userId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM User WHERE user_id = ?";
        User user = null;
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                        rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email_id"),
                        rs.getString("phone_no"),
                        rs.getString("adhaar_no"),
                        rs.getString("password"),
                        rs.getString("gender").charAt(0), 
                        rs.getString("language"),
                        rs.getDate("dob")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving user: " + e.getMessage());
        }
        
        return user;
    }
    
    public User getUserByPwdAndEmail(String email,String pwd) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM User WHERE email_id = ? AND password = ?";
        User user = null;
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            
            stmt.setString(1, email);
            stmt.setString(2, pwd);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                        rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email_id"),
                        rs.getString("phone_no"),
                        rs.getString("adhaar_no"),
                        rs.getString("password"),
                        rs.getString("gender").charAt(0), 
                        rs.getString("language"),
                        rs.getDate("dob")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving user: " + e.getMessage());
        }
        
        return user;
    }
    
    public List<User> getAllUsers() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM User";
        List<User> users = new ArrayList<>();

        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                    rs.getInt("user_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email_id"),
                    rs.getString("phone_no"),
                    rs.getString("adhaar_no"),
                    rs.getString("password"),
                    rs.getString("gender").charAt(0),
                    rs.getString("language"),
                    rs.getDate("dob")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving users: " + e.getMessage());
        }
        
        return users;
    }
    
    public void updateUser(User user) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE User SET first_name = ?, last_name = ?, email_id = ?, phone_no = ?, adhaar_no = ?, password = ?, gender = ?, language = ?, dob = ? WHERE user_id = ?";
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmailId());
            stmt.setString(4, user.getPhoneNo());
            stmt.setString(5, user.getAdhaarNo());
            stmt.setString(6, user.getPassword());
            stmt.setString(7, String.valueOf(user.getGender()));
            stmt.setString(8, user.getLanguage());
            stmt.setDate(9, new java.sql.Date(user.getDob().getTime()));
            stmt.setInt(10, user.getUserId());
            
            stmt.executeUpdate();
        }
    }
    
    public void deleteUser(int userId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM User WHERE user_id = ?";
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }
}
