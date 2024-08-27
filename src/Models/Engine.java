package Models;

public class Engine {
    private int engineId;
    private String engineNumber;
    private String engineType;
    private String manufacturer;
    private int horsepower;
    private int buildYear;
    private String status;
    
    @Override
	public String toString() {
		return "Engine [engineId=" + engineId + ", engineNumber=" + engineNumber + ", engineType=" + engineType
				+ ", manufacturer=" + manufacturer + ", horsepower=" + horsepower + ", buildYear=" + buildYear
				+ ", status=" + status + "]";
	}

	public Engine(int engineId, String engineNumber, String engineType, String manufacturer, int horsepower, int buildYear, String status) {
        this.engineId = engineId;
        this.engineNumber = engineNumber;
        this.engineType = engineType;
        this.manufacturer = manufacturer;
        this.horsepower = horsepower;
        this.buildYear = buildYear;
        this.status = status;
    }

    public int getEngineId() {
        return engineId;
    }

    public void setEngineId(int engineId) {
        this.engineId = engineId;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    public int getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(int buildYear) {
        this.buildYear = buildYear;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
