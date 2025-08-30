package app.verimly.user.application.ports.out.security;

import app.verimly.user.domain.vo.Password;

public interface SecurityPort {

    Password encrypt(Password rawPassword) throws EncryptionException;
}
