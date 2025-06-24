package example.soloproject.global.service;

import example.soloproject.global.entity.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {
    UserDetails loadUserByUserID(String userId) throws UsernameNotFoundException;
}
