package Controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Scanner;

import DAO.*;
import Models.*;

public class GetTrainDetails {

    private TrainDAO trainDAO = new TrainDAO();
    private ScheduleDAO scheduleDAO = new ScheduleDAO();
   private StationDAO stationDAO = new StationDAO();
    Scanner sc = new Scanner(System.in);
    private BookingTickets BookingTicket=new BookingTickets(); 
    public void showTrains() throws ClassNotFoundException, SQLException {
        Train[] trains = trainDAO.getAllTrains();
        for (Train train : trains) {
            System.out.println(train);
        }
    }
    
    public void showTrainByID(User u) throws ClassNotFoundException, SQLException {
        System.out.println("Enter the Train No:");
        String trainNo = sc.next();
        sc.nextLine();
        LocalDate currentDate = LocalDate.now(); 
        Date date;
        do {
        	System.out.println("Enter the Date (yyyy-MM-dd):");
            String dateStr = sc.nextLine();
            date = java.sql.Date.valueOf(dateStr); 
            if (((java.sql.Date) date).toLocalDate().isAfter(currentDate)) {
                System.out.println("Date is in the future, proceeding...");
                break;
            } else {
                System.out.println("Date is not in the future. Please enter a future date.");
            }

        } while (true); 
        Train train = trainDAO.getTrainByNo(trainNo);
        if (train != null) {
            System.out.println(train);
            showTrainStructure(train.getTrainId(), u,date);
        } else {
            System.out.println("Train not found.");
        }
    }

    private void showTrainStructure(int trainId, User u,Date date) throws ClassNotFoundException, SQLException {
        Compartment[] compartments = trainDAO.getCompartments(trainId);

        System.out.print("ENG-");
        for (Compartment compartment : compartments) {
            System.out.print("[" + compartment.getCompartmentNo() + "," + compartment.getCompartmentType() + "]" + "-");
        }
        System.out.print("END");
        System.out.println();
        sc.nextLine();
        System.out.println("Enter the Source Station:");
        String source = sc.nextLine();
        System.out.println("Enter the Destination Station:");
        String destination = sc.nextLine();
        
        Schedule[] schedules = scheduleDAO.getScheduleByTrainByNo(trainId, date);
        int s=0,d=0,f=0;
        for (int i=0;i<schedules.length;i++) {
        	if(f==0 && (schedules[i].getTrack().getPlatform().getStation().getCity().equals(source))) {        		
        		s=i;
        		f=1;
        	}
        	if(f==1 && (schedules[i].getTrack().getPlatform().getStation().getCity().equals(destination))){ 
        		d=i;
        		f=2;
        	}
             System.out.println(schedules[i].getTrack().getPlatform().getStation().getCity() + " " + schedules[i].getArrivalTime() + " " + schedules[i].getArrivalDate());
        }
        
        Schedule schedule[] = new Schedule[d-s+1];
        double fare=0;
        for(int j=0,i=s;i<=d;j++,i++) {
        	schedule[j]=schedules[i];
        	fare+=schedule[j].getFare();
        }
        
        
        int choice;
        do {
            System.out.println("Enter the Compartment No:");
            String compartmentNo = sc.next();
            
            int index = searchCompartment(compartmentNo, compartments);
            if (index != -1) {
                System.out.println("Display the Compartment " + compartments[index].getCompartmentNo());
                Restroom restroom = trainDAO.getRestroomById(compartments[index].getCompartmentId());

                switch (compartments[index].getCompartmentType()) {
                    case "SS":
                        Seat[] seats = trainDAO.getSeatsInCompartment(compartments[index].getCompartmentId());
                        displayItems(seats, restroom, compartments[index].getCompartmentLength(), compartments[index].getCompartmentBreadth(), trainDAO.getPathsByCompartmentId(compartments[index].getCompartmentId()), true, false,schedule);
                        break;
                        
                    case "Sleeper":
                    case "2AC":
                    case "3AC":
                        Sleeper[] sleepers = trainDAO.getSleepersByCompartmentId(compartments[index].getCompartmentId());
                        displayItems(sleepers, restroom, compartments[index].getCompartmentLength(), compartments[index].getCompartmentBreadth(), trainDAO.getPathsByCompartmentId(compartments[index].getCompartmentId()), false, false,schedule);
                        break;
                        
                    case "1AC":
                        SleeperInCabin[] sleepersInCabins = trainDAO.getAllSleeperInCabins(compartments[index].getCompartmentId());
                        displayItems(sleepersInCabins, restroom, compartments[index].getCompartmentLength(), compartments[index].getCompartmentBreadth(), trainDAO.getPathsByCompartmentId(compartments[index].getCompartmentId()), false, true,schedule);
                        break;
                }
            } else {
                System.out.println("Compartment not available.");
            }
            System.out.println("1. Back Or 2. Book Seats Or -1. Exit ");
            choice = sc.nextInt();
            if (choice == 2) {
            	BookingTicket.addBooking(compartments[index], schedule, fare,u, date);
            }
        } while (choice == 1);
    }

