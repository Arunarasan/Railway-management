import java.sql.SQLException;
import java.util.Scanner;

import Controller.AddSchedules;
import Controller.Admin;
import Controller.GetEmployeeDetails;
import Controller.GetTrainDetails;
import Controller.SignUp;
import View.Display;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Scanner sc = new Scanner(System.in);
		int n;
		Display d = new Display();
		Admin ad = new Admin();
		SignUp sb = new SignUp(); 
		d.display();
		do {
		    n=sc.nextInt();
			if(n==1) {
				ad.admin();
			}else if(n==2) {
				sb.user();
			}else if(n==3) {
				sb.signUp();
			}
		}while(n!= -1);
		sc.close();
	}
	
}
