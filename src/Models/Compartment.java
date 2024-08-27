package Models;

public class Compartment {
    private int compartmentId;
    private Train train; 
    private String compartmentNo;
    private String compartmentType;
    private int compartmentLength;
    private int compartmentBreadth;
    private boolean ac;

    public Compartment(int compartmentId, Train train, String compartmentNo, String compartmentType, 
                        int compartmentLength, int compartmentBreadth, boolean ac) {
        this.compartmentId = compartmentId;
        this.train = train;
        this.compartmentNo = compartmentNo;
        this.compartmentType = compartmentType;
        this.compartmentLength = compartmentLength;
        this.compartmentBreadth = compartmentBreadth;
        this.ac = ac;
    }

    public int getCompartmentId() {
        return compartmentId;
    }

    public void setCompartmentId(int compartmentId) {
        this.compartmentId = compartmentId;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public String getCompartmentNo() {
        return compartmentNo;
    }

    public void setCompartmentNo(String compartmentNo) {
        this.compartmentNo = compartmentNo;
    }

    public String getCompartmentType() {
        return compartmentType;
    }

    public void setCompartmentType(String compartmentType) {
        this.compartmentType = compartmentType;
    }

    public int getCompartmentLength() {
        return compartmentLength;
    }

    public void setCompartmentLength(int compartmentLength) {
        this.compartmentLength = compartmentLength;
    }

    public int getCompartmentBreadth() {
        return compartmentBreadth;
    }

    public void setCompartmentBreadth(int compartmentBreadth) {
        this.compartmentBreadth = compartmentBreadth;
    }

    public boolean isAc() {
        return ac;
    }

    public void setAc(boolean ac) {
        this.ac = ac;
    }

	@Override
	public String toString() {
		return "Compartment [compartmentId=" + compartmentId + ", train=" + train + ", compartmentNo=" + compartmentNo
				+ ", compartmentType=" + compartmentType + ", compartmentLength=" + compartmentLength
				+ ", compartmentBreadth=" + compartmentBreadth + ", ac=" + ac + "]";
	}

}
