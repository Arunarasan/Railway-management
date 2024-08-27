package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Connection.ConnectDB;
import Models.*;

public class BookingDAO {

	 public int addBooking(Booking booking) throws SQLException, ClassNotFoundException {
	        String sql = "INSERT INTO Booking (pnr_no, passenger_id, user_id, seat_id, sleeper_id, sleeperin_id ,booking_date,fare) VALUES (?,?, ?, ?, ?, ?, ? ,?)";
	        int generatedId = -1; 

	        try (Connection conn = ConnectDB.getConnect();
	             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

	            stmt.setInt(1, booking.getPnrNo());
	            stmt.setInt(2, booking.getPassenger().getPassengerId());
	            stmt.setInt(3, booking.getUser().getUserId());
	            stmt.setObject(4, (booking.getSeat()!= null)?booking.getSeat().getSeatId():null);
	            stmt.setObject(5, (booking.getSleeper()!= null)?booking.getSleeper().getSleeperId():null);
	            stmt.setObject(6, (booking.getSleeperin()!= null)?booking.getSleeperin().getSleeperId():null);
	            stmt.setDate(7, new java.sql.Date(booking.getBookingDate().getTime()));
	            stmt.setDouble(8, booking.getFare());
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

    public void updateBooking(Booking booking) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Booking SET pnr_no = ?, passenger_id = ?, user_id = ?, seat_id = ?, sleeper_id = ?,sleeperin_id = ?, booking_date = ?, fare = ? WHERE booking_id = ?";
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
             
            stmt.setInt(1, booking.getPnrNo());
            stmt.setInt(2, booking.getPassenger().getPassengerId());
            stmt.setInt(3, booking.getUser().getUserId());
            stmt.setInt(4, booking.getSeat().getSeatId());
            stmt.setInt(5, booking.getSleeper().getSleeperId());
            stmt.setInt(6, booking.getSleeperin().getSleeperId());
            stmt.setDate(7, new java.sql.Date(booking.getBookingDate().getTime())); 
            stmt.setDouble(8, booking.getFare());
            stmt.setInt(8, booking.getBookingId());
            
            stmt.executeUpdate();
        }
    }

    public void deleteBookingById(int bookingId) throws SQLException, ClassNotFoundException {
        try (Connection conn = ConnectDB.getConnect()) {
            conn.setAutoCommit(false);
            
            String deleteFullJourneySql = "DELETE FROM FullJourney WHERE booking_id = ?";
            try (PreparedStatement stmtFullJourney = conn.prepareStatement(deleteFullJourneySql)) {
                stmtFullJourney.setInt(1, bookingId);
                stmtFullJourney.executeUpdate();
            }
            
            String deleteBookingSql = "DELETE FROM Booking WHERE booking_id = ?";
            try (PreparedStatement stmtBooking = conn.prepareStatement(deleteBookingSql)) {
                stmtBooking.setInt(1, bookingId);
                int affectedRows = stmtBooking.executeUpdate();
                
                if (affectedRows > 0) {
                    System.out.println("Booking with ID " + bookingId + " was deleted successfully.");
                } else {
                    System.out.println("No booking found with ID " + bookingId + ".");
                }
            }
            
            conn.commit();
        } catch (SQLException e) {
        	ConnectDB.getConnect().rollback();
            throw e;
        }
    }
    public void deleteFullJourneyByBookingId(int bookingId) throws SQLException, ClassNotFoundException {
        try (Connection conn = ConnectDB.getConnect()) {
            conn.setAutoCommit(false);
            
            String deleteFullJourneySql = "DELETE FROM FullJourney WHERE booking_id = ?";
            try (PreparedStatement stmtFullJourney = conn.prepareStatement(deleteFullJourneySql)) {
                stmtFullJourney.setInt(1, bookingId);
                int affectedRows = stmtFullJourney.executeUpdate();
                
                if (affectedRows > 0) {
                    System.out.println("FullJourney entries with Booking ID " + bookingId + " were deleted successfully.");
                } else {
                    System.out.println("No FullJourney entries found with Booking ID " + bookingId + ".");
                }
            }
            
            conn.commit();
        } catch (SQLException e) {
            try (Connection conn = ConnectDB.getConnect()) {
                conn.rollback();
            }
            throw e;
        }
    }



