package Controller;

import java.sql.SQLException;
import java.util.Scanner;

import DAO.UserDAO;
import Models.User;
import View.Display;

public class Admin {
		public void admin() throws ClassNotFoundException, SQLException {
			Display d = new Display();
			UserDAO ud = new UserDAO();
			GetTrainDetails gt = new GetTrainDetails();
			GetEmployeeDetails emp = new GetEmployeeDetails();
			AddSchedules ad = new AddSchedules();
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter the username:");
			String uname = sc.next(); 
			System.out.println("Enter the password:");
			String pword = sc.next();
			if(uname.equalsIgnoreCase("abcd123") &&  pword.equals("a@123")) {
				
				User admin = ud.getUserById(10001);
				int t=-1;
					do {
						d.admin();
						t=sc.nextInt();
						switch(t) {
						case 1:gt.showTrains(); break;
						case 2:gt.showTrainByID(admin);break;
						case 3:gt.availableTrain(admin);break;
						case 4:gt.getScedule(); break;
						case 5:gt.Stationdisplay();break;
						case 6:emp.getEmployee(); break;
						case 7:ad.addSchedules(); break;
						case 8:ad.addTrain();break;
						default:
						}	
			}while(t!=-1);
				}else {
				System.out.println("Incorrect Password");
			}
		}
}
