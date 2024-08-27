package Models;

import java.sql.Timestamp;

public class Cancel {
    private int cancelId;                
    private Booking booking;            
    private Timestamp cancelDate;       
    private String cancelReason;        
    private double refundAmt;           

    public Cancel() {
    }

    public Cancel(int cancelId, Booking booking, Timestamp cancelDate, String cancelReason, double refundAmt) {
        this.cancelId = cancelId;
        this.booking = booking;
        this.cancelDate = cancelDate;
        this.cancelReason = cancelReason;
        this.refundAmt = refundAmt;
    }

    public int getCancelId() {
        return cancelId;
    }

    public void setCancelId(int cancelId) {
        this.cancelId = cancelId;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Timestamp getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Timestamp cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public double getRefundAmt() {
        return refundAmt;
    }

    public void setRefundAmt(double refundAmt) {
        this.refundAmt = refundAmt;
    }

    @Override
    public String toString() {
        return "Cancel{" +
               "cancelId=" + cancelId +
               ", booking=" + booking +
               ", cancelDate=" + cancelDate +
               ", cancelReason='" + cancelReason + '\'' +
               ", refundAmt=" + refundAmt +
               '}';
    }
}
