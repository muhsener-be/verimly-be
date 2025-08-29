package app.verimly.commons.core.adapter.persistence.aspect;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@Aspect
@Component
public class SoftDeleteAspect {

    @PersistenceContext
    EntityManager entityManager;

    private static final String FILTER_APPLIED_KEY = "softDeleteFilterApplied";

    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void enableSoftDeleteFilter() {


        if (TransactionSynchronizationManager.hasResource(FILTER_APPLIED_KEY))
            return;

        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("soft-delete-filter");
        log.debug("Soft-delete filter is enabled.");

        TransactionSynchronizationManager.bindResource(FILTER_APPLIED_KEY, true);

    }
}
