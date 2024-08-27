package Models;
import java.util.Date;

public class Booking {
 

	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", pnrNo=" + pnrNo + ", passenger=" + passenger + ", user=" + user
				+ ", seat=" + seat + ", sleeper=" + sleeper + ", sleeperin=" + sleeperin + ", bookingDate="
				+ bookingDate + ", fare=" + fare + "]";
	}

	private int bookingId;
    private int pnrNo;
    private Passenger passenger;
    private User user; 
    private Seat seat; 
    private Sleeper sleeper;
    private SleeperInCabin sleeperin;
    private Date bookingDate;
    private double fare; 
    public Booking() {}

   
    public SleeperInCabin getSleeperin() {
		return sleeperin;
	}


	public void setSleeperin(SleeperInCabin sleeperin) {
		this.sleeperin = sleeperin;
	}

	public double getFare() {
		return fare;
	}


	public void setFare(double fare) {
		this.fare = fare;
	}


	public Booking(int bookingId, int pnrNo, Passenger passenger, User user, Seat seat, Sleeper sleeper,
			SleeperInCabin sleeperin, Date bookingDate, double fare) {
		super();
		this.bookingId = bookingId;
		this.pnrNo = pnrNo;
		this.passenger = passenger;
		this.user = user;
		this.seat = seat;
		this.sleeper = sleeper;
		this.sleeperin = sleeperin;
		this.bookingDate = bookingDate;
		this.fare = fare;
	}


	public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getPnrNo() {
        return pnrNo;
    }

    public void setPnrNo(int pnrNo) {
        this.pnrNo = pnrNo;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Sleeper getSleeper() {
        return sleeper;
    }

    public void setSleeper(Sleeper sleeper) {
        this.sleeper = sleeper;
    }


    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }
}
