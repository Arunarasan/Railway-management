package Controller;

import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

import DAO.BookingDAO;
import DAO.UserDAO;
import Models.User;
import View.Display;

public class SignUp {
		Display d = new Display();
	    private Scanner sc = new Scanner(System.in);
		private UserDAO ud = new UserDAO();
		GetTrainDetails gt = new GetTrainDetails();
		BookingTickets bd = new BookingTickets();
		GetEmployeeDetails emp = new GetEmployeeDetails();
		AddSchedules ad = new AddSchedules();
		public  void signUp() throws ClassNotFoundException, SQLException {
			System.out.println("Enter the First Name:");
			String fname = sc.next();
			System.out.println("Enter the Last Name:");
			String lname = sc.next();
			System.out.println("Enter the Email ID:");
			String email = sc.next();
			System.out.println("Enter the Phone No:");
			String pno = sc.next();
			System.out.println("Enter the Gneder(M/F):");
			char g = sc.next().charAt(0);
			sc.nextLine();
		    System.out.println("Enter the Date of Birth (yyyy-MM-dd):");
		    String dateStr = sc.nextLine();
		    Date date = java.sql.Date.valueOf(dateStr);
		    System.out.println("Enter the Prefered Language:");
			String lang = sc.next();
			sc.nextLine();
			System.out.println("Enter your Adhaar No:");
			String ano = sc.nextLine();
			System.out.println("Enter the password");
			String pwd = sc.next();
			System.out.println("Confirm your password");
			while(!pwd.equals(sc.next())) {
				System.out.println("Re-Enter your password");
			}
			
			ud.addUser(new User(0,fname,lname,email,pno,ano,pwd,g,lang,date));
			user();
		}
		
		public  void user() throws ClassNotFoundException, SQLException {
			System.out.println("Enter the Email ID:");
			String email = sc.next();
			System.out.println("Enter the password:");
			String pwd = sc.next();
			if(ud.getUserByPwdAndEmail(email, pwd)!= null) {
				int t=-1;
				do {
					d.user();
					t=sc.nextInt();
					switch(t) {
					case 1:gt.showTrainByID(ud.getUserByPwdAndEmail(email, pwd));break;
					case 2:gt.availableTrain(ud.getUserByPwdAndEmail(email, pwd));;break;
					case 3:gt.trackTrain();break;
					case 4:bd.cancelTicket(ud.getUserByPwdAndEmail(email, pwd));;break;
					default:
					}	
		         }while(t!=-1);
			}else {
				System.out.println("Incorrect Password");
			}
		}
		
}
