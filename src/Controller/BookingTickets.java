package Controller;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

import DAO.BookingDAO;
import DAO.PassengerDAO;
import DAO.PaymentDAO;
import DAO.TrainDAO;
import Models.Booking;
import Models.Cancel;
import Models.Compartment;
import Models.Passenger;
import Models.Payment;
import Models.Schedule;
import Models.Seat;
import Models.Sleeper;
import Models.SleeperInCabin;
import Models.User;

public class BookingTickets {
	Scanner sc = new Scanner(System.in);
	BookingDAO b = new BookingDAO();
	PassengerDAO p = new PassengerDAO();
	PaymentDAO pd = new PaymentDAO();
	TrainDAO t = new TrainDAO();
	public  void addBooking(Compartment c,Schedule[] sch,double fare ,User u,Date date) throws ClassNotFoundException, SQLException {
			String arr[] = new String[10];
			int i=0;
			System.out.println("Select the seat no or exit -1");
			while(sc.hasNext() && i<10) {
			     arr[i] = sc.next();
			     if(arr[i].equals("-1")) {
			    	 System.out.println("Thank you");
			    	 break;
			     }
			     if(!b.isCountOne(c.getCompartmentId(), arr[i])) {
			    	 System.out.println("Sorry seat does not available");
			    	 break;
			     }
			     if(!isSeatAvail(sch,arr[i])) {
			    	 System.out.println("Sorry It's already Booked");
			     }else {
			    	 Seat seat= t.getSeatByCompartmentId(c.getCompartmentId(), arr[i]);
			    	 Sleeper sleeper = t.getSleeperByCompartmentId(c.getCompartmentId(), arr[i]);
			    	 SleeperInCabin sleeperin = t.getSleeperInByCompartmentId(c.getCompartmentId(), arr[i]);
			    	 sc.nextLine();
			    	 System.out.println("Enter the Passenger Name:");
			    	 String name=sc.nextLine();
			    	 System.out.println("Enter the Age:");
			    	 int age = sc.nextInt();
			    	 System.out.println("Enter the gender");
			    	 String gender = sc.next();
			    	 System.out.println("If you are student or govt employee or pwd(1 or 0)");
			    	 if(sc.nextInt() == 1) {
			    		 System.out.println("Enter the proof no:");
			    		 int pr = sc.nextInt();
			    		 if(checkoff(pr)) {
			    			 fare -=(fare/2);
			    		 }else {
			    			 System.out.println("It's not match");
			    		 }
			    	 }
			    	 
			    	 if(!isGenderCheck(sch,seat,sleeper,sleeperin,gender,u)) {
			    		 System.out.println("You are unable to book the seat");
			    		 break;
			    	 }
			    	 double paid ;
			    	 do {
			    	 System.out.println("Pay the Amount:"+fare);
			    	  paid = sc.nextDouble();
			    	 if(paid == fare) {
					    	int pid = p.addPassenger(new Passenger(0,name,age,gender));
					    	int bid  = b.addBooking(new Booking(0,pnrNoGenerated(),p.getPassengerById(pid),u,seat,sleeper,sleeperin,date,fare));
					    	pd.addPayment(new Payment(0,bid,paid,new Timestamp(System.currentTimeMillis())));
					    	 
					    	for(Schedule s : sch) {
					    		b.addFullJourney(b.getBookingById(bid),s,date);
					    	}
					    	ShowTicket(b.getBookingById(bid),sch);
					    	break;
			    	 }
			    	 else {
			    		 System.out.println("First pay the amount:");
			    	 }
			     }while(paid != fare);	     
			     System.out.println("Select the seat no or exit -1");
			     i++;
			}
	}
	}		
	private boolean checkoff(int pr) throws ClassNotFoundException, SQLException {
		
		return b.countEmployeeByNo(pr)==1 || b.countGovtServantsById(pr)==1 || b.countPwdByNo(pr)==1 || b.countStudentById(pr)==1;
	}
	private boolean isGenderCheck(Schedule[] schedule,Seat seat, Sleeper sleeper, SleeperInCabin sleeperin, String gender,User u) throws ClassNotFoundException, SQLException {
		boolean check = true;
		if(seat != null) {
			switch(seat.getPosColumn()%3) {
			case 1:
			String st=isMaleorFemale(schedule,t.getSeat(seat.getCompartment().getCompartmentId(),seat.getPosRow(),seat.getPosColumn()+1).getSeatNo());
			check = isCheckUser(schedule,t.getSeat(seat.getCompartment().getCompartmentId(),seat.getPosRow(),seat.getPosColumn()+1).getSeatNo(),u);
			if(st.length() == 0 || st.toLowerCase().charAt(0) == gender.toLowerCase().charAt(0) || check) {
				return true;
			}break;
			case 2:
				String sp1=isMaleorFemale(schedule,t.getSeat(seat.getCompartment().getCompartmentId(),seat.getPosRow(),seat.getPosColumn()-1).getSeatNo());
				String sp2=isMaleorFemale(schedule,t.getSeat(seat.getCompartment().getCompartmentId(),seat.getPosRow(),seat.getPosColumn()+1).getSeatNo());
				boolean check1 = isCheckUser(schedule,t.getSeat(seat.getCompartment().getCompartmentId(),seat.getPosRow(),seat.getPosColumn()-1).getSeatNo(),u);
				boolean check2= isCheckUser(schedule,t.getSeat(seat.getCompartment().getCompartmentId(),seat.getPosRow(),seat.getPosColumn()+1).getSeatNo(),u);
				if(sp1.length() == 0 ||sp2.length() == 0 ||  sp1.toLowerCase().charAt(0) == gender.toLowerCase().charAt(0) && sp1.toLowerCase().charAt(0) == gender.toLowerCase().charAt(0) || (check1 && check2)) {
					return true;
				}break;
			case 0:
				String s1=isMaleorFemale(schedule,t.getSeat(seat.getCompartment().getCompartmentId(),seat.getPosRow(),seat.getPosColumn()-1).getSeatNo());
				check = isCheckUser(schedule,t.getSeat(seat.getCompartment().getCompartmentId(),seat.getPosRow(),seat.getPosColumn()-1).getSeatNo(),u);
				if(s1.length() == 0 || s1.toLowerCase().charAt(0) == gender.toLowerCase().charAt(0)|| check) {
					return true;
				}break;
			}
		}else if(sleeper != null) {
			Sleeper[] sl = t.getSleepersByCompartmentIdAndRow(sleeper.getCompartment().getCompartmentId(), sleeper.getSleeperRow());
			boolean avail = true;
			for(Sleeper s : sl) {
				String s1=isMaleorFemale(schedule,s.getSleeperNo());
				check = isCheckUser(schedule,s.getSleeperNo(),u);
				avail = avail&((s1.length() == 0 || s1.toLowerCase().charAt(0) == gender.toLowerCase().charAt(0))||check);
			}
			return avail;
		}else {
			SleeperInCabin[] slc = t.getAllSleeperInCabinsBycabin(sleeperin.getCabin().getCabinId());
			boolean avail = true;
			for(SleeperInCabin s : slc) {
				String s1=isMaleorFemale(schedule,s.getSleeperNo());
				check = isCheckUser(schedule,s.getSleeperNo(),u);
				avail = avail&(s1.length() == 0 || s1.toLowerCase().charAt(0) == gender.toLowerCase().charAt(0)||check);
			}
			return avail;
		} 
		return false;
	}
	public boolean isSeatAvail( Schedule[] sch,String seatno) throws ClassNotFoundException {
		boolean avail = true;
		for(Schedule s:sch) {
			avail = avail & b.checkSeatAvailability(seatno,s);
		}
		return avail;
	}
	public boolean isCheckUser( Schedule[] sch,String seatno,User u) throws ClassNotFoundException {
		boolean avail = true;
		for(Schedule s:sch) {
			avail = avail & (b.checkSeatUser(seatno,s)==null || b.checkSeatUser(seatno,s) == u.getUserId() );
		}
		return avail;
	}
	
