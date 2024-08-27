package Models;

import java.sql.Timestamp;

public class Fare {
    private String trainType;
    private String compartmentType;
    private String seatType;
    private double fare; 
    private Timestamp createdAt;

    public Fare() {
    }

    public Fare(String trainType, String compartmentType, String seatType, double fare, Timestamp createdAt) {
        this.trainType = trainType;
        this.compartmentType = compartmentType;
        this.seatType = seatType;
        this.fare = fare;
        this.createdAt = createdAt;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public String getCompartmentType() {
        return compartmentType;
    }

    public void setCompartmentType(String compartmentType) {
        this.compartmentType = compartmentType;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Fare{" +
                "trainType='" + trainType + '\'' +
                ", compartmentType='" + compartmentType + '\'' +
                ", seatType='" + seatType + '\'' +
                ", fare=" + fare +
                ", createdAt=" + createdAt +
                '}';
    }
}
