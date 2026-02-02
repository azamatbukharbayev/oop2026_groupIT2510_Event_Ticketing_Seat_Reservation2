package Exceptions;

public class InvalidTicketCode extends RuntimeException {
    public InvalidTicketCode(String code) {
        super("Invalid ticket code: " + code);
    }
}
