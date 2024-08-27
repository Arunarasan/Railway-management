package Models;
public class Sleeper {
    private int sleeperId;
    private String sleeperNo;
    private String sleeperType;
    private int sleeperRow;
    private int length;
    private int posRow;
    private Compartment compartment; 

    public Sleeper(int sleeperId, String sleeperNo, String sleeperType, int sleeperRow, int length, int posRow, Compartment compartment) {
        this.sleeperId = sleeperId;
        this.sleeperNo = sleeperNo;
        this.sleeperType = sleeperType;
        this.sleeperRow = sleeperRow;
        this.length = length;
        this.posRow = posRow;
        this.compartment = compartment;
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

    public int getSleeperRow() {
        return sleeperRow;
    }

    public void setSleeperRow(int sleeperRow) {
        this.sleeperRow = sleeperRow;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPosRow() {
        return posRow;
    }

    public void setPosRow(int posRow) {
        this.posRow = posRow;
    }

    public Compartment getCompartment() {
        return compartment;
    }

    public void setCompartment(Compartment compartment) {
        this.compartment = compartment;
    }

	public int getBreadth() {
		return 0;
	}

	@Override
	public String toString() {
		return "Sleeper [sleeperId=" + sleeperId + ", sleeperNo=" + sleeperNo + ", sleeperType=" + sleeperType
				+ ", sleeperRow=" + sleeperRow + ", length=" + length + ", posRow=" + posRow + ", compartment="
				+ compartment + "]";
	}
	
}
