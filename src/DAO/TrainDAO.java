package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Connection.ConnectDB;
import Models.*;


public class TrainDAO {

	public int addEngine(Engine engine) throws SQLException, ClassNotFoundException {
	    String sql = "INSERT INTO Engine (engine_number, engine_type, manufacturer, horsepower, build_year, status) VALUES (?, ?, ?, ?, ?, ?)";
	    
	    try (Connection conn = ConnectDB.getConnect();
	         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { 
	        stmt.setString(1, engine.getEngineNumber());
	        stmt.setString(2, engine.getEngineType());
	        stmt.setString(3, engine.getManufacturer());
	        stmt.setInt(4, engine.getHorsepower());
	        stmt.setInt(5, engine.getBuildYear());
	        stmt.setString(6, engine.getStatus());
	        
	        stmt.executeUpdate();
	        
	        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                return generatedKeys.getInt(1);
	            } else {
	                throw new SQLException("Failed to insert engine, no ID obtained.");
	            }
	        }
	    }
	}

	 
	 public int addCompartment(Compartment compartment) throws SQLException, ClassNotFoundException {
		    String sql = "INSERT INTO Compartment (train_id, compartment_no, compartment_type, compartment_length, compartment_breadth, ac) VALUES (?, ?, ?, ?, ?, ?)";
		    int generatedId = -1;
		    
		    try (Connection conn = ConnectDB.getConnect(); 
		         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { 
		         
		        stmt.setInt(1, compartment.getTrain().getTrainId());
		        stmt.setString(2, compartment.getCompartmentNo());
		        stmt.setString(3, compartment.getCompartmentType());
		        stmt.setInt(4, compartment.getCompartmentLength());
		        stmt.setInt(5, compartment.getCompartmentBreadth());
		        stmt.setBoolean(6, compartment.isAc());

		        stmt.executeUpdate();
		        
		        try (ResultSet rs = stmt.getGeneratedKeys()) {
		            if (rs.next()) {
		                generatedId = rs.getInt(1); 
		            }
		        }
		    }
		    
		    return generatedId;
		}

	 
	  public void addPath(Path path) throws SQLException, ClassNotFoundException {
	        String sql = "INSERT INTO Path (path_length, path_breadth, path_row, path_col, compartment_id) VALUES (?, ?, ?, ?, ?)";
	        
	        try (Connection conn = ConnectDB.getConnect(); 
	             PreparedStatement stmt = conn.prepareStatement(sql)) { 
	             
	            stmt.setInt(1, path.getPathLength());
	            stmt.setInt(2, path.getPathBreadth());
	            stmt.setInt(3, path.getPathRow());
	            stmt.setInt(4, path.getPathCol());
	            stmt.setInt(5, path.getCompartment().getCompartmentId()); 
	            
	            stmt.executeUpdate();
	        }
	    }
	  
	  public Path getPathsByCompartmentId(int compartmentId) throws SQLException, ClassNotFoundException {
		    String sql = "SELECT * FROM Path WHERE compartment_id = ? LIMIT 1;";
		    Path path=null;
		    try (Connection conn = ConnectDB.getConnect();
		         PreparedStatement stmt = conn.prepareStatement(sql)) {

		        stmt.setInt(1, compartmentId);

		        try (ResultSet rs = stmt.executeQuery()) {
		            while (rs.next()) {
		            	  Compartment compartment = getCompartmentById(rs.getInt("compartment_id"));
		                path = new Path(
		                    rs.getInt("path_id"), 
		                    rs.getInt("path_length"),
		                    rs.getInt("path_breadth"),
		                    rs.getInt("path_row"),
		                    rs.getInt("path_col"),
		                    compartment
		                );
		            }
		        }
		    }

		    return path;
		}

	  
	  public void addRestroom(Restroom restroom) throws SQLException, ClassNotFoundException {
	        String sql = "INSERT INTO Restroom (length, breadth, toilet_row, compartment_id) VALUES (?, ?, ?, ?)";
	        
	        try (Connection conn = ConnectDB.getConnect();
	             PreparedStatement stmt = conn.prepareStatement(sql)) { 
	             
	            stmt.setInt(1, restroom.getLength());
	            stmt.setInt(2, restroom.getBreadth());
	            stmt.setInt(3, restroom.getToiletRow());
	            stmt.setInt(4, restroom.getCompartment().getCompartmentId()); 
	            
	            stmt.executeUpdate();
	        }
	    }
	
    public void addTrain(Train train) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Train (train_number, train_name, train_type, engine_id, total_coaches, status) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
             
            stmt.setString(1, train.getTrainNumber());
            stmt.setString(2, train.getTrainName());
            stmt.setString(3, train.getTrainType());
            stmt.setInt(4, train.getEngine().getEngineId()); 
            stmt.setInt(5, train.getTotalCoaches());
            stmt.setString(6, train.getStatus());
            
            stmt.executeUpdate();
        }
       }
    public void updateTrain(Train train) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Train SET train_name = ?, train_type = ?, engine_id = ?, total_coaches = ?, status = ? WHERE train_number = ?";
        
        try (Connection conn = ConnectDB.getConnect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, train.getTrainName());
            stmt.setString(2, train.getTrainType());
            stmt.setInt(3, train.getEngine().getEngineId());
            stmt.setInt(4, train.getTotalCoaches());
            stmt.setString(5, train.getStatus());
            stmt.setString(6, train.getTrainNumber());
            
            stmt.executeUpdate();
        }
    }
    public int addSleeper(Sleeper sleeper) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Sleeper (sleeper_no, length, breadth, sleeper_row, compartment_id) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectDB.getConnect();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, sleeper.getSleeperNo());
            stmt.setInt(2, sleeper.getLength());
            stmt.setInt(3, sleeper.getBreadth());
            stmt.setInt(4, sleeper.getSleeperRow());
            stmt.setInt(5, sleeper.getCompartment().getCompartmentId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating sleeper failed, no ID obtained.");
                    }
                }
            } else {
                throw new SQLException("Creating sleeper failed, no rows affected.");
            }
        }
    }

        
        public int addCabin(Cabin cabin) throws SQLException, ClassNotFoundException {
            String sql = "INSERT INTO Cabin (cabin_no, length, breadth, cabin_row, compartment_id) VALUES (?, ?, ?, ?, ?)";
            int generatedId = -1;  
            
            try (Connection conn = ConnectDB.getConnect();
                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                 
                stmt.setString(1, cabin.getCabinNo()); 
                stmt.setInt(2, cabin.getLength()); 
                stmt.setInt(3, cabin.getBreadth()); 
                stmt.setInt(4, cabin.getCabinRow()); 
                stmt.setInt(5, cabin.getCompartment().getCompartmentId()); 

                stmt.executeUpdate();
                
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1); 
                    }
                }
            }
            
            return generatedId;  
        }

        
        public int addSeat(Seat seat) throws SQLException, ClassNotFoundException {
            String sql = "INSERT INTO Seat (compartment_id, seat_no, seat_type, length, row, column) VALUES (?, ?, ?, ?, ?, ?)";
            
            try (Connection conn = ConnectDB.getConnect();
                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                
                stmt.setInt(1, seat.getCompartment().getCompartmentId());
                stmt.setString(2, seat.getSeatNo());
                stmt.setString(3, seat.getSeatType());
                stmt.setInt(4, seat.getLength());
                stmt.setInt(5, seat.getPosRow());
                stmt.setInt(6, seat.getPosColumn());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            return generatedKeys.getInt(1);
                        } else {
                            throw new SQLException("Creating seat failed, no ID obtained.");
                        }
                    }
                } else {
                    throw new SQLException("Creating seat failed, no rows affected.");
                }
            }
        }

        
        public void addDoor(Door door) throws SQLException, ClassNotFoundException {
            String sql = "INSERT INTO Door (door_row, door_length, compartment_id) VALUES (?, ?, ?)";
            
            try (Connection conn = ConnectDB.getConnect(); 
                 PreparedStatement stmt = conn.prepareStatement(sql)) { 
                 
                stmt.setInt(1, door.getDoorRow());
                stmt.setInt(2, door.getDoorLength());
                stmt.setInt(3, door.getCompartment().getCompartmentId()); 
                stmt.executeUpdate();
            }
        }
        
        public void addExit(Exit exit) throws SQLException, ClassNotFoundException {
            String sql = "INSERT INTO Exit (exit_row, exit_length, compartment_id) VALUES (?, ?, ?)";
            
            try (Connection conn = ConnectDB.getConnect(); 
                 PreparedStatement stmt = conn.prepareStatement(sql)) { 
                 
                stmt.setInt(1, exit.getExitRow());
                stmt.setInt(2, exit.getExitLength());
                stmt.setInt(3, exit.getCompartment().getCompartmentId()); 
                stmt.executeUpdate();
            }
        }
        
        public int addSleeperInCabin(SleeperInCabin sleeperInCabin) throws SQLException, ClassNotFoundException {
            String sql = "INSERT INTO SleeperInCabin (sleeper_no, sleeper_type, position, cabin_id) VALUES (?, ?, ?, ?)";
            
            try (Connection conn = ConnectDB.getConnect();
                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                
                stmt.setString(1, sleeperInCabin.getSleeperNo());
                stmt.setString(2, sleeperInCabin.getSleeperType());
                stmt.setString(3, sleeperInCabin.getPosition());
                stmt.setInt(4, sleeperInCabin.getCabin().getCabinId());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            return generatedKeys.getInt(1);
                        } else {
                            throw new SQLException("Creating SleeperInCabin failed, no ID obtained.");
                        }
                    }
                } else {
                    throw new SQLException("Creating SleeperInCabin failed, no rows affected.");
                }
            }
        }

        
        public Seat getSeatById(int seatId) throws SQLException, ClassNotFoundException {
            String sql = "SELECT * FROM Seat WHERE seat_id = ?";
            Seat seat = null;
            
            try (Connection conn = ConnectDB.getConnect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                 
                stmt.setInt(1, seatId);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Compartment compartment = getCompartmentById(rs.getInt("compartment_id"));
                        
                        seat = new Seat(
                            rs.getInt("seat_id"),
                            rs.getString("seat_no"),
                            compartment,
                            rs.getString("seat_type"),
                            rs.getInt("length"),
                            rs.getInt("pos_row"),
                            rs.getInt("pos_column")
                        );
                    }
                }
            }
            return seat;
        }
        
        public Seat getSeatByCompartmentId(int seatId , String seatno) throws SQLException, ClassNotFoundException {
            String sql = "SELECT * FROM Seat WHERE compartment_id = ? And seat_no = ? ";
            Seat seat = null;
            
            try (Connection conn = ConnectDB.getConnect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                 
                stmt.setInt(1, seatId);
                stmt.setString(2, seatno);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Compartment compartment = getCompartmentById(rs.getInt("compartment_id"));
                        
                        seat = new Seat(
                            rs.getInt("seat_id"),
                            rs.getString("seat_no"),
                            compartment,
                            rs.getString("seat_type"),
                            rs.getInt("length"),
                            rs.getInt("pos_row"),
                            rs.getInt("pos_column")
                        );
                    }
                }
            }
            return seat;
        } 
        
        public Cabin getCabinById(int cabinId) throws SQLException, ClassNotFoundException {
            String sql = "SELECT * FROM Cabin WHERE cabin_id = ?";
            Cabin cabin = null;

            try (Connection conn = ConnectDB.getConnect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                 
                stmt.setInt(1, cabinId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Compartment compartment =getCompartmentById(rs.getInt("compartment_id"));

                        cabin = new Cabin(
                            rs.getInt("cabin_id"),
                            rs.getString("cabin_no"),
                            rs.getInt("length"),
                            rs.getInt("breadth"),
                            rs.getInt("cabin_row"),
                            compartment
                        );
                    }
                }
            }
            return cabin;
        }
        
        public SleeperInCabin[] getAllSleeperInCabins(int compartmentId) throws SQLException, ClassNotFoundException {
            String sql = "SELECT * FROM SleeperInCabin JOIN Cabin ON SleeperInCabin.cabin_id = Cabin.cabin_id WHERE Cabin.compartment_id = ?";
            List<SleeperInCabin> sleeperInCabinList = new ArrayList<>();

            try (Connection conn = ConnectDB.getConnect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                
                stmt.setInt(1, compartmentId); 
                
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                    	Cabin cabin = getCabinById(rs.getInt("cabin_id"));
                        SleeperInCabin sleeperInCabin = new SleeperInCabin(
                            rs.getInt("sleeper_id"),
                            rs.getString("sleeper_no"),
                            rs.getString("sleeper_type"),
                            rs.getString("position"),
                            cabin
                        );
                        sleeperInCabinList.add(sleeperInCabin);
                    }
                }
            }

            return sleeperInCabinList.toArray(new SleeperInCabin[0]);
        }
        
        public SleeperInCabin[] getAllSleeperInCabinsBycabin(int compartmentId) throws SQLException, ClassNotFoundException {
            String sql = "SELECT * FROM SleeperInCabin  WHERE cabin_id = ?";
            List<SleeperInCabin> sleeperInCabinList = new ArrayList<>();

            try (Connection conn = ConnectDB.getConnect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                
                stmt.setInt(1, compartmentId); 
                
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                    	Cabin cabin = getCabinById(rs.getInt("cabin_id"));
                        SleeperInCabin sleeperInCabin = new SleeperInCabin(
                            rs.getInt("sleeper_id"),
                            rs.getString("sleeper_no"),
                            rs.getString("sleeper_type"),
                            rs.getString("position"),
                            cabin
                        );
                        sleeperInCabinList.add(sleeperInCabin);
                    }
                }
            }

            return sleeperInCabinList.toArray(new SleeperInCabin[0]);
        }


        public Sleeper getSleeperById(int sleeperId) throws SQLException, ClassNotFoundException {
            String sql = "SELECT * FROM Sleeper WHERE sleeper_id = ?";
            Sleeper sleeper = null;

            try (Connection conn = ConnectDB.getConnect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                 
                stmt.setInt(1, sleeperId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Compartment compartment = getCompartmentById(rs.getInt("compartment_id"));

                        sleeper = new Sleeper(
                            rs.getInt("sleeper_id"),
                            rs.getString("sleeper_no"),
                            rs.getString("sleeper_type"),
                            rs.getInt("sleeper_row"),
                            rs.getInt("length"),
                            rs.getInt("pos_row"),
                            compartment
                        );
                    }
                }
            }
            return sleeper;
        }
        public Sleeper getSleeperByCompartmentId(int sleeperId,String sleeperno) throws SQLException, ClassNotFoundException {
            String sql = "SELECT * FROM Sleeper WHERE compartment_id = ? AND sleeper_no = ?";
            Sleeper sleeper = null;

            try (Connection conn = ConnectDB.getConnect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                 
                stmt.setInt(1, sleeperId);
                stmt.setString(2, sleeperno);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Compartment compartment = getCompartmentById(rs.getInt("compartment_id"));

                        sleeper = new Sleeper(
                            rs.getInt("sleeper_id"),
                            rs.getString("sleeper_no"),
                            rs.getString("sleeper_type"),
                            rs.getInt("sleeper_row"),
                            rs.getInt("length"),
                            rs.getInt("pos_row"),
                            compartment
                        );
                    }
                }
            }
            return sleeper;
        }
        
        public Sleeper[] getSleepersByCompartmentIdAndRow(int compartmentId, int sleeperRow) throws SQLException, ClassNotFoundException {
            String sql = "SELECT * FROM Sleeper WHERE compartment_id = ? AND sleeper_row = ?";
            List<Sleeper> sleeperList = new ArrayList<>();

            try (Connection conn = ConnectDB.getConnect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                 
                stmt.setInt(1, compartmentId);
                stmt.setInt(2, sleeperRow);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Compartment compartment = getCompartmentById(rs.getInt("compartment_id"));

                        Sleeper sleeper = new Sleeper(
                            rs.getInt("sleeper_id"),
                            rs.getString("sleeper_no"),
                            rs.getString("sleeper_type"),
                            rs.getInt("sleeper_row"),
                            rs.getInt("length"),
                            rs.getInt("pos_row"),
                            compartment
                        );
                        
                        sleeperList.add(sleeper);
                    }
                }
            }

            return sleeperList.toArray(new Sleeper[0]);
        }

        public SleeperInCabin getSleeperInCabinById(int sleeperId) throws SQLException, ClassNotFoundException {
            String sql = "SELECT * FROM SleeperInCabin WHERE sleeper_id = ?";
            SleeperInCabin sleeperInCabin = null;

            try (Connection conn = ConnectDB.getConnect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, sleeperId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Cabin cabin = getCabinById(rs.getInt("cabin_id"));
                        sleeperInCabin = new SleeperInCabin(
                            rs.getInt("sleeper_id"),
                            rs.getString("sleeper_no"),
                            rs.getString("sleeper_type"),
                            rs.getString("position"),
                            cabin
                        );
                    }
                }
            }
            return sleeperInCabin;
        }
        public SleeperInCabin getSleeperInByCompartmentId(int sleeperId,String sleeperno) throws SQLException, ClassNotFoundException {
            String sql = "SELECT * FROM SleeperInCabin as sc join cabin as c on sc.cabin_id = c.cabin_id WHERE c.compartment_id = ? AND sleeper_no = ?";
            SleeperInCabin sleeperInCabin = null;

            try (Connection conn = ConnectDB.getConnect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, sleeperId);
                stmt.setString(2, sleeperno);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Cabin cabin = getCabinById(rs.getInt("cabin_id"));
                        sleeperInCabin = new SleeperInCabin(
                            rs.getInt("sleeper_id"),
                            rs.getString("sleeper_no"),
                            rs.getString("sleeper_type"),
                            rs.getString("position"),
                            cabin
                        );
                    }
                }
            }
            return sleeperInCabin;
        }
        
        public Compartment getCompartmentById(int compartmentId) throws SQLException, ClassNotFoundException {
            String sql = "SELECT * FROM Compartment WHERE compartment_id = ?";
            Compartment compartment = null;
            
            try (Connection conn = ConnectDB.getConnect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                 
                stmt.setInt(1, compartmentId);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Train train = getTrainById(rs.getInt("train_id"));
                        
                        compartment = new Compartment(
                            rs.getInt("compartment_id"),
                            train,
                            rs.getString("compartment_no"),
                            rs.getString("compartment_type"),
                            rs.getInt("compartment_length"),
                            rs.getInt("compartment_breadth"),
                            rs.getBoolean("ac"));
                    }
                }
            }
            return compartment;
        }
        public Train[] getAllTrains() throws SQLException, ClassNotFoundException {
            String sql = "SELECT * FROM Train";
            List<Train> trainList = new ArrayList<>();
            
            try (Connection conn = ConnectDB.getConnect();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                    Engine engine = getEngineById(rs.getInt("engine_id"));
                    
                    Train train = new Train(
                        rs.getInt("train_id"),
                        rs.getString("train_number"),
                        rs.getString("train_name"),
                        rs.getString("train_type"),
                        engine,
                        rs.getInt("total_coaches"),
                        rs.getString("status")
                    );
                    
                    trainList.add(train);
                }
            }
            
            Train[] trainArray = new Train[trainList.size()];
            trainArray = trainList.toArray(trainArray);
            
            return trainArray;
        }

        
        public Train getTrainById(int trainId) throws SQLException, ClassNotFoundException {
            String sql = "SELECT * FROM Train WHERE train_id = ?";
            Train train = null;
            
            try (
                 PreparedStatement stmt = ConnectDB.getConnect().prepareStatement(sql)) {
                 
                stmt.setInt(1, trainId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Engine engine = getEngineById(rs.getInt("engine_id"));
                        train = new Train(
                            rs.getInt("train_id"),
                            rs.getString("train_number"),
                            rs.getString("train_name"),
                            rs.getString("train_type"),
                            engine,
                            rs.getInt("total_coaches"),
                            rs.getString("status"));
                    }
                }
            }
            return train;
        }
         
   
        
        public Train getTrainByNo(String trainId) throws SQLException, ClassNotFoundException {
            String sql = "SELECT * FROM Train WHERE train_number = ?";
            Train train = null;
            
            try (
                 PreparedStatement stmt = ConnectDB.getConnect().prepareStatement(sql)) {
                 
                stmt.setString(1, trainId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Engine engine = getEngineById(rs.getInt("engine_id"));
                        train = new Train(
                            rs.getInt("train_id"),
                            rs.getString("train_number"),
                            rs.getString("train_name"),
                            rs.getString("train_type"),
                            engine,
                            rs.getInt("total_coaches"),
                            rs.getString("status"));
                    }
                }
            }
            return train;
        }
        public boolean deleteTrain(int trainId) throws SQLException, ClassNotFoundException {
            String sql = "DELETE FROM Train WHERE train_id = ?";
            boolean rowDeleted = false;
            
            try (
                 PreparedStatement stmt =ConnectDB.getConnect().prepareStatement(sql)) {
                 
                stmt.setInt(1, trainId);
                rowDeleted = stmt.executeUpdate() > 0;
            }
            
            return rowDeleted;
        }
        
        public Engine getEngineById(int engineId) throws SQLException, ClassNotFoundException {
            String sql = "SELECT * FROM Engine WHERE engine_id = ?";
            Engine engine = null;
            
            try (
                 PreparedStatement stmt = ConnectDB.getConnect().prepareStatement(sql)) {
                 
                stmt.setInt(1, engineId);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        engine = new Engine(
                            rs.getInt("engine_id"),
                            rs.getString("engine_number"),
                            rs.getString("engine_type"),
                            rs.getString("manufacturer"),
                            rs.getInt("horsepower"),
                            rs.getInt("build_year"),
                            rs.getString("status")
                        );
                    }
                }
            }
            return engine;
        }
        
        public Compartment[] getCompartments(int tid) throws ClassNotFoundException {
            String sql = "SELECT * FROM Compartment WHERE train_id = ?";
            ArrayList<Compartment> compartmentsList = new ArrayList<>();

            try (Connection conn = ConnectDB.getConnect(); 
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, tid);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                    	 Train train = getTrainById(rs.getInt("train_id"));
                         
                    	 Compartment  compartment = new Compartment(
                             rs.getInt("compartment_id"),
                             train,
                             rs.getString("compartment_no"),
                             rs.getString("compartment_type"),
                             rs.getInt("compartment_length"),
                             rs.getInt("compartment_breadth"),
                             rs.getBoolean("ac"));           
                    	 compartmentsList.add(compartment);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Compartment[] compartmentsArray = new Compartment[compartmentsList.size()];
            return compartmentsList.toArray(compartmentsArray);
        }
        
        public Seat[] getSeatsInCompartment(int cmpId) throws ClassNotFoundException {
            String sql = "SELECT * FROM Seat WHERE compartment_id = ?";
            ArrayList<Seat> seatList = new ArrayList<>();

            try (Connection conn = ConnectDB.getConnect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, cmpId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                     Compartment compartment = getCompartmentById(rs.getInt("compartment_id"));
                        
                            Seat seat = new Seat(
                            rs.getInt("seat_id"),
                            rs.getString("seat_no"),
                            compartment,
                            rs.getString("seat_type"),
                            rs.getInt("length"),
                            rs.getInt("pos_row"),
                            rs.getInt("pos_column")
                        );
                        seatList.add(seat);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return seatList.toArray(new Seat[0]);
        }
        
        public int getTotalCompartmentLength(int tid) throws ClassNotFoundException {
            String sql = "SELECT SUM(compartment_length) AS total_length FROM Compartment WHERE train_id = ?";
            int totalLength = 0;

            try (Connection conn = ConnectDB.getConnect(); 
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, tid);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        totalLength = rs.getInt("total_length");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return totalLength;
        }

        
        
        public Sleeper[] getSleepersByCompartmentId(int compartmentId) throws SQLException, ClassNotFoundException {
            String sql = "SELECT * FROM Sleeper WHERE compartment_id = ?";
            List<Sleeper> sleeperList = new ArrayList<>();

            try (Connection conn = ConnectDB.getConnect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                 
                stmt.setInt(1, compartmentId);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Compartment compartment = getCompartmentById(compartmentId);

                        if (compartment == null) {
                            throw new SQLException("Compartment with ID " + compartmentId + " not found.");
                        }

                        Sleeper sleeper = new Sleeper(
                            rs.getInt("sleeper_id"),
                            rs.getString("sleeper_no"),
                            rs.getString("sleeper_type"),
                            rs.getInt("sleeper_row"),
                            rs.getInt("length"),
                            rs.getInt("pos_row"),
                            compartment
                        );
                        sleeperList.add(sleeper);
                    }
                }
            }

            return sleeperList.toArray(new Sleeper[0]);
        }

        public Restroom getRestroomById(int toiletId) throws ClassNotFoundException {
            Restroom restroom = null;
            String query = "SELECT * FROM Restroom WHERE compartment_id = ?";

            try (Connection conn = ConnectDB.getConnect(); 
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setInt(1, toiletId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int length = rs.getInt("length");
                    int breadth = rs.getInt("breadth");
                    int toiletRow = rs.getInt("toilet_row");

                    int compartmentId = rs.getInt("compartment_id");
                    Compartment compartment = getCompartmentById(compartmentId);

                    restroom = new Restroom(toiletId, length, breadth, toiletRow, compartment);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return restroom;
        }
       
        public Seat getSeat(int compartmentId,int row,int col) throws ClassNotFoundException, SQLException {
        	  String sql = "SELECT * FROM Seat WHERE compartment_id = ? And pos_row = ? AND pos_column = ? ";
              Seat seat = null;
              
              try (Connection conn = ConnectDB.getConnect();
                   PreparedStatement stmt = conn.prepareStatement(sql)) {
                   
                  stmt.setInt(1, compartmentId);
                  stmt.setInt(2, row);
                  stmt.setInt(3, col);
                  try (ResultSet rs = stmt.executeQuery()) {
                      if (rs.next()) {
                          Compartment compartment = getCompartmentById(rs.getInt("compartment_id"));
                          
                          seat = new Seat(
                              rs.getInt("seat_id"),
                              rs.getString("seat_no"),
                              compartment,
                              rs.getString("seat_type"),
                              rs.getInt("length"),
                              rs.getInt("pos_row"),
                              rs.getInt("pos_column")
                          );
                      }
                  }
              }
              return seat;
          } 
}
