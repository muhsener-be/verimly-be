package app.verimly.task.adapter.web.mapper;

import app.verimly.task.adapter.web.dto.response.SessionSummaryWebResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TotalTimeAndSessionWebResponses {

    private Duration totalTime;
    private List<SessionSummaryWebResponse> sessions;


}
