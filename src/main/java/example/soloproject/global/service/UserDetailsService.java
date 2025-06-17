package example.soloproject.global.service;

import example.soloproject.global.entity.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {
    UserDetails loadUserByNickname(String nickname) throws UsernameNotFoundException;
}
