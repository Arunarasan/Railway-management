package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Connection.ConnectDB;
import Models.PwD;

public class PwDDao {

    public void addPwD(PwD pwd) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO PwD (pwd_no, pwd_certificate, name, gender) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
             
            stmt.setString(1, pwd.getPwdNo());
            stmt.setString(2, pwd.getPwdCertificate());
            stmt.setString(3, pwd.getName());
            stmt.setString(4, String.valueOf(pwd.getGender()));
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding PwD: " + e.getMessage());
        }
    }
    
    public PwD getPwDById(int pwdId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM PwD WHERE pwd_id = ?";
        PwD pwd = null;
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            
            stmt.setInt(1, pwdId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pwd = new PwD(
                        rs.getInt("pwd_id"),
                        rs.getString("pwd_no"),
                        rs.getString("pwd_certificate"),
                        rs.getString("name"),
                        rs.getString("gender").charAt(0)
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving PwD: " + e.getMessage());
        }
        
        return pwd;
    }
    
    public List<PwD> getAllPwDs() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM PwD";
        List<PwD> pwds = new ArrayList<>();

        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PwD pwd = new PwD(
                    rs.getInt("pwd_id"),
                    rs.getString("pwd_no"),
                    rs.getString("pwd_certificate"),
                    rs.getString("name"),
                    rs.getString("gender").charAt(0)
                );
                pwds.add(pwd);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving PwDs: " + e.getMessage());
        }
        
        return pwds;
    }
    
    public void updatePwD(PwD pwd) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE PwD SET pwd_no = ?, pwd_certificate = ?, name = ?, gender = ? WHERE pwd_id = ?";
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            
            stmt.setString(1, pwd.getPwdNo());
            stmt.setString(2, pwd.getPwdCertificate());
            stmt.setString(3, pwd.getName());
            stmt.setString(4, String.valueOf(pwd.getGender()));
            stmt.setInt(5, pwd.getPwdId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating PwD: " + e.getMessage());
        }
    }
    
    public void deletePwD(int pwdId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM PwD WHERE pwd_id = ?";
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, pwdId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting PwD: " + e.getMessage());
        }
    }
}
