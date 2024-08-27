package Models;

import java.sql.Timestamp;

public class Platform {
    private int platformId;
    private int platformNumber;
    private Station station; 
    private int platformLength;
    private String platformType;
    private boolean isActive;
    private Timestamp createdAt;

    public Platform(int platformId, int platformNumber, Station station, int platformLength, String platformType,
			boolean isActive, Timestamp createdAt) {
		super();
		this.platformId = platformId;
		this.platformNumber = platformNumber;
		this.station = station;
		this.platformLength = platformLength;
		this.platformType = platformType;
		this.isActive = isActive;
		this.createdAt = createdAt;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public int getPlatformNumber() {
        return platformNumber;
    }

    public void setPlatformNumber(int platformNumber) {
        this.platformNumber = platformNumber;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public int getPlatformLength() {
        return platformLength;
    }

    @Override
	public String toString() {
		return "Platform [platformId=" + platformId + ", platformNumber=" + platformNumber + ", station=" + station
				+ ", platformLength=" + platformLength + ", platformType=" + platformType + ", isActive=" + isActive
				+ ", createdAt=" + createdAt + "]";
	}

	public void setPlatformLength(int platformLength) {
        this.platformLength = platformLength;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
