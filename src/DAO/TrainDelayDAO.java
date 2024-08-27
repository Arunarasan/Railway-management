package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import Connection.ConnectDB;
import Models.*;
import Models.TrainDelay;

public class TrainDelayDAO {

    public void addTrainDelay(TrainDelay trainDelay) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO TrainDelay (train_id, station_id, delay_date, delay_duration, reason) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, trainDelay.getTrain().getTrainId()); 
            stmt.setInt(2, trainDelay.getStation().getStationId()); 
            stmt.setTimestamp(3, trainDelay.getDelayDate());
            stmt.setInt(4, trainDelay.getDelayDuration());
            stmt.setString(5, trainDelay.getReason());
            
            stmt.executeUpdate();
        }
    }
    public List<TrainDelay> getAllTrainDelays() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM TrainDelay";
        List<TrainDelay> trainDelays = new ArrayList<>();
        TrainDAO t = new TrainDAO();
        StationDAO s = new StationDAO();

        try (Connection conn = ConnectDB.getConnect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
            	int delayId = rs.getInt("delay_id");
                Train train = t.getTrainById(rs.getInt("train_id"));
                Station station = s.getStationById(rs.getInt("station_id"));
                Timestamp delayDate = rs.getTimestamp("delay_date");
                int delayDuration = rs.getInt("delay_duration");
                String reason = rs.getString("reason");
                
                TrainDelay trainDelay = new TrainDelay(delayId ,train, station, delayDate, delayDuration, reason);
                trainDelays.add(trainDelay);
            }
        }
        
        return trainDelays;
    }
}
