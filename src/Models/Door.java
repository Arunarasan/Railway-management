package Models;

public class Door {
    private int doorId;
    private int doorRow;
    private int doorLength;
    private Compartment compartment; 

    @Override
	public String toString() {
		return "Door [doorId=" + doorId + ", doorRow=" + doorRow + ", doorLength=" + doorLength + ", compartment="
				+ compartment + "]";
	}

	public Door(int doorId, int doorRow, int doorLength, Compartment compartment) {
        this.doorId = doorId;
        this.doorRow = doorRow;
        this.doorLength = doorLength;
        this.compartment = compartment;
    }

    public int getDoorId() {
        return doorId;
    }

    public void setDoorId(int doorId) {
        this.doorId = doorId;
    }

    public int getDoorRow() {
        return doorRow;
    }

    public void setDoorRow(int doorRow) {
        this.doorRow = doorRow;
    }

    public int getDoorLength() {
        return doorLength;
    }

    public void setDoorLength(int doorLength) {
        this.doorLength = doorLength;
    }

    public Compartment getCompartment() {
        return compartment;
    }

    public void setCompartment(Compartment compartment) {
        this.compartment = compartment;
    }
}
