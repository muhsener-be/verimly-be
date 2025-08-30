package app.verimly.task.adapter.web;

import app.verimly.task.application.dto.SessionSummaryData;

import java.time.Instant;
import java.util.Comparator;

public class SessionComparator implements Comparator<SessionSummaryData> {


    @Override
    public int compare(SessionSummaryData o1, SessionSummaryData o2) {
        if (o1.isRunning())
            return -1;
        else if (o2.isRunning())
            return 1;

        Instant start1 = o1.getStartedAt();
        Instant start2 = o2.getStartedAt();
        return start2.compareTo(start1);

    }
}
