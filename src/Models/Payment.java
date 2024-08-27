package Models;

import java.sql.Timestamp;

public class Payment {
    @Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", bookingId=" + bookingId + ", payAmt=" + payAmt + ", paidDate="
				+ paidDate + "]";
	}

	private int paymentId;
    private int bookingId;
    private double payAmt;
    private Timestamp paidDate;

    // Constructors
    public Payment() {}

    public Payment(int paymentId, int bookingId, double payAmt, Timestamp paidDate) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.payAmt = payAmt;
        this.paidDate = paidDate;
    }

    // Getters and Setters
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public double getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(double payAmt) {
        this.payAmt = payAmt;
    }

    public Timestamp getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Timestamp paidDate) {
        this.paidDate = paidDate;
    }
}
