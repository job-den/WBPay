package application;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class State {
/*
{
        "batchId": "c0f89c7b-353d-4cca-b7c3-f64bd4084536",
        "paymentIds": ["18d32b6d-7c78-40f3-a87a-574f238db8e7","29a32b6d-7c78-40f3-a87a-231f238db8zu"]
}
*/

    private String RequestId;
    private Timestamp inDateTime;
    private String batchId;
    private String paymentId;

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String RequestId) {
        this.RequestId = RequestId;
    }

    public Timestamp getInDateTime() {
        return inDateTime;
    }

    public void setInDateTime(Timestamp inDateTime) {
        this.inDateTime = inDateTime;
    }

    public String getBatchId() { return batchId; }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getPaymentId(){
        return paymentId;
    }

    public void setPaymentId(String paymentId){
        this.paymentId = paymentId;
    }

}
