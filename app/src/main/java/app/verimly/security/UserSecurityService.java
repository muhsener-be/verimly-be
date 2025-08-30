package app.verimly.security;

import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.security.SecurityUser;
import app.verimly.user.domain.entity.User;
import app.verimly.user.domain.repository.UserWriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor

@Service
public class UserSecurityService implements UserDetailsService {

    private final UserWriteRepository userWriteRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Optional<User> optional = userWriteRepository.findByEmail(Email.of(username));
        if (optional.isEmpty())
            throw new UsernameNotFoundException("User with email '%s' not found.".formatted(username));


        User foundUser = optional.get();
        return new SecurityUser(foundUser.getId().getValue(), foundUser.getEmail().getValue(), foundUser.getPassword().getEncrypted() , foundUser.getName().getFullName());

    }
}
