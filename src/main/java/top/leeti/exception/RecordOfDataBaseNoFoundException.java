package top.leeti.exception;

public class RecordOfDataBaseNoFoundException extends RuntimeException {

    private final String id;
    private final String uriOrMethod;

    public String getUriOrMethod() {
        return uriOrMethod;
    }

    public String getId() {
        return id;
    }

    public RecordOfDataBaseNoFoundException(String message, String id, String uriOrMethod) {
        super(message);
        this.id = id;
        this.uriOrMethod = uriOrMethod;
    }
}
