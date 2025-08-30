package app.verimly.commons.core.adapter.persistence.aspect;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class SoftDeleteAspect {

    @PersistenceContext
    EntityManager entityManager;


    @Before("@annotation(EnableSoftDeleteFilter)")
    public void enableSoftDeleteFilter() {

        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("soft-delete-filter");
        log.debug("Soft-delete filter is enabled.");


    }


}
