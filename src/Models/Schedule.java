package Models;
import java.sql.Time;
import java.util.Date;

public class Schedule {
    @Override
	public String toString() {
		return "Schedule [scheduleId=" + scheduleId + ", train=" + train + ", track=" + track + ", arrivalTime="
				+ arrivalTime + ", departTime=" + departTime + ", arrivalDate=" + arrivalDate + ", departDate="
				+ departDate + ", fare=" + fare + "]";
	}

	private int scheduleId;
    private Train train; 
    private Track track; 
    private Time arrivalTime;
    private Time departTime;
    private Date arrivalDate;
    private Date departDate;
    private double fare;

    public Schedule() {}

    public Schedule(int scheduleId, Train train, Track track, Time arrivalTime, Time departTime, 
                    Date arrivalDate, Date departDate, double fare) {
        this.scheduleId = scheduleId;
        this.train = train;
        this.track = track;
        this.arrivalTime = arrivalTime;
        this.departTime = departTime;
        this.arrivalDate = arrivalDate;
        this.departDate = departDate;
        this.fare = fare;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Time arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Time getDepartTime() {
        return departTime;
    }

    public void setDepartTime(Time departTime) {
        this.departTime = departTime;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Date getDepartDate() {
        return departDate;
    }

    public void setDepartDate(Date departDate) {
        this.departDate = departDate;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }
}
