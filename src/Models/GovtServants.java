package Models;
public class GovtServants {
    private int servantsId;          
    private String govtId;           
    private String govtRole;         
    private String name;           
    private char gender;           

    public GovtServants() {
    }

    public GovtServants(int servantsId, String govtId, String govtRole, String name, char gender) {
        this.servantsId = servantsId;
        this.govtId = govtId;
        this.govtRole = govtRole;
        this.name = name;
        this.gender = gender;
    }

    public int getServantsId() {
        return servantsId;
    }

    public void setServantsId(int servantsId) {
        this.servantsId = servantsId;
    }

    public String getGovtId() {
        return govtId;
    }

    public void setGovtId(String govtId) {
        this.govtId = govtId;
    }

    public String getGovtRole() {
        return govtRole;
    }

    public void setGovtRole(String govtRole) {
        this.govtRole = govtRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "GovtServants{" +
                "servantsId=" + servantsId +
                ", govtId='" + govtId + '\'' +
                ", govtRole='" + govtRole + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                '}';
    }
}