    private void displayItems(Object[] items, Restroom restroom, int length, int breadth, Path path, boolean isSeats, boolean isCabin,Schedule[] sch) throws ClassNotFoundException {
        int k = 0, p = 5;
        boolean flag = false;
        String cabinNo = "";
        String book = "";
        for (int i = 1; i <= length; i++) {
            if (i == p) {
                flag = true;
                p += 3;
            }
            for (int j = 1; j <= breadth; j++) {
                if (i == 2 || i == 3 || i == length - 2 || i == length - 1) {
                    if (j == 1 || j == breadth) {
                        System.out.print("D            ");
                        continue;
                    } else {
                        System.out.print("            ");
                    }
                    continue;
                }
                if (j == 1) {
                    System.out.print("|");
                    continue;
                }
                if (i == 1 || i == length) {
                    System.out.print("------------");
                    continue;
                }
                
                if (i >= restroom.getToiletRow() && i < restroom.getToiletRow() + restroom.getBreadth()) {
                    if (i == restroom.getToiletRow() && j == 2 && isCabin) {
                        System.out.print("---------------------------------------------------------|                                                  |\n|");
                    }
                    if (j <= restroom.getLength() || (j > restroom.getLength() + path.getPathBreadth() + 1 && !isCabin)) {
                        System.out.print("   TOILET   ");
                    } else {
                        System.out.print("            ");
                    }
                    continue;
                }
                
                if (flag) {
                    System.out.print("            ");
                    continue;
                }
                if (j >= path.getPathRow() && j <= path.getPathRow() + path.getPathBreadth()) {
                    System.out.print("            ");
                    continue;
                }
                
                if (k >= items.length) {
                    break;
                } else {
                    if (isSeats) {
                        Seat seat = (Seat) items[k];
                        if(BookingTicket.isSeatAvail(sch,seat.getSeatNo())) {
                        	book = "Avl";
                        }else {
                        	book = "B*"+BookingTicket.isMaleorFemale(sch,seat.getSeatNo()).charAt(0);
                        }
                            System.out.print(" [" + seat.getSeatNo() + "," + seat.getSeatType() +","+book +"]");
                        
                    } else if (isCabin) {
                        SleeperInCabin sleeperInCabin = (SleeperInCabin) items[k];
                        if(BookingTicket.isSeatAvail(sch,sleeperInCabin.getSleeperNo())) {
                        	book = "Avl";
                        }else {
                        	book = "B*"+BookingTicket.isMaleorFemale(sch,sleeperInCabin.getSleeperNo()).charAt(0);
                        }
                        if (!cabinNo.equals(sleeperInCabin.getCabin().getCabinNo())) {
                            cabinNo = sleeperInCabin.getCabin().getCabinNo();
                            System.out.print("----------------------------------------------------------|                                                 |\n|");
                        }
                        j += sleeperInCabin.getCabin().getBreadth() / 2;
                        System.out.print(cabinNo + "  [ " + sleeperInCabin.getSleeperNo() + " ," + sleeperInCabin.getSleeperType() +" , "+book+ " ] ");
                        if (sleeperInCabin.getSleeperType().equals("Lower")) {
                            System.out.print("D                                     ");
                        }
                    } else {
                        Sleeper sleeper = (Sleeper) items[k];
                        if(BookingTicket.isSeatAvail(sch,sleeper.getSleeperNo())) {
                        	book = "Avl";
                        }else {
                        	book = "B*"+BookingTicket.isMaleorFemale(sch,sleeper.getSleeperNo()).charAt(0);
                        }
                        j += sleeper.getLength();
                        String type = sleeper.getSleeperType();
                        if (type.startsWith("Side")) {
                            if (sleeper.getCompartment().getCompartmentType().equals("2AC")) {
                                System.out.print(" ");
                            }
                            System.out.print(" [" + sleeper.getSleeperNo() + ", " + type+","+book + "]");
                        } else {
                            if (sleeper.getCompartment().getCompartmentType().equals("2AC")) {
                                System.out.print("   [   " + sleeper.getSleeperNo() + " , " + type+" , "+book + "  ]  ");
                            } else {
                                System.out.print("[" + sleeper.getSleeperNo() + " ," + type+" , "+book + " ]");
                            }
                        }
                    }
                    k++;
                }
            }
            flag = false;
            if (!(i == 2 || i == 3 || i == length - 2 || i == length - 1)) {
                System.out.println("|");
            } else {
                System.out.println();
            }
        }
    }

   

