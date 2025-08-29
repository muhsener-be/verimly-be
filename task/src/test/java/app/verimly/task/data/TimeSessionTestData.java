package app.verimly.task.data;

import app.verimly.commons.core.domain.vo.SessionId;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.domain.input.SessionCreationDetails;
import app.verimly.task.domain.vo.session.SessionName;
import com.github.javafaker.Faker;

public class TimeSessionTestData {

    private static final Faker FAKER = new Faker();
    private static final TimeSessionTestData INSTANCE = new TimeSessionTestData();

    public static TimeSessionTestData getInstance() {
        return INSTANCE;
    }

    public SessionId id() {
        return SessionId.random();
    }

    public SessionName name() {
        return SessionName.of(FAKER.team().name());
    }

    public SessionCreationDetails sessionCreationDetailsWithOwnerId(UserId ownerId) {
        return new SessionCreationDetails(name(),ownerId);
    }
}
