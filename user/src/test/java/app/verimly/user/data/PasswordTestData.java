package app.verimly.user.data;

import app.verimly.user.domain.vo.Password;

public class PasswordTestData {

    private static final PasswordTestData INSTANCE = new PasswordTestData();
    public static PasswordTestData getInstance(){
        return INSTANCE;
    }

    public static final String RAW = "validPassword";
    public static final String ENCRYPTED = "encryptedByAPasswordEncoder";

    public Password encrypted() {
        Password password = Password.withRaw(RAW);
        password.encrypt(ENCRYPTED);
        return password;
    }

    public Password raw() {
        return Password.withRaw(RAW);
    }
}
