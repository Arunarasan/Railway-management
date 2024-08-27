package Models;

import java.sql.Timestamp;

public class Station {
    private int stationId;
    private String stationName;
    private String location;
    private String city;
    private String state;
    private Timestamp createdAt;
	public Station(int stationId, String stationName, String location, String city, String state, 
			Timestamp createdAt) {
		super();
		this.stationId = stationId;
		this.stationName = stationName;
		this.location = location;
		this.city = city;
		this.state = state;
		this.createdAt = createdAt;
	}
	public int getStationId() {
		return stationId;
	}
	public void setStationId(int stationId) {
		this.stationId = stationId;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	@Override
	public String toString() {
		return "Station [stationId=" + stationId + ", stationName=" + stationName + ", location=" + location + ", city="
				+ city + ", state=" + state + ", createdAt=" + createdAt + "]";
	}
}
