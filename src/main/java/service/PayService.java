package service;

import entity.PayOrder;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PayService {

    public static void pay(PayOrder payOrder) {
        JSONObject sampleObject = new JSONObject();
        sampleObject.put("documentNumber",payOrder.getDocumentNumber());
        sampleObject.put("date",payOrder.getDate());
        sampleObject.put("amount",payOrder.getAmount());
        sampleObject.put("recipientName",payOrder.getRecipientName());
        sampleObject.put("inn",payOrder.getInn());
        sampleObject.put("kpp",payOrder.getKpp());
        sampleObject.put("bankAcnt",payOrder.getBankBik());
        sampleObject.put("bankBik",payOrder.getBankBik());
        sampleObject.put("accountNumber",payOrder.getAccountNumber());
        sampleObject.put("paymentPurpose",payOrder.getPaymentPurpose());
        sampleObject.put("executionOrder",payOrder.getExecutionOrder());
        sampleObject.put("taxPayerStatus",payOrder.getTaxPayerStatus());
        sampleObject.put("kbk",payOrder.getKbk());
        sampleObject.put("oktmo",payOrder.getOktmo());
        sampleObject.put("taxEvidence",payOrder.getTaxEvidence());
        sampleObject.put("taxPeriod",payOrder.getTaxPeriod());
        sampleObject.put("uin",payOrder.getUin());
        sampleObject.put("taxDocNumber",payOrder.getTaxDocNumber());
        sampleObject.put("taxDocDate",payOrder.getTaxDocDate());
        sampleObject.put("revenueTypeCode",payOrder.getRevenueTypeCode());
        sampleObject.put("collectionAmountNumber",payOrder.getCollectionAmountNumber());
        sampleObject.put("recipientCorrAccountNumber",payOrder.getRecipientCorrAccountNumber());

        try {
            Files.write(Paths.get("c://Temp/"+payOrder.getDocumentNumber()+".txt"), sampleObject.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
