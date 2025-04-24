package uz.pdp.uybozor.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.uybozor.repo.UsersRepository;

@Service
public class CustumUserDetailsServise implements UserDetailsService {

    private final UsersRepository usersRepository;

    public CustumUserDetailsServise(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usersRepository.findByEmail(email).orElseThrow();
    }
}
