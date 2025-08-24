package app.verimly.commons.core.domain.vo;

public class Email extends ValueObject<String> {


    protected Email(String value) {
        super(value);
    }

    public static Email of(String value) {
        if (value == null) {
            return null;
        }
        return new Email(validateAndFormat(value));
    }

    public static String validateAndFormat(String value) {
        assert value != null;
        // TODO: implement validation/formatting if needed
        return value.trim();
    }

    public static Email reconstruct(String value) {
        return new Email(value);
    }


}
