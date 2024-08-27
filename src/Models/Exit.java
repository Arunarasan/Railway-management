package Models;

public class Exit {
    private int exitId;
    private int exitRow;
    private int exitLength;
    private Compartment compartment; 

    @Override
	public String toString() {
		return "Exit [exitId=" + exitId + ", exitRow=" + exitRow + ", exitLength=" + exitLength + ", compartment="
				+ compartment + "]";
	}

	public Exit(int exitId, int exitRow, int exitLength, Compartment compartment) {
        this.exitId = exitId;
        this.exitRow = exitRow;
        this.exitLength = exitLength;
        this.compartment = compartment;
    }

    public int getExitId() {
        return exitId;
    }

    public void setExitId(int exitId) {
        this.exitId = exitId;
    }

    public int getExitRow() {
        return exitRow;
    }

    public void setExitRow(int exitRow) {
        this.exitRow = exitRow;
    }

    public int getExitLength() {
        return exitLength;
    }

    public void setExitLength(int exitLength) {
        this.exitLength = exitLength;
    }

    public Compartment getCompartment() {
        return compartment;
    }

    public void setCompartment(Compartment compartment) {
        this.compartment = compartment;
    }
}
