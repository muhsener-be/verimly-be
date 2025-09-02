package app.verimly.task.adapter.web;

import app.verimly.task.application.TaskAbstractUnitTest;
import app.verimly.task.application.dto.SessionSummaryData;
import app.verimly.task.domain.vo.session.SessionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class SessionComparatorTestTask extends TaskAbstractUnitTest {

    List<SessionSummaryData> sessions;

    SessionComparator comparator;

    @BeforeEach
    public void setup() {
        comparator = new SessionComparator();
        sessions = SESSION_TEST_DATA.sessionSummaryDataWithRandomStartedAt(20);

    }

    @Test
    void test() {
        sortSessions();

        printSessions();

        sessions.getLast().setStatus(SessionStatus.RUNNING);
        System.out.println("******");
        System.out.println("******");
        System.out.println("******");


        sortSessions();

        printSessions();
    }




    private void sortSessions() {
        sessions.sort(comparator);
    }

    private void printSessions() {
        sessions.forEach(s -> {
            System.out.printf("Name: %-30s, startedAt: %s%n", s.getName(), s.getStartedAt());
        });
    }
}