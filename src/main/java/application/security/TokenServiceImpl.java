package application.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${rest.auth.secret}")
    private String secretKey;

    @Override
    public boolean validateToken(String token, String message) {
        return generateToken(message).equals(token);
    }

    @Override
    public String generateToken(String message) {
        return HMACUtil.hmacBC(secretKey, message);
    }
}