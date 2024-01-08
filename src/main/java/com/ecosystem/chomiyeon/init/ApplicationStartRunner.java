//package com.ecosystem.chomiyeon.init;
//
//import com.ecosystem.chomiyeon.entity.AdminRole;
//import com.ecosystem.chomiyeon.entity.AdminUser;
//import com.ecosystem.chomiyeon.enumaration.RoleName;
//import com.ecosystem.chomiyeon.repository.IAdminRoleRepository;
//import com.ecosystem.chomiyeon.repository.IAdminUserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Component
//public class ApplicationStartRunner implements CommandLineRunner {
//    @Autowired
//    private IAdminUserRepository iAdminUserRepository;
//
//    @Autowired
//    private IAdminRoleRepository iAdminRoleRepository;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//    @Override
//    public void run(String... args) throws Exception {
//        AdminRole adminRole = new AdminRole();
//        adminRole.setName(RoleName.valueOf("ROLE_ADMIN"));
//        iAdminRoleRepository.save(adminRole);
//
//        // Create an admin user
//        AdminUser adminUser = new AdminUser();
//        adminUser.setName("admin");
//        adminUser.setEmail("admin@gmail.com");
//        adminUser.setUsername("admin");
//        adminUser.setPassword(passwordEncoder.encode("password"));
//
//
//        Set<AdminRole> roles = new HashSet<>();
//        roles.add(adminRole);
//        adminUser.setRoles(roles);
//
//        iAdminUserRepository.save(adminUser);
//    }
//}
