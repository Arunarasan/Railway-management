package View;

public class Display {

	 public void SignUp() {
		 System.out.println("                     WELCOME TO RAILWAYS");
		}
		public void display() {
			System.out.println("*********************************************************************");
			System.out.println("                        Railway Management");
			System.out.println("*********************************************************************");
			System.out.print("1.Admin            ");
			System.out.print("2.User             ");
			System.out.print("3.New User          ");
			System.out.println("-1.Exit");
			System.out.println("______________________________________________________________________");
		}
		
		public void admin() {
			System.out.println("1.list out the train details");
			System.out.println("2.List the particular train detials");
			System.out.println("3.Available trains");
			System.out.println("4.list out the Stations details");
			System.out.println("5.list out the User details");
			System.out.println("6.list out the Employee details");
			System.out.println("7.Add schedule");
			System.out.println("8.Add New Train ");
			
		}
		
		public void user() {
			System.out.println("1.list out the train details");
			System.out.println("2.Search Trains available for source and destination");
			System.out.println("3.Book the train");
			System.out.println("4.Cancel ticket");
		}
}
