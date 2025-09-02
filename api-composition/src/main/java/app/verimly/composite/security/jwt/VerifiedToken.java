package app.verimly.composite.security.jwt;

import java.util.UUID;


public record VerifiedToken(UUID subject, String email) {

}
