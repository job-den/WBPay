package application;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PayBatch {
/*
{
        "batchId": "c0f89c7b-353d-4cca-b7c3-f64bd4084536",
        "from": {
                "senderAccount": "40777777746777127777"
                },
        "to": [{
                "paymentId": "18d32b6d-7c78-40f3-a87a-574f238db8e7",
                "docAmount": 1234.78,
                "docNumber": "123456",//номер документа из банковской выписки
                "recipientBankName": "ПАО БАБАНК",
                "recipientKpp": "776001601",
                "recipientBankBik": "044030611",
                "recipientCorrespondentAccount": "30101111111111111111",
                "recipientName": "Тест ООО",
                "recipientAccount": "40777777777777777777",
                "recipientInn": "2222222222",
                "paymentDescription": "Оплата по договору б/н от 2020-05-29 за Одежда, Белье, Спортивная одежда. Сумма  1234.78. в т.ч. НДС 30.05 руб.",
                "executionOrder": 5 //приоритет выполнения.
        }]
}
*/


private LocalDateTime InDateTime;
private String batchId;
private ArrayList<Payer> payer;
private ArrayList<Recipient> recipient;

    public String getBatchId() { return batchId; }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public LocalDateTime getInDateTime() {
        return InDateTime;
    }

    public void setInDateTime(LocalDateTime inDateTime) {
        InDateTime = inDateTime;
    }

    public ArrayList<Payer> getPayer() { return payer; }

    public void setPayer(ArrayList<Payer> payer) { this.payer = payer; }

    public ArrayList<Recipient> getRecipient() { return recipient; }

    public void setRecipient(ArrayList<Recipient> recipient) { this.recipient = recipient; }

}
