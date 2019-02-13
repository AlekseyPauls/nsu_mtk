public class ParserException extends Exception {
    private final String message;

    ParserException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
