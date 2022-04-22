package application;

public class PayError {

    Integer errorId;
    String message;

    public PayError(Integer errorId, String message) {
        this.errorId = errorId;
        this.message = message;
    }
}
