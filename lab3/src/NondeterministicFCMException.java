public class NondeterministicFCMException extends Exception {
    private final String message;

    NondeterministicFCMException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
