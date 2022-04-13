package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
public class PayController {

    @Autowired
    PayService service;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostMapping(value = "/payToFile")
    public ResponseEntity<?> payToFile(@RequestBody PayOrder payOrder) {
        service.payToFile(payOrder);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/payToBase")
    public ResponseEntity<?> payToBase(@RequestBody PayOrder payOrder) {
        service.payToBase(payOrder);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/manyPayToBase")
    public ResponseEntity<?> manyPayToBase(@RequestBody PayOrder[] payOrders) {
        service.manyPayToBase(payOrders);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping(value = "/newPaysToBase",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> newPaysToBase(@RequestBody Map<String, Object> filterMap) {
        String batchId = (filterMap).get("batchId").toString();
        Map from = (Map) (filterMap).get("from");

        String senderAccount = from.get("senderAccount").toString();
      /*  String bic = from.get("BIC").toString();*/

        List <Map<String,String>> toObject = (List<Map<String,String>>) filterMap.get("to");

        List <Recipient> recipients = new ArrayList<>();
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

        //service.newPaysToBase(recipients,payBatch,payer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/payToBaseStub")
    public String payToBaseStub() {
        service.payToBaseStub();
        return "pay OK";
    }

    @RequestMapping(value ="/payState",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity payState(@RequestParam(name="id") String requestID) throws Exception {
        List resultList = new ArrayList();
        try {

            PreparedStatementSetter preparedStatement = new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(0, requestID);
                }
            };
           // System.out.println("ID="+"["+requestID+"]") ;
            List<Map<String, Object>> queryList = jdbcTemplate.queryForList("select p.RequestID as RequestID" +
                    "      ,p.documentNumber as documentNumber" +
                    "      ,dt.Qty as Qty" +
                    "      ,dt.Confirmed as Confirmed" +
                    "      ,dt.DealTransactID as DealTransactID" +
                    "      ,case when Confirmed = 101 then 'Загружен'" +
                    "            when Confirmed = 1   then 'Исполнен'" +
                    "       else 'Обрабатывается'" +
                    "       end PayStatus  " +
                    "  ,isnull(p.Error,'') as Error " +
                    " from WB_Payment p " +
                    " left join tDealTransact dt on dt.DealTransactID = p.DealTransactID" +
                    " where p.RequestId = ?", requestID.toString());  //4eeb8b89-8fa2-4b85-b50a-2adc0736beed

           // List resultList = new ArrayList();
            for (Map row : queryList) {

                PayOrdState ordState = new PayOrdState();
                ordState.setRequestID((String) row.get("RequestID"));
                ordState.setDocumentNumber((String) row.get("documentNumber").toString());
                ordState.setAmount((BigDecimal) row.get("Qty"));
                ordState.setConfirmed((String) row.get("Confirmed").toString());
                ordState.setDealTransactID((BigDecimal) row.get("DealTransactID"));
                ordState.setPayStatus((String) row.get("PayStatus"));
                ordState.setError((String) row.get("Error"));

                resultList.add(ordState);
        }

        } catch (Exception e) {
             throw e;
          //  ResponseEntity<Object> responseEntity = new ResponseEntity<>(resultList,HttpStatus.METHOD_FAILURE);
          //  return  responseEntity;
        }

        ResponseEntity<Object> responseEntity = new ResponseEntity<>(resultList,HttpStatus.OK);
        return  responseEntity;
    }


    @RequestMapping(value ="/PayStateTest",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity payStateTest(@RequestParam(name="id") String requestID) throws Exception {
           List<Object> resultList = new ArrayList();
        try {

            PreparedStatementSetter preparedStatement = new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(0, requestID);
                }
            };
            List<Map<String, Object>> queryList = jdbcTemplate.queryForList("select p.RequestID as RequestID\n" +
                    "      ,p.documentNumber as documentNumber\n" +
                    "      ,dt.Qty as Qty\n" +
                    "      ,dt.Confirmed as Confirmed\n" +
                    "      ,dt.DealTransactID as DealTransactID\n" +
                    "      ,case when Confirmed = 101 then 'Загружен'\n" +
                    "            when Confirmed = 1   then 'Исполнен'\n" +
                    "       else 'Обрабатывается'\n" +
                    "       end PayStatus   \n" +
                    "\t  ,p.Error as Error\n" +
                    " from WB_Payment p\n" +
                    "left join tDealTransact dt on dt.DealTransactID = p.DealTransactID\n" +
                    " where p.RequestId = '?'", requestID);


        } catch (Exception e) {
            throw e;

        }
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(resultList,HttpStatus.OK);
        return  responseEntity;
    }
        public List<Object> extractData (ResultSet resultSet) throws SQLException {

            List<Object> resultList = null;
            while (resultSet.next()) {
                resultList = new ArrayList<Object>();

                resultList.add(resultSet.getObject("RequestID"));
                resultList.add(resultSet.getObject("documentNumber"));
                resultList.add(resultSet.getObject("Qty"));
                resultList.add(resultSet.getObject("Confirmed"));
                resultList.add(resultSet.getObject("DealTransactID"));
                resultList.add(resultSet.getObject("PayStatus"));
                resultList.add(resultSet.getObject("Error"));
            }

            return resultList;

        }

/*
    public List<Object> extractData(ResultSet resultSet) throws SQLException {

        List<Object> resultList = null;
        while (resultSet.next()) {
            resultList = new ArrayList<Object>();

            resultList.add(resultSet.getObject("RequestID"));
            resultList.add(resultSet.getObject("documentNumber"));
            resultList.add(resultSet.getObject("Qty"));
            resultList.add(resultSet.getObject("Confirmed"));
            resultList.add(resultSet.getObject("DealTransactID"));
            resultList.add(resultSet.getObject("PayStatus"));
            resultList.add(resultSet.getObject("Error"));
        }

        return resultList;
*/
}