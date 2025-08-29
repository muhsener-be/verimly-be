package app.verimly.task.domain.repository;

import app.verimly.task.domain.entity.TimeSession;

public interface TimeSessionWriteRepository {

    TimeSession save(TimeSession session);

}
