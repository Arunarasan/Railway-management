package Models;

import java.util.Arrays;
import java.util.Date;

public class FullJourney {
    

	@Override
	public String toString() {
		return "FullJourney [booking=" + booking + ", schedule=" + Arrays.toString(schedule) + ", bookingDate="
				+ bookingDate + "]";
	}

	private Booking booking; 
    private Schedule schedule[]; 
    private Date bookingDate;

    public FullJourney() {}

    public FullJourney(Booking booking, Schedule schedule[], Date bookingDate) {
        this.booking = booking;
        this.schedule = schedule;
        this.bookingDate = bookingDate;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Schedule[] getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule[]) {
        this.schedule = schedule;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }
}
