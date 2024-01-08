package com.ecosystem.chomiyeon.configuration;

import com.ecosystem.chomiyeon.entity.AdminUser;
import com.ecosystem.chomiyeon.repository.IAdminUserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {


    private final IAdminUserRepository iAdminUserRepository;
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        AdminUser adminUser = iAdminUserRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Admin user not found with username or email : " + usernameOrEmail)
                );

        return UserPrincipal.create(adminUser);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        AdminUser adminUser = iAdminUserRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Admin user not found with id : " + id)
        );

        return UserPrincipal.create(adminUser);
    }
}
