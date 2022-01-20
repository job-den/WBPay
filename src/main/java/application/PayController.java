package application;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.lang.Nullable;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
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

    @GetMapping(value = "/payToBaseStub")
    public String payToBaseStub() {
        service.payToBaseStub();
        return "pay OK";
    }

    @RequestMapping(value ="/payState",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity payState(@RequestParam(name="id") String requestID) throws Exception {
        List formList = new ArrayList();
        try {
            /*
            return jdbcTemplate.queryForList("select p.RequestID as RequestID\n" +
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
                    " where p.RequestId = '8f6633d7-3d6c-48cf-8327-4379a92b45a2'");
                    */

            PreparedStatementSetter preparedStatement = new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, requestID);
                }
            };

            List<Map<String, Object>> queryList = jdbcTemplate.queryForList("select id,documentNumber,inDateTime " +
                    "from dtr_sys_pwb_payment d where SPID =?", preparedStatement);

            for (Map row : queryList) {
                PayOrder order = new PayOrder();
                order.setDocumentNumber((String) row.get("documentNumber"));
                order.setId((String) row.get("id"));
                //order.setID(((Integer) row.get("ID")).longValue()); если будет число
                order.setInDateTime(((Timestamp) row.get("inDateTime")).toLocalDateTime());
                formList.add(order);
            }
        } catch (Exception e) {
            ResponseEntity<Object> responseEntity = new ResponseEntity<>(formList,HttpStatus.METHOD_FAILURE);
            return  responseEntity;
        }

/*
        PayOrder order1 = new PayOrder();
        order1.setDocumentNumber("1");
        order1.setId("id1");
        formList.add(order1);

        PayOrder order2 = new PayOrder();
        order2.setDocumentNumber("2");
        order2.setId("id2");
        formList.add(order2);
*/

        ResponseEntity<Object> responseEntity = new ResponseEntity<>(formList,HttpStatus.OK);
        return  responseEntity;
    }

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

    }

 }