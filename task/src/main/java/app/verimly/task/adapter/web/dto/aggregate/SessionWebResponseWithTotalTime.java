package app.verimly.task.adapter.web.dto.aggregate;

import app.verimly.task.adapter.web.dto.response.SessionSummaryWebResponse;
import lombok.Getter;

import java.time.Duration;
import java.util.List;

@Getter
public class SessionWebResponseWithTotalTime {

    private Duration totalTime;
    private List<SessionSummaryWebResponse> sessions;

    public SessionWebResponseWithTotalTime(Duration totalTime, List<SessionSummaryWebResponse> sessions) {
        this.totalTime = totalTime;
        this.sessions = sessions;
    }
}
