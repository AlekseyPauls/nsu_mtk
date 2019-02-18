public class FiniteStateMachineException extends Exception {
    private final String message;

    FiniteStateMachineException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
