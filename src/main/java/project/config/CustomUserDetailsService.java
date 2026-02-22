package project.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.entity.UserEntity;
import project.repository.UserRepository;

import java.util.Optional;
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optional = userRepository.findByUsername(username);
        if(optional.isEmpty()){
            throw new UsernameNotFoundException("user not found");
        }
        UserEntity user = optional.get();
        return new CustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                user.getStatus()
        );
    }
}
