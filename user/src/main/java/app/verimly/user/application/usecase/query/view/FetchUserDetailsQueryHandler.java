package app.verimly.user.application.usecase.query.view;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.commons.core.security.Principal;
import app.verimly.user.application.dto.UserDetailsData;
import app.verimly.user.application.exception.UserNotFoundException;
import app.verimly.user.application.ports.out.persistence.UserReadRepository;
import app.verimly.user.application.ports.out.security.UserSecurityGateway;
import app.verimly.user.application.ports.out.security.context.FetchUserDetailsContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FetchUserDetailsQueryHandler {

    private final UserSecurityGateway securityGateway;
    private final UserReadRepository repository;

    @Transactional
    public UserDetailsData handle() {
        Principal principal = securityGateway.getCurrentPrincipal();

        authorizeRequest(principal);

        return fetchUserDetailsAndCheckExistence(principal.getId());

    }

    private UserDetailsData fetchUserDetailsAndCheckExistence(UserId id) {
        return repository.fetchUserDetailsById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    private void authorizeRequest(Principal principal) {
        FetchUserDetailsContext context = FetchUserDetailsContext.createWithUserId(principal.getId());
        securityGateway.authorizeFetchUserDetails(principal, context);
    }
}
