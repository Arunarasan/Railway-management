package Models;

public class Path {
    private int pathId;
    private int pathLength;
    private int pathBreadth;
    private int pathRow;
    private int pathCol;
    private Compartment compartment; 
    
    @Override
	public String toString() {
		return "Path [pathId=" + pathId + ", pathLength=" + pathLength + ", pathBreadth=" + pathBreadth + ", pathRow="
				+ pathRow + ", pathCol=" + pathCol + ", compartment=" + compartment + "]";
	}

	public Path(int pathId, int pathLength, int pathBreadth, int pathRow, int pathCol, Compartment compartment) {
        this.pathId = pathId;
        this.pathLength = pathLength;
        this.pathBreadth = pathBreadth;
        this.pathRow = pathRow;
        this.pathCol = pathCol;
        this.compartment = compartment;
    }

    public int getPathId() {
        return pathId;
    }

    public void setPathId(int pathId) {
        this.pathId = pathId;
    }

    public int getPathLength() {
        return pathLength;
    }

    public void setPathLength(int pathLength) {
        this.pathLength = pathLength;
    }

    public int getPathBreadth() {
        return pathBreadth;
    }

    public void setPathBreadth(int pathBreadth) {
        this.pathBreadth = pathBreadth;
    }

    public int getPathRow() {
        return pathRow;
    }

    public void setPathRow(int pathRow) {
        this.pathRow = pathRow;
    }

    public int getPathCol() {
        return pathCol;
    }

    public void setPathCol(int pathCol) {
        this.pathCol = pathCol;
    }

    public Compartment getCompartment() {
        return compartment;
    }

    public void setCompartment(Compartment compartment) {
        this.compartment = compartment;
    }
}
