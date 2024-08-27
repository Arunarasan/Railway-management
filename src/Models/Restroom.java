package Models;

public class Restroom {
    private int toiletId;
    private int length;
    private int breadth;
    private int toiletRow;
    private Compartment compartment; 

    public Restroom(int toiletId, int length, int breadth, int toiletRow, Compartment compartment) {
        this.toiletId = toiletId;
        this.length = length;
        this.breadth = breadth;
        this.toiletRow = toiletRow;
        this.compartment = compartment;
    }

    @Override
	public String toString() {
		return "Restroom [toiletId=" + toiletId + ", length=" + length + ", breadth=" + breadth + ", toiletRow="
				+ toiletRow + ", compartment=" + compartment + "]";
	}

	public int getToiletId() {
        return toiletId;
    }

    public void setToiletId(int toiletId) {
        this.toiletId = toiletId;
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

    public int getToiletRow() {
        return toiletRow;
    }

    public void setToiletRow(int toiletRow) {
        this.toiletRow = toiletRow;
    }

    public Compartment getCompartment() {
        return compartment;
    }

    public void setCompartment(Compartment compartment) {
        this.compartment = compartment;
    }
}
