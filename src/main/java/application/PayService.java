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

@Component
public class PayService {

    private static final String INSERT_INTO_PWB_PAYMENT ="INSERT INTO dtr_sys_pwb_payment ("+
            "SPID,ID,INDATETIME,DOCUMENTNUMBER,DATE,AMOUNT,RECIPIENTNAME,INN,KPP,BANKACNT,BANKBIK,ACCOUNTNUMBER,"+
            "PAYMENTPURPOSE,EXECUTIONORDER,TAXPAYERSTATUS,KBK,OKTMO,TAXEVIDENCE,TAXPERIOD,UIN,TAXDOCNUMBER,"+
            "TAXDOCDATE,REVENUETYPECODE,COLLECTIONAMOUNTNUMBER,RECIPIENTCORRACCOUNTNUMBER) "+
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" ;

    private static final String INSERT_INTO_PWB_PAYMENT_SMAL ="INSERT INTO dtr_sys_pwb_payment ("+
            "SPID,ID,INDATETIME,DOCUMENTNUMBER,\"date\",AMOUNT) "+
            "VALUES (?, ?, ?, ?, ?, ?)" ;

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
        private static final String INSERT_INTO_PWB_PAYMENT ="INSERT INTO dtr_sys_pwb_payment ("+
                "SPID,ID,INDATETIME,DOCUMENTNUMBER,DATE,AMOUNT,RECIPIENTNAME,INN,KPP,BANKACNT,BANKBIK,ACCOUNTNUMBER,"+
                "PAYMENTPURPOSE,EXECUTIONORDER,TAXPAYERSTATUS,KBK,OKTMO,TAXEVIDENCE,TAXPERIOD,UIN,TAXDOCNUMBER,"+
                "TAXDOCDATE,REVENUETYPECODE,COLLECTIONAMOUNTNUMBER,RECIPIENTCORRACCOUNTNUMBER) "+
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" ;
*/
        try {
            jdbcTemplate.update(INSERT_INTO_PWB_PAYMENT_SMAL, new PreparedStatementSetter() {
            //jdbcTemplate.update(INSERT_INTO_PWB_PAYMENT, new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, payOrder.getDocumentNumber());
                        ps.setString(2, payOrder.getDocumentNumber());
                        ps.setTimestamp (3, Timestamp.valueOf(payOrder.getDate()));
                        ps.setString(4,payOrder.getDocumentNumber());
                        ps.setString(5,payOrder.getDate());
                        ps.setBigDecimal(6, new BigDecimal(payOrder.getAmount()));
                    }
                });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void payToBaseStub() {
/*
        private static final String INSERT_INTO_PWB_PAYMENT ="INSERT INTO dtr_sys_pwb_payment ("+
                "SPID,ID,INDATETIME,DOCUMENTNUMBER,DATE,AMOUNT,RECIPIENTNAME,INN,KPP,BANKACNT,BANKBIK,ACCOUNTNUMBER,"+
                "PAYMENTPURPOSE,EXECUTIONORDER,TAXPAYERSTATUS,KBK,OKTMO,TAXEVIDENCE,TAXPERIOD,UIN,TAXDOCNUMBER,"+
                "TAXDOCDATE,REVENUETYPECODE,COLLECTIONAMOUNTNUMBER,RECIPIENTCORRACCOUNTNUMBER) "+
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" ;
*/

            jdbcTemplate.update(INSERT_INTO_PWB_PAYMENT_SMAL, new PreparedStatementSetter() {
                //jdbcTemplate.update(INSERT_INTO_PWB_PAYMENT, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, "123");
                    ps.setString(2, "123");
                    ps.setTimestamp (3, Timestamp.valueOf("2021-10-11 00:00:00"));
                    ps.setString(4,"1111");
                    ps.setString(5,"2021-10-11");
                    ps.setLong(6,new Long("1000"));
                }
            });
        }
}
