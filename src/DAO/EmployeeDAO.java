package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Connection.ConnectDB;
import Models.Employee;
import Models.EmployeeFamily;

public class EmployeeDAO {

    public void addEmployee(Employee employee) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Employee (employee_no, employee_name, employee_role) VALUES (?, ?, ?)";
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setString(1, employee.getEmployeeNo());
            stmt.setString(2, employee.getEmployeeName());
            stmt.setString(3, employee.getEmployeeRole());
            stmt.executeUpdate();
        }
    }

    public Employee getEmployeeById(String employeeNo) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Employee WHERE employee_no = ?";
        Employee employee = null;
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setString(1, employeeNo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    employee = new Employee(
                    	rs.getInt("employee_id"),
                        rs.getString("employee_no"),
                        rs.getString("employee_name"),
                        rs.getString("employee_role")
                    );
                }
            }
        }
        return employee;
    }
    public Employee getEmployeeByID(int employeeNo) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Employee WHERE employee_id = ?";
        Employee employee = null;
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, employeeNo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    employee = new Employee(
                    	rs.getInt("employee_id"),
                        rs.getString("employee_no"),
                        rs.getString("employee_name"),
                        rs.getString("employee_role")
                    );
                }
            }
        }
        return employee;
    }

    public Employee[] getAllEmployees() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Employee";
        List<Employee> employees = new ArrayList<>();
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Employee employee = new Employee(
                		rs.getInt("employee_id"),
                    rs.getString("employee_no"),
                    rs.getString("employee_name"),
                    rs.getString("employee_role")
                );
                employees.add(employee);
            }
        }
        Employee emp[] = new Employee[employees.size()];
        emp = employees.toArray(emp);
        return emp;
    }

    public void updateEmployee(Employee employee) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Employee SET employee_name = ?, employee_role = ? WHERE employee_no = ?";
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setString(1, employee.getEmployeeName());
            stmt.setString(2, employee.getEmployeeRole());
            stmt.setString(3, employee.getEmployeeNo());
            stmt.executeUpdate();
        }
    }

    public void deleteEmployee(String employeeNo) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Employee WHERE employee_no = ?";
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setString(1, employeeNo);
            stmt.executeUpdate();
        }
    }

    public void addEmployeeFamily(EmployeeFamily employeeFamily) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO EmployeeFamily (employee_id, relation, name, gender) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, employeeFamily.getEmployee().getEmployeeId());
            stmt.setString(2, employeeFamily.getRelation());
            stmt.setString(3, employeeFamily.getName());
            stmt.setString(4, String.valueOf(employeeFamily.getGender()));
            stmt.executeUpdate();
        }
    }

    public EmployeeFamily getEmployeeFamilyById(int employeeFamilyId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM EmployeeFamily WHERE employee_family_id = ?";
        EmployeeFamily employeeFamily = null;
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, employeeFamilyId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    employeeFamily = new EmployeeFamily(
                    	getEmployeeByID(rs.getInt("employee_id")),
                        rs.getString("relation"),
                        rs.getString("name"),
                        rs.getString("gender").charAt(0)
                    );
                }
            }
        }
        return employeeFamily;
    }

    public EmployeeFamily[] getAllEmployeeFamilies(int employeeId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM EmployeeFamily WHERE employee_id = ?";
        List<EmployeeFamily> employeeFamilies = new ArrayList<>();
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, employeeId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    EmployeeFamily employeeFamily = new EmployeeFamily(
                    	getEmployeeByID(rs.getInt("employee_id")),
                        rs.getString("relation"),
                        rs.getString("name"),
                        rs.getString("gender").charAt(0)
                    );
                    employeeFamilies.add(employeeFamily);
                }
            }
        }
        EmployeeFamily emp[] = new EmployeeFamily[employeeFamilies.size()];
        emp = employeeFamilies.toArray(emp);
        return emp;
    }


    public void updateEmployeeFamily(EmployeeFamily employeeFamily) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE EmployeeFamily SET relation = ?, name = ?, gender = ? WHERE employee_id = ?";
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setString(1, employeeFamily.getRelation());
            stmt.setString(2, employeeFamily.getName());
            stmt.setString(3, String.valueOf(employeeFamily.getGender()));
            stmt.setInt(4, employeeFamily.getEmployee().getEmployeeId());
            stmt.executeUpdate();
        }
    }

    public void deleteEmployeeFamily(EmployeeFamily employeeFamily) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM EmployeeFamily WHERE employee_id = ?";
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, employeeFamily.getEmployee().getEmployeeId());
            stmt.executeUpdate();
        }
    }
}