	private int searchCompartment(String compartmentNo, Compartment[] compartments) {
        for (int i = 0; i < compartments.length; i++) {
            if (compartmentNo.equalsIgnoreCase(compartments[i].getCompartmentNo())) {
                return i;
            }
        }
        return -1;
    }

    private void searchTrainByID(String source, String destination, Date date,User u) throws ClassNotFoundException, SQLException {
        int[] trainIds = scheduleDAO.getTrainIds(date, source, destination);
        for (int trainId : trainIds) {
            System.out.println(trainDAO.getTrainById(trainId));
        }
        if (trainIds.length == 0) {
            System.out.println("No trains matched.");
            availableTrain(u);
        } else {
            int trainId;
            do {
                System.out.println("Enter the Train Id:");
                trainId = sc.nextInt();
                showTrainStructure(trainId, u,date);
            } while (true);
        }
    }
    
    public void Stationdisplay() throws ClassNotFoundException, SQLException {
    	Station[] s = stationDAO.getAllStations(); 
    	for(Station st : s) {
    		System.out.println(st.toString());
    	}
    }
    
    public void availableTrain(User u) throws ClassNotFoundException, SQLException {
    	Stationdisplay();
        System.out.println("Enter the Source Station:");
        String source = sc.nextLine();
    	Stationdisplay();
        System.out.println("Enter the Destination Station:");
        String destination = sc.nextLine();
        LocalDate currentDate = LocalDate.now(); 
        Date date;
        do {
        	System.out.println("Enter the Date (yyyy-MM-dd):");
            String dateStr = sc.nextLine();
            date = java.sql.Date.valueOf(dateStr); 
            if (((java.sql.Date) date).toLocalDate().isAfter(currentDate)) {
                System.out.println("Date is in the future, proceeding...");
                break;
            } else {
                System.out.println("Date is not in the future. Please enter a future date.");
            }

        } while (true); 
        searchTrainByID(source, destination, date,u);
    }
    
    public void getScedule() throws ClassNotFoundException, SQLException {
    	Schedule[] sch = scheduleDAO.getAllSchedules();
    	for(Schedule s :sch) {
    		System.out.println(s.toString());
    	}
    }

    public void trackTrain() throws ClassNotFoundException, SQLException {
        System.out.println("Enter the Train No:");
        String trainNo = sc.next();
        Train train = trainDAO.getTrainByNo(trainNo);

        if (train != null) {
            System.out.println(train);
            Date cdate = new Date();
            LocalDate currentDate = LocalDate.now();
            LocalTime currentTime = LocalTime.now();

            Schedule[] schedules = scheduleDAO.getScheduleByTrainByNo(train.getTrainId(), cdate);

            for (int i = 0; i < schedules.length; i++) {
                Schedule schedule = schedules[i];
                String city = schedule.getTrack().getPlatform().getStation().getCity();
                java.sql.Date arrivalDate = (java.sql.Date) schedule.getArrivalDate();
                java.sql.Time arrivalTime = schedule.getArrivalTime();

                System.out.print(city + " " + arrivalTime + " " + arrivalDate);

                if (arrivalDate.toLocalDate().isBefore(currentDate) || 
                    (arrivalDate.toLocalDate().equals(currentDate) && 
                    arrivalTime.toLocalTime().isBefore(currentTime))) {
                    System.out.print(" *"); 
                }

                System.out.println();
            }
        } else {
            System.out.println("Train not found.");
        }
    }

}
