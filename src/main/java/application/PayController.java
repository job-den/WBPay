package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}