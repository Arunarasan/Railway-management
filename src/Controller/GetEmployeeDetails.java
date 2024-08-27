package Controller;

import java.sql.SQLException;

import DAO.*;
import Models.*;

public class GetEmployeeDetails {
		public void getEmployee() throws ClassNotFoundException, SQLException {
			EmployeeDAO t = new EmployeeDAO();
			Employee[] employee =    t.getAllEmployees();
			for(int i=0;i<employee.length;i++) {
				System.out.println(employee[i].toString());
			}
		}
		public void getEmployeeFamily(int eid) throws ClassNotFoundException, SQLException {
			EmployeeDAO t = new EmployeeDAO();
			EmployeeFamily[] employee =    t.getAllEmployeeFamilies(eid);
			for(int i=0;i<employee.length;i++) {
				System.out.println(employee[i].toString());
			}
		}
		
		public void getStudent() throws ClassNotFoundException, SQLException {
			StudentsDAO t = new StudentsDAO();
			Student[] employee =  t.getAllStudents()  ;
			for(int i=0;i<employee.length;i++) {
				System.out.println(employee[i].toString());
			}
		}
		public void getgovtser() throws ClassNotFoundException, SQLException {
			GovtServantsDAO t = new GovtServantsDAO();
			GovtServants[] employee =  t.getAllGovtServants();
			for(int i=0;i<employee.length;i++) {
				System.out.println(employee[i].toString());
			}
		}
}
