package application;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@Component
public class PayService {

    private static final String INSERT_INTO_PWB_PAYMENT ="INSERT INTO dbo.pwb_payment ("+
            "RequestId,INDATETIME,PAYMENTID,SENDERACCOUNT,DOCUMENTNUMBER,\"date\",AMOUNT,RECIPIENTNAME,INN,KPP,BANKACNT,BANKBIK,ACCOUNTNUMBER,"+
            "PAYMENTPURPOSE,EXECUTIONORDER,TAXPAYERSTATUS,KBK,OKTMO,TAXEVIDENCE,TAXPERIOD,UIN,TAXDOCNUMBER,"+
            "TAXDOCDATE,REVENUETYPECODE,COLLECTIONAMOUNTNUMBER,RECIPIENTCORRACCOUNTNUMBER) "+
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" ;

    private static final String INSERT_INTO_PWB_PAYMENT_SMAL ="INSERT INTO dbo.pwb_payment ("+
            "RequestId,INDATETIME,DOCUMENTNUMBER,\"date\",AMOUNT) "+
            "VALUES (?, ?, ?, ?, ?)" ;

    @Value("${file_path}")
    private String file_path;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void payToFile(PayOrder payOrder) {
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
            Files.write(Paths.get(file_path + payOrder.getDocumentNumber()+".txt"), sampleObject.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void payToBase(PayOrder payOrder) {
/*
        private static final String INSERT_INTO_PWB_PAYMENT ="INSERT INTO dbo.pwb_payment ("+
                "RequestId,INDATETIME,DOCUMENTNUMBER,DATE,AMOUNT,RECIPIENTNAME,INN,KPP,BANKACNT,BANKBIK,ACCOUNTNUMBER,"+
                "PAYMENTPURPOSE,EXECUTIONORDER,TAXPAYERSTATUS,KBK,OKTMO,TAXEVIDENCE,TAXPERIOD,UIN,TAXDOCNUMBER,"+
                "TAXDOCDATE,REVENUETYPECODE,COLLECTIONAMOUNTNUMBER,RECIPIENTCORRACCOUNTNUMBER) "+
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" ;
*/
        String uuid = UUID.randomUUID().toString();
        saveToDB(payOrder, uuid);
    }

    public void manyPayToBase(PayOrder[] payOrders) {
        String uuid = UUID.randomUUID().toString();
        for(PayOrder payOrder: payOrders){
            saveToDB(payOrder, uuid);
        }
        jdbcTemplate.update("exec dbo.WB_PaymentRequest @pRequestID = ?, @ReqName = ?", new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, uuid);
                ps.setString(2, "WB_pay");
            }
        });
    }

    public void newPaysToBase(Recipient[] recipients, PayBatch payBatch, Payer payer) {
        String uuid = payBatch.getBatchId();
        String senderAccount = payer.getSenderAccount();
        for(Recipient recipient: recipients){
            newsaveToDB(recipient, uuid, senderAccount);
        }
        jdbcTemplate.update("exec dbo.WB_PaymentRequest @pRequestID = ?, @ReqName = ?", new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, uuid);
                ps.setString(2, "WB_pay");
            }
        });
    }

    private void saveToDB(PayOrder payOrder, String uuid) {
        try {
            jdbcTemplate.update(INSERT_INTO_PWB_PAYMENT, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, uuid); /*ps.setBigDecimal(2, new BigDecimal(UUID.randomUUID().toString()));*/
                    ps.setTimestamp (2, Timestamp.valueOf(LocalDateTime.now())); /*getTimeStamp(payOrder.getDate())); */
                    ps.setString(3,"");
                    ps.setString(4,"");
                    ps.setString(5, payOrder.getDocumentNumber());
                    ps.setString(6,payOrder.getDate());
                    ps.setBigDecimal(7, new BigDecimal(payOrder.getAmount()));
                    ps.setString(8,payOrder.getRecipientName());
                    ps.setString(9,payOrder.getInn());
                    ps.setString(10,payOrder.getKpp());
                    ps.setString(11,payOrder.getBankAcnt());
                    ps.setString(12,payOrder.getBankBik());
                    ps.setString(13,payOrder.getAccountNumber());
                    ps.setString(14,payOrder.getPaymentPurpose());
                    ps.setString(15,payOrder.getExecutionOrder());
                    ps.setString(16,payOrder.getTaxPayerStatus());
                    ps.setString(17,payOrder.getKbk());
                    ps.setString(18,payOrder.getOktmo());
                    ps.setString(19,payOrder.getTaxEvidence());
                    ps.setString(20,payOrder.getTaxPeriod());
                    ps.setString(21,payOrder.getUin());
                    ps.setString(22,payOrder.getTaxDocNumber());
                    ps.setString(23,payOrder.getTaxDocDate());
                    ps.setString(24,payOrder.getRevenueTypeCode());
                    ps.setString(25,payOrder.getCollectionAmountNumber());
                    ps.setString(26,payOrder.getRecipientCorrAccountNumber());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void newsaveToDB(Recipient recipient, String uuid, String senderAccount) {
        try {
            jdbcTemplate.update(INSERT_INTO_PWB_PAYMENT, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, uuid); /*ps.setBigDecimal(2, new BigDecimal(UUID.randomUUID().toString()));*/
                    ps.setTimestamp (2, Timestamp.valueOf(LocalDateTime.now())); /*getTimeStamp(payOrder.getDate())); */
                    ps.setString(3,recipient.getPaymentId());
                    ps.setString(4,senderAccount);
                    ps.setString(5, recipient.getDocNumber());
                    ps.setString(6, Timestamp.valueOf(LocalDateTime.now()).toString());
                    ps.setBigDecimal(7, new BigDecimal(recipient.getDocAmount()));
                    ps.setString(8,recipient.getRecipientName());
                    ps.setString(9,recipient.getRecipientInn());
                    ps.setString(10,recipient.getRecipientKpp());
                    ps.setString(11,recipient.getRecipientCorrespondentAccount());
                    ps.setString(12,recipient.getRecipientBankBik());
                    ps.setString(13,recipient.getRecipientAccount());
                    ps.setString(14,recipient.getPaymentDescription());
                    ps.setString(15,recipient.getExecutionOrder());
                    ps.setString(16,"");
                    ps.setString(17,"");
                    ps.setString(18,"");
                    ps.setString(19,"");
                    ps.setString(20,"");
                    ps.setString(21,"");
                    ps.setString(22,"");
                    ps.setString(23,"");
                    ps.setString(24,"");
                    ps.setString(25,"");
                    ps.setString(26,recipient.getRecipientCorrespondentAccount());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Timestamp getTimeStamp(String date) {
        ZonedDateTime parse = ZonedDateTime.parse(date);
        return Timestamp.valueOf(parse.toLocalDateTime());
    }

    public void payToBaseStub() {

        jdbcTemplate.update(INSERT_INTO_PWB_PAYMENT_SMAL, new PreparedStatementSetter() {
            //jdbcTemplate.update(INSERT_INTO_PWB_PAYMENT, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, UUID.randomUUID().toString());
                /*  ps.setTimestamp (2, Timestamp.valueOf(LocalDateTime.now()));*/
                ps.setTimestamp (2, getTimeStamp("2021-12-21T12:30+03:00"));
                ps.setString(3,"1111");
                ps.setString(4,"2021-10-11");
                ps.setLong(5,new Long("1000"));
            }
        });
    }
}
