package example.soloproject.global.service.impl;

import example.soloproject.global.entity.UserDetails;
import example.soloproject.global.repository.UserRepository;
import example.soloproject.global.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUserID(String userId) throws UsernameNotFoundException {
        logger.info("UserDetailsServiceImpl: loadUserByUserId 실행 유저 ID : {}", userId);
        return userRepository.findByUId(userId);
    }
}
