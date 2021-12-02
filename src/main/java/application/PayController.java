package application;

import entity.PayOrder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.PayService;

import java.io.IOException;

@RestController
public class PayController {

    @GetMapping("/test")
    public String pay(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) throws IOException {
        model.addAttribute("name", name);
        return "test";
    }

    @PostMapping(value = "/payOrder")
    public ResponseEntity<?> create(@RequestBody PayOrder payOrder) {
        PayService.pay(payOrder);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}