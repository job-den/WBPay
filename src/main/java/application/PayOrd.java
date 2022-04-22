package application;

import java.time.LocalDateTime;

public class PayOrd {
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
private String senderAccount;
private String paymentId;
private String docNumber;
private String docAmount;
private String recipientBankName;
private String recipientKpp;
private String recipientBankBik;
private String recipientCorrespondentAccount;
private String recipientName;
private String recipientAccount;
private String recipientInn;
private String paymentDescription;
private String executionOrder;

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public LocalDateTime getInDateTime() {
        return InDateTime;
    }

    public void setInDateTime(LocalDateTime inDateTime) {
        InDateTime = inDateTime;
    }

    public String getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(String senderAccount) {
        this.senderAccount = senderAccount;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getDocAmount() {
        return docAmount;
    }

    public void setDocAmount(String docAmount) {
        this.docAmount = docAmount;
    }

    public String getRecipientBankName() {
        return recipientBankName;
    }

    public void setRecipientBankName(String recipientBankName) {
        this.recipientBankName = recipientBankName;
    }

    public String getRecipientKpp() {
        return recipientKpp;
    }

    public void setRecipientKpp(String recipientKpp) {
        this.recipientKpp = recipientKpp;
    }

    public String getRecipientBankBik() {
        return recipientBankBik;
    }

    public void setRecipientBankBik(String recipientBankBik) {
        this.recipientBankBik = recipientBankBik;
    }

    public String getRecipientCorrespondentAccount() {
        return recipientCorrespondentAccount;
    }

    public void setRecipientCorrespondentAccount(String recipientCorrespondentAccount) {
        this.recipientCorrespondentAccount = recipientCorrespondentAccount;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientAccount() {
        return recipientAccount;
    }

    public void setRecipientAccount(String recipientAccount) {
        this.recipientAccount = recipientAccount;
    }

    public String getRecipientInn() {
        return recipientInn;
    }

    public void setRecipientInn(String inn) {
        this.recipientInn = recipientInn;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public String getExecutionOrder() {
        return executionOrder;
    }

    public void setExecutionOrder(String executionOrder) {
        this.executionOrder = executionOrder;
    }

}
