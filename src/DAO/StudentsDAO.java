package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Connection.ConnectDB;
import Models.Student;

public class StudentsDAO {

    public void addStudent(Student student) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Student (student_id, student_name, student_qualification, student_proof, gender) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
             
            stmt.setInt(1, student.getStudentId());
            stmt.setString(2, student.getStudentName());
            stmt.setString(3, student.getStudentQualification());
            stmt.setString(4, student.getStudentProof());
            stmt.setString(5, String.valueOf(student.getGender()));
            
            stmt.executeUpdate();
        }
    }
    
    public Student getStudentById(int studentId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Student WHERE student_id = ?";
        Student student = null;
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            
            stmt.setInt(1, studentId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    student = new Student(
                        rs.getInt("student_id"),
                        rs.getString("student_name"),
                        rs.getString("student_qualification"),
                        rs.getString("student_proof"),
                        rs.getString("gender").charAt(0)
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving student: " + e.getMessage());
        }
        
        return student;
    }
    
    public Student[] getAllStudents() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Student";
        List<Student> students = new ArrayList<>();

        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Student student = new Student(
                    rs.getInt("student_id"),
                    rs.getString("student_name"),
                    rs.getString("student_qualification"),
                    rs.getString("student_proof"),
                    rs.getString("gender").charAt(0)
                );
                students.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving students: " + e.getMessage());
        }
        Student[] st = new Student[students.size()];
        st = students.toArray(st);
        return st;
    }
    
    public void updateStudent(Student student) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Student SET student_name = ?, student_qualification = ?, student_proof = ?, gender = ? WHERE student_id = ?";
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            
            stmt.setString(1, student.getStudentName());
            stmt.setString(2, student.getStudentQualification());
            stmt.setString(3, student.getStudentProof());
            stmt.setString(4, String.valueOf(student.getGender()));
            stmt.setInt(5, student.getStudentId());
            
            stmt.executeUpdate();
        }
    }
    
    public void deleteStudent(int studentId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Student WHERE student_id = ?";
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, studentId);
            stmt.executeUpdate();
        }
    }
}
