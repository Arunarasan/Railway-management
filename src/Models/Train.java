package Models;

public class Train {
    private int trainId;
    private String trainNumber;
    private String trainName;
    private String trainType;
    private Engine engine; 
    private int totalCoaches;
    private String status;
    
    public Train(int trainId, String trainNumber, String trainName, String trainType, Engine engine, int totalCoaches, String status) {
        this.trainId = trainId;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.trainType = trainType;
        this.engine = engine;
        this.totalCoaches = totalCoaches;
        this.status = status;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public int getTotalCoaches() {
        return totalCoaches;
    }

    public void setTotalCoaches(int totalCoaches) {
        this.totalCoaches = totalCoaches;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	@Override
	public String toString() {
		return "Train [trainId=" + trainId + ", trainNumber=" + trainNumber + ", trainName=" + trainName
				+ ", trainType=" + trainType + ", engine=" + engine + ", totalCoaches=" + totalCoaches + ", status="
				+ status + "]";
	}
    
}
