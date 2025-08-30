package app.verimly.user.adapter.security;

import app.verimly.commons.core.domain.exception.Assert;
import app.verimly.user.application.ports.out.security.EncryptionException;
import app.verimly.user.application.ports.out.security.SecurityPort;
import app.verimly.user.domain.vo.Password;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityPortAdapter implements SecurityPort {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Password encrypt(Password rawPassword) throws EncryptionException {
        Assert.notNull(rawPassword, "Password cannot be null!");

        try {
            String rawValue = rawPassword.getRaw();
            String encodedValue = passwordEncoder.encode(rawValue);
            rawPassword.encrypt(encodedValue);

            return rawPassword;
        } catch (Exception e) {
            throw new EncryptionException("Encryption failed due to: " + e.getMessage(), e);
        }


    }
}