	private int pnrNoGenerated() throws ClassNotFoundException, SQLException {
		return b.getLastBookingId()+10000;
	}
	private void ShowTicket(Booking book,Schedule[] sch) {
		System.out.println("**************************************************");
		System.out.println("PNR No:"+book.getPnrNo());
		System.out.println("Passenger Name:"+book.getPassenger().getPassengerName());
		System.out.println("Gender:"+book.getPassenger().getGender());
		System.out.println("Date:"+book.getBookingDate());
		System.out.println("Fare :"+book.getFare());
		if(book.getSeat()!=null) {
			System.out.println("Seat No:"+book.getSeat().getSeatNo());
			System.out.println("Compartment No:"+book.getSeat().getCompartment().getCompartmentNo());
			System.out.println("Train No:"+book.getSeat().getCompartment().getTrain().getTrainNumber());
		}else if(book.getSleeper()!=null) {
			System.out.println("Sleeper No:"+book.getSleeper().getSleeperNo());
			System.out.println("Compartment No:"+book.getSleeper().getCompartment().getCompartmentNo());
			System.out.println("Train No:"+book.getSleeper().getCompartment().getTrain().getTrainNumber());
		}else {
			System.out.println("Sleeper No:"+book.getSleeperin().getSleeperNo());
			System.out.println("Compartment No:"+book.getSleeperin().getCabin().getCompartment().getCompartmentNo()+" "+book.getSleeperin().getCabin().getCompartment().getCompartmentType());
			System.out.println("Train No:"+book.getSleeperin().getCabin().getCompartment().getTrain().getTrainNumber());
		}
		System.out.println("Source and Destination: "+sch[0].getTrack().getPlatform().getStation().getCity()+"-"+sch[sch.length-1].getTrack().getPlatform().getStation().getCity());
		System.out.println("Pick up Date and Time :"+sch[0].getArrivalDate()+" "+sch[0].getArrivalTime());
		System.out.println("Drop Date and Time :"+sch[sch.length-1].getArrivalDate()+" "+sch[sch.length-1].getArrivalTime());
		System.out.println("***************************************************");
	}
	
