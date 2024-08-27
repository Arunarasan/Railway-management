package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Connection.ConnectDB;
import Models.Passenger;

public class PassengerDAO {

	 public int addPassenger(Passenger passenger) throws SQLException, ClassNotFoundException {
	        String sql = "INSERT INTO Passenger (passenger_name, age, gender) VALUES (?, ?, ?)";
	        int generatedId = -1; 

	        try (Connection conn = ConnectDB.getConnect();
	             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

	            stmt.setString(1, passenger.getPassengerName());
	            stmt.setInt(2, passenger.getAge());
	            stmt.setString(3, String.valueOf(passenger.getGender()));

	            int affectedRows = stmt.executeUpdate();

	            if (affectedRows > 0) {
	                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	                    if (generatedKeys.next()) {
	                        generatedId = generatedKeys.getInt(1); 
	                    }
	                }
	            }
	        }
	        return generatedId;
	    }

    public void updatePassenger(Passenger passenger) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Passenger SET passenger_name = ?, age = ?, gender = ? WHERE passenger_id = ?";
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setString(1, passenger.getPassengerName());
            stmt.setInt(2, passenger.getAge());
            stmt.setString(3, String.valueOf(passenger.getGender())); 
            stmt.setInt(4, passenger.getPassengerId());
            stmt.executeUpdate();
        }
    }

    public void deletePassenger(int passengerId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Passenger WHERE passenger_id = ?";
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, passengerId);
            stmt.executeUpdate();
        }
    }

    public Passenger getPassengerById(int passengerId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Passenger WHERE passenger_id = ?";
        
        Passenger passenger = null;
        try (Connection conn = ConnectDB.getConnect();
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, passengerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    passenger = new Passenger(
                        rs.getInt("passenger_id"),
                        rs.getString("passenger_name"),
                        rs.getInt("age"),
                        rs.getString("gender")  
                    );
                }
            }
        }
        return passenger;
    }
}
