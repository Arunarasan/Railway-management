package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Connection.ConnectDB;
import Models.GovtServants;

public class GovtServantsDAO {

    public void addGovtServants(GovtServants govtServants) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO GovtServants (govt_id, govt_role, name, gender) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setString(1, govtServants.getGovtId());
            stmt.setString(2, govtServants.getGovtRole());
            stmt.setString(3, govtServants.getName());
            stmt.setString(4, String.valueOf(govtServants.getGender()));
            stmt.executeUpdate();
        }
    }

    public GovtServants getGovtServantsById(String govtId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM GovtServants WHERE govt_id = ?";
        GovtServants govtServants = null;
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setString(1, govtId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    govtServants = new GovtServants(
                        rs.getInt("servant_id"),	
                        rs.getString("govt_id"),
                        rs.getString("govt_role"),
                        rs.getString("name"),
                        rs.getString("gender").charAt(0)
                    );
                }
            }
        }
        return govtServants;
    }

    public GovtServants[] getAllGovtServants() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM GovtServants";
        List<GovtServants> govtServantsList = new ArrayList<>();
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                GovtServants govtServants = new GovtServants(
                    rs.getInt("servant_id"),	
                    rs.getString("govt_id"),
                    rs.getString("govt_role"),
                    rs.getString("name"),
                    rs.getString("gender").charAt(0)
                );
                govtServantsList.add(govtServants);
            }
        }
        GovtServants[] gs = new GovtServants[ govtServantsList.size()];
        gs =  govtServantsList.toArray(gs);
        return gs;
    }

    public void updateGovtServants(GovtServants govtServants) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE GovtServants SET govt_role = ?, name = ?, gender = ? WHERE govt_id = ?";
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setString(1, govtServants.getGovtRole());
            stmt.setString(2, govtServants.getName());
            stmt.setString(3, String.valueOf(govtServants.getGender()));
            stmt.setString(4, govtServants.getGovtId());
            stmt.executeUpdate();
        }
    }

    public void deleteGovtServants(String govtId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM GovtServants WHERE govt_id = ?";
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setString(1, govtId);
            stmt.executeUpdate();
        }
    }
}
