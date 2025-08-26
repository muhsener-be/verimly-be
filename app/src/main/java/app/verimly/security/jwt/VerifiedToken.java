package app.verimly.security.jwt;

import java.util.UUID;


public record VerifiedToken(UUID subject, String email) {

}
