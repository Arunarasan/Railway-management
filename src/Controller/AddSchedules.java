package Controller;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.Scanner;

import DAO.*;
import Models.*;

public class AddSchedules {
    Scanner sc = new Scanner(System.in);
    TrainDAO t = new TrainDAO();
    StationDAO st = new StationDAO();
    ScheduleDAO sd = new ScheduleDAO();

    public void addSchedules() throws ClassNotFoundException, SQLException {
        System.out.println("Enter the Train No:");
        String trn_no = sc.next();
        Train train = t.getTrainByNo(trn_no);

        if (train == null) {
            System.out.println("Train not found.");
            return;
        }

        System.out.println("Enter the Station Name:");
        String st_no = sc.next();
        Station station = st.getStationByName(st_no);

        if (station == null) {
            System.out.println("Station not found.");
            return;
        }

        Platform[] platforms = st.getAllPlatformsByStationId(station.getStationId());

        if (platforms.length == 0) {
            System.out.println("No platforms available for this station.");
            return;
        }

        for (Platform platform : platforms) {
            System.out.println(platform.toString());
        }

        int p_no;
        Platform selectedPlatform = null;
        do {
            System.out.println("Enter the platform No:");
            p_no = sc.nextInt();
            selectedPlatform = st.getPlatformByStationIdAndNumber(station.getStationId(), p_no);

            if (selectedPlatform == null) {
                System.out.println("Invalid platform number. Please try again.");
                continue;
            }

            int totalCompartmentLength = t.getTotalCompartmentLength(train.getTrainId());

            if (selectedPlatform.getPlatformLength() < totalCompartmentLength) {
                System.out.println("Train can't stay at that platform. The platform length is insufficient.");
                System.out.println("Choose another platform or exit (-1).");
            } else {
                Track[] tracks = st.getAllTracksByPlatformId(selectedPlatform.getPlatformId());

                if (tracks.length == 0) {
                    System.out.println("No tracks available for this platform.");
                    continue;
                }

                for (Track track : tracks) {
                    System.out.println(track.getTrackId());
                }

                System.out.println("Select the Track Id:");
                int tId = sc.nextInt();

                Track selectedTrack = null;
                for (Track track : tracks) {
                    if (track.getTrackId() == tId) {
                        selectedTrack = track;
                        break;
                    }
                }

                if (selectedTrack == null) {
                    System.out.println("Invalid track ID. Please try again.");
                    continue;
                }

                System.out.println("Enter the Arrival Date (yyyy-MM-dd):");
                String at = sc.next();
                Date adate = java.sql.Date.valueOf(at);

                System.out.println("Enter the Depart Date (yyyy-MM-dd):");
                String dt = sc.next();
                Date ddate = java.sql.Date.valueOf(dt);

                System.out.println("Enter the Arrival Time (HH:mm):");
                String sT = sc.next() + ":00";
                Time startTime = java.sql.Time.valueOf(sT);

                System.out.println("Enter the Depart Time (HH:mm):");
                String eT = sc.next() + ":00";
                Time eTime = java.sql.Time.valueOf(eT);

                System.out.println("Enter the Fare");
                double fare = sc.nextDouble();
                
                if (sd.isTrainFree(tId, train.getTrainId(), adate, ddate, startTime, eTime)) {
                    sd.addSchedule(train.getTrainId(), selectedTrack.getTrackId(), adate, ddate, startTime, eTime,fare);
                    System.out.println("Schedule added successfully.");
                    break;
                } else {
                    System.out.println("The train is already scheduled at this track during the proposed time.");
                    System.out.println("Choose another track or exit (-1).");
                }
            }
        } while (p_no != -1);

        if (p_no == -1) {
            System.out.println("Scheduling cancelled.");
        }
    }
    
