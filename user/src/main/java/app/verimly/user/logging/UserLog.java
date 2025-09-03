package app.verimly.user.logging;

import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.domain.vo.UserId;
import lombok.extern.slf4j.Slf4j;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
public class UserLog {


    public static void userRegistered(String actor, UserId userId, Email email) {
        log.info("New user registered",
                kv("event", "user.registration"),
                kv("details", java.util.Map.of(
                        "actor", actor,
                        "user_id", userId.getValue().toString(),
                        "email", email.maskEmail()
                ))
        );
    }


}