	public void cancelTicket(User u) throws ClassNotFoundException, SQLException {
		System.out.println("Enter the PNR No:");
		int pnr = sc.nextInt();
		
		LocalDateTime paidDate = pd.getPaymentById(b.getBookingByNo(pnr).getBookingId()).getPaidDate().toLocalDateTime();  
		LocalDateTime currentDate = LocalDateTime.now();

	    Duration duration = Duration.between(paidDate, currentDate);
	    long hours = duration.toHours();
	    double rfundamt = (pd.getPaymentById(b.getBookingByNo(pnr).getBookingId()).getPayAmt()/100)*hours;
		System.out.println("If you want cancel the Ticket  (YES 1/ NO 0) and the refund amount is "+rfundamt);
		
		
		if(sc.nextInt() == 1 && u.getUserId() == b.getBookingByNo(pnr).getUser().getUserId()) {
			sc.nextLine();
			System.out.println("Why you cancel the Ticket");
			String reason = sc.nextLine();
			 System.out.println("Your Amount Refund within 2 days");
			 b.addCancel(new Cancel(0,b.getBookingByNo(pnr),Timestamp.valueOf(currentDate),reason,rfundamt));
			 b.deleteFullJourneyByBookingId(b.getBookingByNo(pnr).getBookingId());
		}else {
			System.out.println("See to the next Time");
		}
	}
	 public String isMaleorFemale(Schedule[] schedule, String sleeperNo) throws ClassNotFoundException {
	    	String gender = "";
	    	for(Schedule sc : schedule) {
	    	gender += BookingDAO.checkMaleOrFemale( sleeperNo,sc);}
	    	return gender;
		}
	
	
	
}