    public void addTrain() throws ClassNotFoundException, SQLException {
    	System.out.println("1.Add New Train Or 2.Update Train Or Exit -1");
    	
    	if(1==sc.nextInt()) {
    		    System.out.println("Enter the train no");
    		    String trn = sc.next();
    			sc.nextLine();
    			System.out.println("Enter the Name:");
    			String trnName = sc.nextLine();
    			System.out.println("Enter the Train Type:");
    			String trnType = sc.nextLine();
    			System.out.println("Enter the Engine Details");
    			Engine eng =addEngine();
       			System.out.println("Enter the no of coaches :");
    			int c = sc.nextInt();
    			System.out.println("Enter the Status");
    			String str = sc.next();
    			
    			
    			Train train = new Train(0,trn,trnName,trnType,eng,c,str);
    			t.addTrain(train);
    			
    			Train tr = t.getTrainByNo(trn);
    				for(int i=0;i<c;i++) {
    					addCompartment(tr);
    				}
    	
    	}else {
    		  System.out.println("Enter the train no");
  		  String trn = sc.next();
  		  Train tr = t.getTrainByNo(trn);
			
    	}
    }
    private Engine addEngine() throws ClassNotFoundException, SQLException {
    	System.out.println("Enter the Engine number:");
    	String engNo = sc.next();
    	System.out.println("Enter the Engine Type");
    	String engType = sc.next();
    	sc.nextLine();
    	System.out.println("Enter the Manufacturare company:");
    	String manufacturer = sc.nextLine();
    	System.out.println("Enter the Horse power of this Engine:");
    	int hp = sc.nextInt();
    	System.out.println("Enter the build year:");
    	int year = sc.nextInt();
    	System.out.println("Enter the Status:");
    	String st = sc.next();
    	
    	Engine eng = new Engine(0,engNo,engType,manufacturer,hp,year,st);
    	int enid = t.addEngine(eng);
    	return t.getEngineById(enid);
    }
      private void addCompartment(Train tno) throws ClassNotFoundException, SQLException {
    	 System.out.println("Enter the compartment Number:");
    	 String cno=sc.next();
    	 System.out.println("Enter the Compartment Type :");
    	 String ctype = sc.next();
    	 System.out.println("Enter the Compartment length :");
    	 int  clen = sc.nextInt();
    	 System.out.println("Enter the Compartment breadth : ");
    	 int cbr = sc.nextInt();
    	 System.out.println("1.AC or 2.non AC");
    	 boolean ac = (sc.nextInt()==1);
    	 Compartment c = new Compartment(0,tno,cno,ctype,clen,cbr,ac);
    	 int cid = t.addCompartment(c);
    	 adddesign(t.getCompartmentById(cid));
    }
      private void adddesign(Compartment c) throws ClassNotFoundException, SQLException {
    	  switch(c.getCompartmentType()) {
    	  
    	        case "1AC":
    	        	System.out.println("Enter the length of cabin:");
    	        	int len=sc.nextInt();
    	        	System.out.println("Enter the breadth of cabin:");
    	        	int br=sc.nextInt();
    	        	System.out.println("Enter the no of cabin :");
    	        	int cn = sc.nextInt();
    	        	System.out.println("Enter the path length:");
    	        	int path = sc.nextInt();
    	            System.out.println("Enter the rest room length:");
    	            int tl = sc.nextInt();
    	            if(br+path+1 == c.getCompartmentBreadth() && len*cn+tl+6 == c.getCompartmentLength() ) {
    	            	System.out.println("To make design");
    	            	toDesign(c,cn,len,br);
    	            	System.out.println("Enter the path starting column:");
    	            	int st = sc.nextInt();
    	            	t.addPath(new Path(0,c.getCompartmentLength(),path,st,1,c));
    	            	System.out.println("Enter the Toilet Starting Row:");
    	            	int tr = sc.nextInt();
    	            	System.out.println("Enter the rest room length:");
        	            int tln = sc.nextInt();
    	            	t.addRestroom(new Restroom(0,tln,tl,tr,c));
    	            }else {
    	            	System.out.println("Sorry Can't possible");
    	            }
    	            break;
    	        default:
    	        	System.out.println("Enter the no of column   (max 4 or 6) :");
    	            int cl = sc.nextInt();
    	            System.out.println("Enter the no of rows:");
    	            int rw = sc.nextInt();
    	            System.out.println("Enter the no of space:");
    	            int sp = sc.nextInt();
    	            System.out.println("Enter the length of the seat or sleeper:");
    	            int length = sc.nextInt();
    	            System.out.println("Enter the path length:");
    	            int plen = sc.nextInt();
    	            System.out.println("Enter the rest room breadth:");
    	            int tlen = sc.nextInt();
    	            if(rw+tlen+6+sp == c.getCompartmentLength() && (cl*length) + plen +1 == c.getCompartmentBreadth()) {
    	            	System.out.println("To make design");
    	            	if(!c.getCompartmentType().equals("GN")) {
    	            	todesign(c,rw,cl,length);    
    	            	System.out.println("Enter the path starting column:");
    	            	int st = sc.nextInt();
    	            	t.addPath(new Path(0,c.getCompartmentLength(),plen,st,1,c));
    	            	System.out.println("Enter the Toilet Starting Row:");
    	            	int tr = sc.nextInt();
    	            	System.out.println("Enter the rest room length:");
        	            int tln = sc.nextInt();
    	            	t.addRestroom(new Restroom(0,tln,tlen,tr,c));
    	            	}
    	            }else {
    	            	System.out.println("Sorry Can't possible");
    	            }
    	        	break;
    	  }}
    	  private void toDesign(Compartment c, int cn, int len, int br) throws ClassNotFoundException, SQLException {
    		  String[] sleeperTypes = {"Upper", "Lower"};
    		  String[] sleepe = {"Row 1", "Row 2"};

    		  for(int i=1;i<=cn;i++) {
    			     String cabinNo = String.format("C%03d", (i - 1) * 4  + 1);
  
    			    int cid= t.addCabin(new Cabin(0,cabinNo,len,br,i,c));
    			   Cabin cb = t.getCabinById(cid);
    			    for(int j=1;j<4;j++) {
    			    		String sleeperNo = String.format("S%03d", (i - 1) * 4 + j + 1);
    			    		t.addSleeperInCabin(new SleeperInCabin(0,sleeperNo,sleeperTypes[(j-1)%2],sleepe[(i-1)%2],cb));
    			  }
    		  }
    	  }

