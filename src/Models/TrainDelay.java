package Models;

import java.sql.Timestamp;

public class TrainDelay {
    private int delayId;
    private Train train;
    private Station station; 
    private Timestamp delayDate;
    private int delayDuration; 
    private String reason;

    public TrainDelay(int delayId, Train train, Station station, Timestamp delayDate, int delayDuration, String reason) {
        this.delayId = delayId;
        this.train = train;
        this.station = station; 
        this.delayDate = delayDate;
        this.delayDuration = delayDuration;
        this.reason = reason;
    }


    public int getDelayId() {
        return delayId;
    }

    public void setDelayId(int delayId) {
        this.delayId = delayId;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Station getStation() {
        return station; // Getter for station
    }

    public void setStation(Station station) {
        this.station = station; // Setter for station
    }

    public Timestamp getDelayDate() {
        return delayDate;
    }

    public void setDelayDate(Timestamp delayDate) {
        this.delayDate = delayDate;
    }

    public int getDelayDuration() {
        return delayDuration;
    }

    public void setDelayDuration(int delayDuration) {
        this.delayDuration = delayDuration;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


	@Override
	public String toString() {
		return "TrainDelay [delayId=" + delayId + ", train=" + train + ", station=" + station + ", delayDate="
				+ delayDate + ", delayDuration=" + delayDuration + ", reason=" + reason + "]";
	}
    
}
