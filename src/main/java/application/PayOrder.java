package application;

public class PayOrder {
/*
    {
"documentNumber": "654321",
"date": "2021-08-03T12:30+03:00[Europe/Moscow]",
"amount": 50000,
"recipientName": "ООО Замечательная компания",
"inn": "987654321987",
"kpp": "654321654",
"bankAcnt": "99998888777766665555",
"bankBik": "76543277778",
"accountNumber": "55554444333322221111",
"paymentPurpose": "оплата товара",
"executionOrder": 5,
"taxPayerStatus": "09",
"kbk": "33335555666677778888",
"oktmo": "33335555",
"taxEvidence": "ТП",
"taxPeriod": "ГД.00.2019",
"uin": "0",
"taxDocNumber": "0",
"taxDocDate": "0",
"revenueTypeCode": "2",
"collectionAmountNumber": 987654321,
"recipientCorrAccountNumber": "66668888000066667777"
    }
*/

private String documentNumber;
private String date;
private String amount;
private String recipientName;
private String inn;
private String kpp;
private String bankAcnt;
private String bankBik;
private String accountNumber;
private String paymentPurpose;
private String executionOrder;
private String taxPayerStatus;
private String kbk;
private String oktmo;
private String taxEvidence;
private String taxPeriod;
private String uin;
private String taxDocNumber;
private String taxDocDate;
private String revenueTypeCode;
private String collectionAmountNumber;
private String recipientCorrAccountNumber;

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public String getBankAcnt() {
        return bankAcnt;
    }

    public void setBankAcnt(String bankAcnt) {
        this.bankAcnt = bankAcnt;
    }

    public String getBankBik() {
        return bankBik;
    }

    public void setBankBik(String bankBik) {
        this.bankBik = bankBik;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPaymentPurpose() {
        return paymentPurpose;
    }

    public void setPaymentPurpose(String paymentPurpose) {
        this.paymentPurpose = paymentPurpose;
    }

    public String getExecutionOrder() {
        return executionOrder;
    }

    public void setExecutionOrder(String executionOrder) {
        this.executionOrder = executionOrder;
    }

    public String getTaxPayerStatus() {
        return taxPayerStatus;
    }

    public void setTaxPayerStatus(String taxPayerStatus) {
        this.taxPayerStatus = taxPayerStatus;
    }

    public String getKbk() {
        return kbk;
    }

    public void setKbk(String kbk) {
        this.kbk = kbk;
    }

    public String getOktmo() {
        return oktmo;
    }

    public void setOktmo(String oktmo) {
        this.oktmo = oktmo;
    }

    public String getTaxEvidence() {
        return taxEvidence;
    }

    public void setTaxEvidence(String taxEvidence) {
        this.taxEvidence = taxEvidence;
    }

    public String getTaxPeriod() {
        return taxPeriod;
    }

    public void setTaxPeriod(String taxPeriod) {
        this.taxPeriod = taxPeriod;
    }

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }

    public String getTaxDocNumber() {
        return taxDocNumber;
    }

    public void setTaxDocNumber(String taxDocNumber) {
        this.taxDocNumber = taxDocNumber;
    }

    public String getTaxDocDate() {
        return taxDocDate;
    }

    public void setTaxDocDate(String taxDocDate) {
        this.taxDocDate = taxDocDate;
    }

    public String getRevenueTypeCode() {
        return revenueTypeCode;
    }

    public void setRevenueTypeCode(String revenueTypeCode) {
        this.revenueTypeCode = revenueTypeCode;
    }

    public String getCollectionAmountNumber() {
        return collectionAmountNumber;
    }

    public void setCollectionAmountNumber(String collectionAmountNumber) {
        this.collectionAmountNumber = collectionAmountNumber;
    }

    public String getRecipientCorrAccountNumber() {
        return recipientCorrAccountNumber;
    }

    public void setRecipientCorrAccountNumber(String recipientCorrAccountNumber) {
        this.recipientCorrAccountNumber = recipientCorrAccountNumber;
    }
}
