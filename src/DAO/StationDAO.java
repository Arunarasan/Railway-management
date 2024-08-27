package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Connection.ConnectDB;
import Models.*;

public class StationDAO {

    // Method to add a new station
    public void addStation(Station station) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Station (station_name, location, city, state) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = ConnectDB.getConnect().prepareStatement(sql)) {
            stmt.setString(1, station.getStationName());
            stmt.setString(2, station.getLocation());
            stmt.setString(3, station.getCity());
            stmt.setString(4, station.getState());
            stmt.executeUpdate();
        }
    }
    
    // Method to update an existing station
    public void updateStation(Station station) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Station SET station_name = ?, location = ?, city = ?, state = ? WHERE station_id = ?";
        
        try (PreparedStatement stmt = ConnectDB.getConnect().prepareStatement(sql)) {
            stmt.setString(1, station.getStationName());
            stmt.setString(2, station.getLocation());
            stmt.setString(3, station.getCity());
            stmt.setString(4, station.getState());
            stmt.setInt(5, station.getStationId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating station: " + e.getMessage());
        }
    }

    // Method to add a new platform
    public void addPlatform(Platform platform) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Platform (platform_number, station_id, platform_length, platform_type, is_active) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = ConnectDB.getConnect().prepareStatement(sql)) {
            stmt.setInt(1, platform.getPlatformNumber());
            stmt.setInt(2, platform.getStation().getStationId()); 
            stmt.setDouble(3, platform.getPlatformLength());
            stmt.setString(4, platform.getPlatformType());
            stmt.setBoolean(5, platform.isActive());
            stmt.executeUpdate();
        }
    }
    
    // Method to add a new track
    public void addTrack(Track track) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Track (platform_id) VALUES (?)";
        
        try (PreparedStatement stmt = ConnectDB.getConnect().prepareStatement(sql)) { 
            stmt.setInt(1, track.getPlatform().getPlatformId());
            stmt.executeUpdate();
        }
    }
    
    // Method to get a platform by its ID
    public Platform getPlatformById(int platformId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Platform WHERE platform_id = ?";
        Platform platform = null;

        try (PreparedStatement stmt = ConnectDB.getConnect().prepareStatement(sql)) {
            stmt.setInt(1, platformId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Station station = getStationById(rs.getInt("station_id"));
                    platform = new Platform(
                        rs.getInt("platform_id"),
                        rs.getInt("platform_number"),
                        station,
                        rs.getInt("platform_length"),
                        rs.getString("platform_type"),
                        rs.getBoolean("is_active"),
                        rs.getTimestamp("created_at")
                    );
                }
            }
        }
        return platform;
    }

    // Method to get a platform by station ID and platform number
    public Platform getPlatformByStationIdAndNumber(int stationId, int platformNumber) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Platform WHERE station_id = ? AND platform_number = ?";
        Platform platform = null;

        try (PreparedStatement stmt = ConnectDB.getConnect().prepareStatement(sql)) {
            stmt.setInt(1, stationId);
            stmt.setInt(2, platformNumber);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Station station = getStationById(rs.getInt("station_id"));
                    platform = new Platform(
                        rs.getInt("platform_id"),
                        rs.getInt("platform_number"),
                        station,
                        rs.getInt("platform_length"),
                        rs.getString("platform_type"),
                        rs.getBoolean("is_active"),
                        rs.getTimestamp("created_at")
                    );
                }
            }
        }
        return platform;
    }

    // Method to get all platforms by station ID
    public Platform[] getAllPlatformsByStationId(int stationId) throws SQLException, ClassNotFoundException {
        String countSql = "SELECT COUNT(*) FROM Platform WHERE station_id = ?";
        String sql = "SELECT * FROM Platform WHERE station_id = ?";
        Platform[] platforms = null;

        try (
            PreparedStatement countStmt = ConnectDB.getConnect().prepareStatement(countSql);
            PreparedStatement stmt = ConnectDB.getConnect().prepareStatement(sql)) {

            countStmt.setInt(1, stationId);
            try (ResultSet countRs = countStmt.executeQuery()) {
                if (countRs.next()) {
                    int platformCount = countRs.getInt(1);
                    platforms = new Platform[platformCount];
                }
            }

            stmt.setInt(1, stationId);
            try (ResultSet rs = stmt.executeQuery()) {
                Station station = getStationById(stationId);
                int index = 0;

                while (rs.next()) {
                    Platform platform = new Platform(
                        rs.getInt("platform_id"),
                        rs.getInt("platform_number"),
                        station,
                        rs.getInt("platform_length"),
                        rs.getString("platform_type"),
                        rs.getBoolean("is_active"),
                        rs.getTimestamp("created_at")
                    );
                    platforms[index++] = platform;
                }
            }
        }
        return platforms;
    }

    // Method to get a track by its ID
    public Track getTrackById(int trackId) throws SQLException, ClassNotFoundException {
        Track track = null;
        String query = "SELECT track_id, platform_id FROM Track WHERE track_id = ?";

        try (PreparedStatement preparedStatement = ConnectDB.getConnect().prepareStatement(query)) {
            preparedStatement.setInt(1, trackId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("track_id");
                    int platformId = resultSet.getInt("platform_id");
                    Platform platform = getPlatformById(platformId);
                    track = new Track(id, platform);
                }
            }
        }
        return track;
    }

    // Method to get all tracks by platform ID
    public Track[] getAllTracksByPlatformId(int platformId) throws SQLException, ClassNotFoundException {
        String query = "SELECT track_id, platform_id FROM Track WHERE platform_id = ?";
        ArrayList<Track> tracksList = new ArrayList<>();

        try (PreparedStatement preparedStatement = ConnectDB.getConnect().prepareStatement(query)) {
            preparedStatement.setInt(1, platformId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int trackId = resultSet.getInt("track_id");
                    Track track = getTrackById(trackId); 
                    tracksList.add(track);
                }
            }
        }

        return tracksList.toArray(new Track[0]);
    }

    // Method to get a station by its ID
    public Station getStationById(int stationId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Station WHERE station_id = ?";
        Station station = null;

        try (PreparedStatement stmt = ConnectDB.getConnect().prepareStatement(sql)) {
            stmt.setInt(1, stationId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    station = new Station(
                        rs.getInt("station_id"),
                        rs.getString("station_name"),
                        rs.getString("location"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getTimestamp("created_at")
                    );
                }
            }
        }
        return station;
    }

    // Method to get a station by its name
    public Station getStationByName(String stationName) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Station WHERE station_name = ?";
        Station station = null;

        try (PreparedStatement stmt = ConnectDB.getConnect().prepareStatement(sql)) {
            stmt.setString(1, stationName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    station = new Station(
                        rs.getInt("station_id"),
                        rs.getString("station_name"),
                        rs.getString("location"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getTimestamp("created_at")
                    );
                }
            }
        }
        return station;
    }

    // Method to get all stations
    public Station[] getAllStations() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Station";
        List<Station> stations = new ArrayList<>();

        try (PreparedStatement stmt = ConnectDB.getConnect().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Station station = new Station(
                    rs.getInt("station_id"),
                    rs.getString("station_name"),
                    rs.getString("location"),
                    rs.getString("city"),
                    rs.getString("state"),
                    rs.getTimestamp("created_at")
                );
                stations.add(station);
            }
        }
        return stations.toArray(new Station[0]);
    }
}
