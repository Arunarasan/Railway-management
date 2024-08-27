package Models;

public class Seat {
    @Override
	public String toString() {
		return "Seat [seatId=" + seatId + ", seatNo=" + seatNo + ", compartment=" + compartment + ", seatType="
				+ seatType + ", length=" + length + ", posRow=" + posRow + ", posColumn=" + posColumn + "]";
	}

	private int seatId;
    private String seatNo;
    private Compartment compartment;
    private String seatType;
    private int length;
    private int posRow;
    private int posColumn;

    public Seat(int seatId, String seatNo, Compartment compartment, String seatType, 
                int length, int posRow, int posColumn) {
        this.seatId = seatId;
        this.seatNo = seatNo;
        this.compartment = compartment;
        this.seatType = seatType;
        this.length = length;
        this.posRow = posRow;
        this.posColumn = posColumn;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public Compartment getCompartment() {
        return compartment;
    }

    public void setCompartment(Compartment compartment) {
        this.compartment = compartment;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
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

    public int getPosColumn() {
        return posColumn;
    }

    public void setPosColumn(int posColumn) {
        this.posColumn = posColumn;
    }
}
