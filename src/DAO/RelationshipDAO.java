package DAO;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Connection.ConnectDB;

public class RelationshipDAO {

   
    public boolean addPassengerVSStudent(int passengerId, int studentId) throws ClassNotFoundException {
        String sql = "INSERT INTO PassengerVSStudent (passenger_id, student_id) VALUES (?, ?)";
        try (PreparedStatement stmt = ConnectDB.getConnect().prepareStatement(sql)) {
            stmt.setInt(1, passengerId);
            stmt.setInt(2, studentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addPassengerVSPwd(int passengerId, int pwdId) throws ClassNotFoundException {
        String sql = "INSERT INTO PassengerVSPwd (passenger_id, pwd_id) VALUES (?, ?)";
        try (PreparedStatement stmt = ConnectDB.getConnect().prepareStatement(sql)) {
            stmt.setInt(1, passengerId);
            stmt.setInt(2, pwdId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addPassengerVSEmployee(int passengerId, int employeeId) throws ClassNotFoundException {
        String sql = "INSERT INTO PassengerVSEmployee (passenger_id, employee_id) VALUES (?, ?)";
		try (PreparedStatement stmt = ConnectDB.getConnect().prepareStatement(sql)) {
            stmt.setInt(1, passengerId);
            stmt.setInt(2, employeeId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addPassengerVSGovtServants(int passengerId, int servantId) throws ClassNotFoundException {
        String sql = "INSERT INTO PassengerVSGovtServants (passenger_id, servant_id) VALUES (?, ?)";
        try (PreparedStatement stmt = ConnectDB.getConnect().prepareStatement(sql)) {
            stmt.setInt(1, passengerId);
            stmt.setInt(2, servantId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
