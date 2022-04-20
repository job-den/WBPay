package application.security;

public interface TokenService {

    boolean validateToken(String token,String message);

    String generateToken(String message);

}