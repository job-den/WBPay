package application.security;

import application.Recipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class SecureController {

    @Autowired
    TokenService tokenService;

    @PostMapping(value = "/hmac",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> hmac(HttpServletRequest request) {
        String body = null;
        String generateToken = null;
        try {
            /* RequestWrapper requestWrapper = new RequestWrapper(request);
            body = requestWrapper.getBody();*/
            //body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            generateToken = tokenService.generateToken("hmac:#" + request.hashCode());
        } catch (Exception e) {
            new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(generateToken,HttpStatus.CREATED);
    }

}
