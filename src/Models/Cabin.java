package Models;
public class Cabin {
    @Override
	public String toString() {
		return "Cabin [cabinId=" + cabinId + ", cabinNo=" + cabinNo + ", length=" + length + ", breadth=" + breadth
				+ ", cabinRow=" + cabinRow + ", compartment=" + compartment + "]";
	}

	private int cabinId;
    private String cabinNo;
    private int length;
    private int breadth;
    private int cabinRow;
    private Compartment compartment; 

    public Cabin(int cabinId, String cabinNo, int length, int breadth, int cabinRow, Compartment compartment) {
        this.cabinId = cabinId;
        this.cabinNo = cabinNo;
        this.length = length;
        this.breadth = breadth;
        this.cabinRow = cabinRow;
        this.compartment = compartment;
    }

    public int getCabinId() {
        return cabinId;
    }

    public void setCabinId(int cabinId) {
        this.cabinId = cabinId;
    }

    public String getCabinNo() {
        return cabinNo;
    }

    public void setCabinNo(String cabinNo) {
        this.cabinNo = cabinNo;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getBreadth() {
        return breadth;
    }

    public void setBreadth(int breadth) {
        this.breadth = breadth;
    }

    public int getCabinRow() {
        return cabinRow;
    }

    public void setCabinRow(int cabinRow) {
        this.cabinRow = cabinRow;
    }

    public Compartment getCompartment() {
        return compartment;
    }

    public void setCompartment(Compartment compartment) {
        this.compartment = compartment;
    }
}
