package app.verimly.user.application.ports.out;

import app.verimly.user.domain.vo.Password;

public interface SecurityPort {

    Password encrypt(Password rawPassword) throws EncryptionException;
}
