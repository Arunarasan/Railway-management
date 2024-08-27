package Models;

public class SleeperInCabin {
    private int sleeperId;
    private String sleeperNo;
    private String sleeperType;
    private String position; 
    private Cabin cabin; 

    public SleeperInCabin(int sleeperId, String sleeperNo, String sleeperType, String position, Cabin cabin) {
        this.sleeperId = sleeperId;
        this.sleeperNo = sleeperNo;
        this.sleeperType = sleeperType;
        this.position = position;
        this.cabin = cabin;
    }

    public int getSleeperId() {
        return sleeperId;
    }

    public void setSleeperId(int sleeperId) {
        this.sleeperId = sleeperId;
    }

    public String getSleeperNo() {
        return sleeperNo;
    }

    public void setSleeperNo(String sleeperNo) {
        this.sleeperNo = sleeperNo;
    }

    public String getSleeperType() {
        return sleeperType;
    }

    public void setSleeperType(String sleeperType) {
        this.sleeperType = sleeperType;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Cabin getCabin() {
        return cabin;
    }

    public void setCabin(Cabin cabin) {
        this.cabin = cabin;
    }

	@Override
	public String toString() {
		return "SleeperInCabin [sleeperId=" + sleeperId + ", sleeperNo=" + sleeperNo + ", sleeperType=" + sleeperType
				+ ", position=" + position + ", cabin=" + cabin + "]";
	}
    
}
