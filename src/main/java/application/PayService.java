package application;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.*;

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

    private static final String INSERT_INTO_WB_PAYMENTSTATE ="INSERT INTO dbo.wb_paymentstate ("+
            "RequestId,INDATETIME,BATCHID,PAYMENTID) "+
            "VALUES (?, ?, ?, ?)" ;

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

    public void newPaysToBase(Map<String, Object> filterMap) {
        String uuid = (filterMap).get("batchId").toString();
        Map from = (Map) (filterMap).get("from");

        String senderAccount = from.get("senderAccount").toString();


          List <Map<String,String>> toObject = (List<Map<String,String>>) filterMap.get("to");

          List<Recipient> recipients = new ArrayList<>();
          Iterator<Map<String, String>> toObjectLine = toObject.iterator();

        while (toObjectLine.hasNext()) {
        Map<String, String> to = toObjectLine.next();
        Recipient recipient = new Recipient();
        recipient.setPaymentId(to.get("paymentId"));
        recipient.setDocAmount(String.valueOf(to.get("docAmount")));
        recipient.setDocNumber(to.get("docNumber"));
        recipient.setRecipientBankName(to.get("recipientBankName"));
        recipient.setRecipientKpp(to.get("recipientKpp"));
        recipient.setRecipientBankBik(to.get("recipientBankBik"));
        recipient.setRecipientCorrespondentAccount(to.get("recipientCorrespondentAccount"));
        recipient.setRecipientName(to.get("recipientName"));
        recipient.setRecipientAccount(to.get("recipientAccount"));
        recipient.setRecipientInn(to.get("recipientInn"));
        recipient.setPaymentDescription(to.get("paymentDescription"));
        recipient.setExecutionOrder(String.valueOf(to.get("executionOrder")));
        recipients.add(recipient);
    }
        for(Recipient onerecipient: recipients){
        newsaveToDB(onerecipient, uuid, senderAccount);}
        jdbcTemplate.update("exec dbo.WB_PaymentRequest @pRequestID = ?, @ReqName = ?", new PreparedStatementSetter() {
        @Override
        public void setValues(PreparedStatement ps) throws SQLException {
            ps.setString(1, uuid);
            ps.setString(2, "WB_pay");
        }
    });
    }

    public ResponseEntity<Object> paymentStatus(Map<String, Object> filterMap) {
        ResponseEntity<Object> responseEntity;
        String batchId = (filterMap).get("batchId").toString();
        ArrayList paymentIDs = (ArrayList) filterMap.get("paymentIds");
        String uuid = UUID.randomUUID().toString();
        System.out.println("ID="+"["+batchId+paymentIDs+"]") ;

        List<State> states = new ArrayList<>();
        Iterator paymentid = paymentIDs.iterator();
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

        while (paymentid.hasNext()) {
            State state = new State();
            state.setPaymentId((String) paymentid.next());
            state.setBatchId(batchId);
            state.setRequestId(uuid);
            state.setInDateTime(timestamp);
            states.add(state);
        }
        for(State state: states) {
            saveState(state, batchId);
        }
        List resultList = new ArrayList();
        try {

            PreparedStatementSetter preparedStatement = new PreparedStatementSetter() {
                @ Override
                public void setValues(PreparedStatement ps)throws SQLException {

                    ps.setString(1, uuid.toString());
                    //ps.setObject(2, paymentIDs.toArray());

                }
            };
            System.out.println("ID="+"["+uuid+paymentIDs+"]") ;
            List < Map < String, Object >> queryList = jdbcTemplate.queryForList("select p.RequestID as RequestID" +
                    "      ,p.PaymentID as PaymentID" +
                    "      ,p.documentNumber as documentNumber" +
                    "      ,dt.Qty as Qty" +
                    "      ,dt.Confirmed as Confirmed" +
                    "      ,dt.DealTransactID as DealTransactID" +
                    "      ,case when Confirmed = 101 then 'Загружен'" +
                    "            when Confirmed = 1   then 'Исполнен'" +
                    "       else 'Обрабатывается'" +
                    "       end PayStatus  " +
                    "  ,isnull(p.Error,'') as Error " +
                    //   ",'('+char(39)+replace(replace(replace(?,'[',''),']',''),', ',char(39)+','+char(39))+char(39)+')' as Confirmed" +
                    //  ",? as PaymentID" +
                    " from  WB_PaymentState s" +
                    " inner join WB_Payment p on p.PaymentID = s.PaymentID" +
                    " left join tDealTransact dt on dt.DealTransactID = p.DealTransactID" +
                    " where s.RequestId = ?",uuid.toString());
            // " where p.RequestId = ? and p.PaymentID in (select char(39)+replace(replace(replace(?,'[',''),']',''),', ',char(39)+','+char(39))+char(39))", uuid.toString(),String.valueOf(paymentIDs));
            //" where p.RequestId = ? and p.PaymentID in ('18d32b6d-7c78-40f3-a87a-574f238db8e7')", uuid.toString(),String.valueOf(paymentIDs));
            //and p.PaymentID in (select '('+char(39)+replace(replace(replace('?','[',''),']',''),', ',char(39)+','+char(39))+char(39)+')')
            // List resultList = new ArrayList();
            System.out.println("query-list="+"["+queryList+"]") ;
            for (Map row: queryList) {
                System.out.println("row="+"["+row+"]") ;
                PayOrdState ordState = new PayOrdState();
                ordState.setRequestID((String)row.get("RequestID"));
                ordState.setPaymentID((String)row.get("PaymentID"));
                ordState.setDocumentNumber((String)row.get("documentNumber").toString());
                ordState.setAmount((BigDecimal)row.get("Qty"));
                ordState.setConfirmed((String)row.get("Confirmed").toString());
                ordState.setDealTransactID((BigDecimal)row.get("DealTransactID"));
                ordState.setPayStatus((String)row.get("PayStatus"));
                ordState.setError(new PayError((Integer) row.get("errorID"), (String)row.get("message")));
                resultList.add(ordState);
            }
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(e,HttpStatus.METHOD_FAILURE);
        }

        /*{ "paymentsStatusesList":[
                {"paymentId": "18d32b6d-7c78-40f3-a87a-574f238db8e7", "status": "success", "error":null},
                {"paymentId": "29a32b6d-7c78-40f3-a87a-231f238db8zu", "status": "error", "error":
                    {"errorId": 1, "message":"не найден контрагент с указаными реквизитами"}}
            ] }
*/
        JSONObject paymentsStatuses = new JSONObject();
        paymentsStatuses.put("paymentsStatuses",resultList);

        responseEntity = new ResponseEntity<>(paymentsStatuses,HttpStatus.OK);

        return responseEntity;
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

    void newsaveToDB(Recipient onerecipient, String uuid, String senderAccount) {
        try {
            jdbcTemplate.update(INSERT_INTO_PWB_PAYMENT, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, uuid); /*ps.setBigDecimal(2, new BigDecimal(UUID.randomUUID().toString()));*/
                    ps.setTimestamp (2, Timestamp.valueOf(LocalDateTime.now())); /*getTimeStamp(payOrder.getDate())); */
                    ps.setString(3,onerecipient.getPaymentId());
                    ps.setString(4,senderAccount);
                    ps.setString(5, onerecipient.getDocNumber());
                    ps.setString(6, Timestamp.valueOf(LocalDateTime.now()).toString());
                    ps.setBigDecimal(7, new BigDecimal(onerecipient.getDocAmount()));
                    ps.setString(8,onerecipient.getRecipientName());
                    ps.setString(9,onerecipient.getRecipientInn());
                    ps.setString(10,onerecipient.getRecipientKpp());
                    ps.setString(11,onerecipient.getRecipientCorrespondentAccount());
                    ps.setString(12,onerecipient.getRecipientBankBik());
                    ps.setString(13,onerecipient.getRecipientAccount());
                    ps.setString(14,onerecipient.getPaymentDescription());
                    ps.setString(15,onerecipient.getExecutionOrder());
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
                    ps.setString(26,onerecipient.getRecipientCorrespondentAccount());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void saveState(State state, String batchId) {
        try {
            jdbcTemplate.update(INSERT_INTO_WB_PAYMENTSTATE, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, state.getRequestId());
                    ps.setTimestamp (2,state.getInDateTime());
                    ps.setString(3,state.getBatchId());
                    ps.setString(4,state.getPaymentId());
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