    public Booking getBookingById(int bookingId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Booking WHERE booking_id = ?";
        Booking booking = null;
        PassengerDAO p = new PassengerDAO();
        UserDAO u = new UserDAO();
        TrainDAO t = new TrainDAO();
        ScheduleDAO s = new ScheduleDAO();
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, bookingId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                	 
                    booking = new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("pnr_no"),
                        p.getPassengerById(rs.getInt("passenger_id")),
                        u.getUserById(rs.getInt("user_id")),
                        t.getSeatById(rs.getInt("seat_id")),
                        t.getSleeperById(rs.getInt("sleeper_id")),
                        t.getSleeperInCabinById(rs.getInt("sleeperin_id")),
                        rs.getDate("booking_date"),
                        rs.getDouble("fare")
                    );
                }
            }
        }
        return booking;
    }
    
    public Booking getBookingByNo(int bookingId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Booking WHERE pnr_no = ?";
        Booking booking = null;
        PassengerDAO p = new PassengerDAO();
        UserDAO u = new UserDAO();
        TrainDAO t = new TrainDAO();
        ScheduleDAO s = new ScheduleDAO();
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, bookingId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                	 
                    booking = new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("pnr_no"),
                        p.getPassengerById(rs.getInt("passenger_id")),
                        u.getUserById(rs.getInt("user_id")),
                        t.getSeatById(rs.getInt("seat_id")),
                        t.getSleeperById(rs.getInt("sleeper_id")),
                        t.getSleeperInCabinById(rs.getInt("sleeperin_id")),
                        rs.getDate("booking_date"),
                        rs.getDouble("fare")
                    );
                }
            }
        }
        return booking;
    }
    public int getBookingCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) AS total FROM Booking";
        int count = 0;

        try (Connection conn = ConnectDB.getConnect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt("total");
            }
        }
        
        return count;
    }
    public int getLastBookingId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT MAX(booking_id) AS last_id FROM Booking";
        int lastId = -1;

        try (Connection conn = ConnectDB.getConnect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                lastId = rs.getInt("last_id");
            }
        }
        
        return lastId;
    }


    
     public boolean addCancel(Cancel cancel) throws ClassNotFoundException {
        String sql = "INSERT INTO Cancel (cancel_id, booking_id, cancel_date, cancel_reason, refund_amt) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = ConnectDB.getConnect().prepareStatement(sql)) {
            stmt.setInt(1, cancel.getCancelId());
            stmt.setInt(2, cancel.getBooking().getBookingId()); 
            stmt.setTimestamp(3, cancel.getCancelDate());
            stmt.setString(4, cancel.getCancelReason());
            stmt.setDouble(5, cancel.getRefundAmt());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Cancel getCancelById(int cancelId) throws ClassNotFoundException {
        String sql = "SELECT * FROM Cancel WHERE cancel_id = ?";
        try (PreparedStatement stmt = ConnectDB.getConnect().prepareStatement(sql)) {
            stmt.setInt(1, cancelId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Booking booking = getBookingById(rs.getInt("booking_id")); 
                return new Cancel(
                    rs.getInt("cancel_id"),
                    booking,
                    rs.getTimestamp("cancel_date"),
                    rs.getString("cancel_reason"),
                    rs.getDouble("refund_amt")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Cancel> getAllCancels() throws ClassNotFoundException {
        List<Cancel> cancels = new ArrayList<>();
        String sql = "SELECT * FROM Cancel";
        try (PreparedStatement stmt = ConnectDB.getConnect().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Booking booking = getBookingById(rs.getInt("booking_id"));
                cancels.add(new Cancel(
                		rs.getInt("cancel_id"),
                        booking,
                        rs.getTimestamp("cancel_date"),
                        rs.getString("cancel_reason"),
                        rs.getDouble("refund_amt")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cancels;
    }
    
    
    public boolean isCountOne(int cid,String seat) throws ClassNotFoundException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            String sql = "SELECT count(*) " +
                         "FROM compartment AS c " +
                         "LEFT JOIN seat AS s ON s.compartment_id = c.compartment_id " +
                         "LEFT JOIN sleeper AS sl ON sl.compartment_id = c.compartment_id " +
                         "LEFT JOIN cabin AS cb ON cb.compartment_id = c.compartment_id " +
                         "LEFT JOIN sleeperincabin AS sc ON sc.cabin_id = cb.cabin_id " +
                         "WHERE (s.compartment_id IS NOT NULL " +
                         "OR sl.compartment_id IS NOT NULL " +
                         "OR cb.compartment_id IS NOT NULL " +
                         "OR sc.cabin_id IS NOT NULL) " +
                         "AND c.compartment_id = ? " +
                         "AND (s.seat_no = ? " +
                         "OR sl.sleeper_no = ? " +
                         "OR sc.sleeper_no = ?)";

            pstmt = ConnectDB.getConnect().prepareStatement(sql);

            pstmt.setInt(1, cid);          
            pstmt.setString(2, seat);    
            pstmt.setString(3, seat);   
            pstmt.setString(4, seat);  

            rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);  
                return count == 1;         
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false; 
    }
    
    public void addFullJourney(Booking bk, Schedule scheduleId, java.util.Date bookingDate) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO FullJourney (booking_id, schedule_id, booking_date) VALUES (?, ?, ?)";

        try (Connection conn = ConnectDB.getConnect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bk.getBookingId());
            pstmt.setInt(2, scheduleId.getScheduleId());
            pstmt.setDate(3, new Date(bookingDate.getTime()));

            pstmt.executeUpdate();
        }
    }
    
    public static boolean checkSeatAvailability(String seatNo, Schedule scheduleId) throws ClassNotFoundException {
       

        String sql = "SELECT * FROM Booking b " +
                     "JOIN FullJourney fj ON b.booking_id = fj.booking_id " +
                     "LEFT JOIN Seat s ON b.seat_id = s.seat_id " +
                     "LEFT JOIN Sleeper sl ON b.sleeper_id = sl.sleeper_id " +
                     "LEFT JOIN SleeperinCabin sc ON b.sleeperin_id = sc.sleeper_id " +
                     "WHERE (s.seat_no = ? OR sl.sleeper_no = ? OR sc.sleeper_no = ?) " +
                     "AND fj.schedule_id = ?";

        try (
             PreparedStatement statement = ConnectDB.getConnect().prepareStatement(sql)) {

            statement.setString(1, seatNo);      
            statement.setString(2, seatNo);     
            statement.setString(3, seatNo);
            statement.setInt(4, scheduleId.getScheduleId());     

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
    public static Integer checkSeatUser(String seatNo, Schedule scheduleId) throws ClassNotFoundException {
        String sql = "SELECT b.user_id FROM Booking b " +
                     "JOIN FullJourney fj ON b.booking_id = fj.booking_id " +
                     "LEFT JOIN Seat s ON b.seat_id = s.seat_id " +
                     "LEFT JOIN Sleeper sl ON b.sleeper_id = sl.sleeper_id " +
                     "LEFT JOIN SleeperinCabin sc ON b.sleeperin_id = sc.sleeper_id " +
                     "WHERE (s.seat_no = ? OR sl.sleeper_no = ? OR sc.sleeper_no = ?) " +
                     "AND fj.schedule_id = ?";

        try (PreparedStatement statement = ConnectDB.getConnect().prepareStatement(sql)) {
            statement.setString(1, seatNo);      
            statement.setString(2, seatNo);      
            statement.setString(3, seatNo);
            statement.setInt(4, scheduleId.getScheduleId());     

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    
    public static  String checkMaleOrFemale(String seatNo, Schedule scheduleId) throws ClassNotFoundException {
        String sql = "SELECT b.*, fj.*, s.*, sl.*, sc.*, p.gender " +
                     "FROM Booking b " +
                     "JOIN FullJourney fj ON b.booking_id = fj.booking_id " +
                     "LEFT JOIN Seat s ON b.seat_id = s.seat_id " +
                     "LEFT JOIN Sleeper sl ON b.sleeper_id = sl.sleeper_id " +
                     "LEFT JOIN SleeperinCabin sc ON b.sleeperin_id = sc.sleeper_id " +
                     "LEFT JOIN Passenger p ON b.passenger_id = p.passenger_id " +
                     "WHERE (s.seat_no = ? OR sl.sleeper_no = ? OR sc.sleeper_no = ?) " +
                     "AND fj.schedule_id = ?";
        String gender="";

        try (
            PreparedStatement statement = ConnectDB.getConnect().prepareStatement(sql)) {

            statement.setString(1, seatNo);      
            statement.setString(2, seatNo);     
            statement.setString(3, seatNo);
            statement.setInt(4, scheduleId.getScheduleId());     

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
              gender = resultSet.getString("gender");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gender; 
    }
    public int countStudentById(int studentId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM student WHERE student_id = ?";
        return executeCountQuery(sql, studentId);
    }

    public int countPwdByNo(int pwdNo) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM pwd WHERE pwd_no = ?";
        return executeCountQuery(sql, pwdNo);
    }

    public int countGovtServantsById(int govtId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM govtservants WHERE govt_id = ?";
        return executeCountQuery(sql, govtId);
    }

    public int countEmployeeByNo(int employeeNo) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM employee WHERE employee_no = ?";
        return executeCountQuery(sql, employeeNo);
    }
    private int executeCountQuery(String sql, int id) throws SQLException, ClassNotFoundException {
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) { 
                if (rs.next()) {
                    return rs.getInt(1); 
                }
            }
        }
        return 0;
    }

}
