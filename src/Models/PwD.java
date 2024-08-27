package Models;
public class PwD {
    private int pwdId;                  
    private String pwdNo;              
    private String pwdCertificate;     
    private String name;               
    private char gender;               

    public PwD() {
    }

    public PwD(int pwdId, String pwdNo, String pwdCertificate, String name, char gender) {
        this.pwdId = pwdId;
        this.pwdNo = pwdNo;
        this.pwdCertificate = pwdCertificate;
        this.name = name;
        this.gender = gender;
    }

    public int getPwdId() {
        return pwdId;
    }

    public void setPwdId(int pwdId) {
        this.pwdId = pwdId;
    }

    public String getPwdNo() {
        return pwdNo;
    }

    public void setPwdNo(String pwdNo) {
        this.pwdNo = pwdNo;
    }

    public String getPwdCertificate() {
        return pwdCertificate;
    }

    public void setPwdCertificate(String pwdCertificate) {
        this.pwdCertificate = pwdCertificate;
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
        return "PwD{" +
                "pwdId=" + pwdId +
                ", pwdNo='" + pwdNo + '\'' +
                ", pwdCertificate='" + pwdCertificate + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                '}';
    }
}
