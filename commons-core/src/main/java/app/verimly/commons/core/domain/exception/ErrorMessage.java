package app.verimly.commons.core.domain.exception;

public record ErrorMessage(String code, String defaultMessage) {

    public ErrorMessage {
        Assert.notNull(code, "ErrorMessage code cannot be null!");
        if(defaultMessage == null)
            defaultMessage = "";

    }

    public static ErrorMessage of(String code, String defaultMessage) {
        return new ErrorMessage(code, defaultMessage);
    }

    public static ErrorMessage unknown() {
        return new ErrorMessage("unknown", "unknown error.");
    }
}
