package application;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PayOrdState {

private String RequestID;
private String PaymentID;
private String documentNumber;
private BigDecimal amount;
private String Confirmed;
private BigDecimal DealTransactID;
private String PayStatus;
private PayError Error;

    public String getRequestID() {
        return RequestID;
    }

    public void setRequestID(String RequestID) {
        this.RequestID = RequestID;
    }

    public String getPaymentID() {
        return PaymentID;
    }

    public void setPaymentID(String PaymentID) {
        this.PaymentID = PaymentID;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getConfirmed() {
        return Confirmed;
    }

    public void setConfirmed(String Confirmed) {
        this.Confirmed = Confirmed;
    }

    public BigDecimal getDealTransactID() {
        return DealTransactID;
    }

    public void setDealTransactID(BigDecimal DealTransactID) {
        this.DealTransactID = DealTransactID;
    }

    public String getPayStatus() {
        return PayStatus;
    }

    public void setPayStatus(String PayStatus) {
        this.PayStatus = PayStatus;
    }

    public PayError getError() {
        return Error;
    }

    public void setError(PayError Error) {
        this.Error = Error;
    }



}