		private void todesign(Compartment c, int row,int col,int len) throws ClassNotFoundException, SQLException {
    		 
    		  String[] sleeperTypes = {"Upper", "Middle", "Lower", "Side Upper", "Side Lower"};
    		  char[] seatTypes = {'A','B','C','D','E','G'};
    		  String sleeperType="";
    		  for (int i = 1; i <= row; i++) {
    	            for (int j = 1; j <= col; j++) {
    	            	
                        String sleeperNo = String.format("S%03d", (i - 1) * col + j + 1);

    	            	if(c.getCompartmentType().equals("SS")) {
                            String seatType = ""+seatTypes[(j-1) % seatTypes.length];
                            t.addSeat(new Seat(0,sleeperNo,c,seatType,len,i,j));
    	            	}else { 
    	            		
    	            		if(c.getCompartmentType().equals("2AC")){
    	            			if(j >= 4) {
    	            				sleeperType=sleeperTypes[3+(i%2)];
    	            			}else {
    	            			int k;
    	            				if((j-1)%2 == 0) {
    	            					k=(j-1)%2;
    	            				}else {
    	            					k=j+1;
    	            				}
    	            			sleeperType=sleeperTypes[k];
    	            		}
    	            	}else{
    	            		if(j >= 4) {
    	            			sleeperType=sleeperTypes[3+(i%2)];
    	            		}else {
    	            			sleeperType= sleeperTypes[(j-1) % 3];
    	            	}
    	            		}
    	            		t.addSleeper(new Sleeper(0,sleeperNo,sleeperType,i,len,j,c));
    	            	}
    	            	
    	            	
    	            }
    	       }
    	  }
      
      
}
