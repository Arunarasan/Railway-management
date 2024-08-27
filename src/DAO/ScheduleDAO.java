package DAO;

import Connection.ConnectDB;
import Models.Schedule;
import Models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleDAO {

	public void addSchedule(int trainId, int trackId, Date adate, Date ddate, Time arrivalTime, Time departTime, double fare) throws SQLException, ClassNotFoundException {
	    String sql = "INSERT INTO Schedule (train_id, track_id, arrival_time, depart_time, arrival_date, depart_date, fare) VALUES (?, ?, ?, ?, ?, ?, ?)";
	    try (Connection conn = ConnectDB.getConnect();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, trainId);
	        stmt.setInt(2, trackId);
	        stmt.setTime(3, arrivalTime);
	        stmt.setTime(4, departTime);
	        stmt.setDate(5, new java.sql.Date(adate.getTime()));
	        stmt.setDate(6, new java.sql.Date(ddate.getTime()));
	        stmt.setDouble(7, fare);  // Add fare parameter

	        stmt.executeUpdate();
	    }
	}



    public Schedule getScheduleById(int scheduleId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Schedule WHERE schedule_id = ?";
        Schedule schedule = null;
        TrainDAO t = new TrainDAO();
        StationDAO s = new StationDAO();
        try (Connection conn = ConnectDB.getConnect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, scheduleId);
            
            try (ResultSet rs = stmt.executeQuery()) {
            	
                if (rs.next()) {
                	Train train = t.getTrainById(rs.getInt("train_id"));
                	Track track = s.getTrackById(rs.getInt("track_id"));
                    schedule = new Schedule(
                        rs.getInt("schedule_id"),
                        train,
                        track,
                        rs.getTime("arrival_time"),
                        rs.getTime("depart_time"),
                        rs.getDate("arrival_date"),
                        rs.getDate("depart_date"),
                        rs.getDouble("fare")
                    );
                }
            }
        }
        return schedule;
    }

    public void updateSchedule(Schedule schedule) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Schedule SET train_id = ?, track_id = ?, arrival_time = ?, depart_time = ?, arrival_date = ?, depart_date = ?, fare = ? WHERE schedule_id = ?";
        try (Connection conn = ConnectDB.getConnect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, schedule.getTrain().getTrainId());
            stmt.setInt(2, schedule.getTrack().getTrackId());
            stmt.setTime(3, schedule.getArrivalTime());
            stmt.setTime(4, schedule.getDepartTime());
            stmt.setDate(5, (java.sql.Date) schedule.getArrivalDate());
            stmt.setDate(6, (java.sql.Date) schedule.getDepartDate());
            stmt.setDouble(7, schedule.getFare());
            stmt.setInt(8, schedule.getScheduleId());
            
            stmt.executeUpdate();
        }
    }

    public void deleteSchedule(int scheduleId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Schedule WHERE schedule_id = ?";
        try (Connection conn = ConnectDB.getConnect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, scheduleId);
            stmt.executeUpdate();
        }
    }

    public Schedule[] getAllSchedules() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Schedule Limit 100";
        TrainDAO t = new TrainDAO();
        StationDAO s = new StationDAO();
        List<Schedule> scheduleList = new ArrayList<>();
        
        try (Connection conn = ConnectDB.getConnect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Train train = t.getTrainById(rs.getInt("train_id"));
                Track track = s.getTrackById(rs.getInt("track_id"));
                Schedule schedule = new Schedule(
                    rs.getInt("schedule_id"),
                    train,
                    track,
                    rs.getTime("arrival_time"),
                    rs.getTime("depart_time"),
                    rs.getDate("arrival_date"),
                    rs.getDate("depart_date"),
                    rs.getDouble("fare")
                );
                scheduleList.add(schedule);
            }
        }
        
        Schedule[] scheduleArray = new Schedule[scheduleList.size()];
        scheduleArray = scheduleList.toArray(scheduleArray);
        
        return scheduleArray;
    }
    
    public boolean isTrainFree(int platformId, int trainId, java.util.Date adate, java.util.Date ddate, Time startTime, Time endTime) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM Schedule WHERE track_id = ? AND train_id = ? AND (arrival_date BETWEEN ? AND ? OR departure_date BETWEEN ? AND ?) AND (arrival_time < ? AND departure_time > ?)";
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, platformId);
            stmt.setInt(2, trainId);
            stmt.setDate(3, (java.sql.Date) adate);
            stmt.setDate(4, (java.sql.Date) ddate);
            stmt.setDate(5, (java.sql.Date) adate);
            stmt.setDate(6, (java.sql.Date) ddate);
            stmt.setTime(7, endTime);
            stmt.setTime(8, startTime);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count == 0; 
                }
            }
        }
        return false;
    }
    
   
    
    public Schedule[] getScheduleByTrainByNo(int trainNumber, java.util.Date dt) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Schedule " +
                     "WHERE train_id = ? AND arrival_date >= ? " +
                     "ORDER BY arrival_date, arrival_time LIMIT 20";
        
        TrainDAO t = new TrainDAO();
        StationDAO s = new StationDAO();
        ArrayList<Schedule> scheduleList = new ArrayList<>();
        
        try (Connection conn = ConnectDB.getConnect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, trainNumber);
            pstmt.setDate(2, new java.sql.Date(dt.getTime()));
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Train train = t.getTrainById(rs.getInt("train_id"));
                    Track track = s.getTrackById(rs.getInt("track_id"));
                    
                    Schedule schedule = new Schedule(
                        rs.getInt("schedule_id"),
                        train,
                        track,
                        rs.getTime("arrival_time"),
                        rs.getTime("depart_time"),
                        rs.getDate("arrival_date"),
                        rs.getDate("depart_date"),
                        rs.getDouble("fare")
                    );
                    
                    scheduleList.add(schedule);
                }
            }
        }
        
        Schedule[] scheduleArray = new Schedule[scheduleList.size()];
        return scheduleList.toArray(scheduleArray);
    }

  

        public int[] getTrainIds(java.util.Date date, String city1, String city2) throws SQLException, ClassNotFoundException {
            String sql = "SELECT s.train_id " +
                         "FROM Schedule s " +
                         "JOIN Track t ON s.track_id = t.track_id " +
                         "JOIN Platform p ON t.platform_id = p.platform_id " +
                         "JOIN Station st ON p.station_id = st.station_id " +
                         "WHERE st.city IN (?, ?) " +
                         "AND (s.arrival_date = ? OR s.depart_date = ?) " +
                         "GROUP BY s.train_id " +
                         "HAVING COUNT(DISTINCT st.city) = 2";
            
            List<Integer> trainIds = new ArrayList<>();
            
            try (Connection conn = ConnectDB.getConnect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setString(1, city1);
                pstmt.setString(2, city2);
                pstmt.setDate(3, new java.sql.Date(date.getTime()));
                pstmt.setDate(4, new java.sql.Date(date.getTime()));
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        trainIds.add(rs.getInt("train_id"));
                    }
                }
            }

            int[] trainIdsArray = trainIds.stream().mapToInt(i -> i).toArray();
         
            return trainIdsArray;
        }



		
    

}
