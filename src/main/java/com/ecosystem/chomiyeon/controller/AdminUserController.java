package com.ecosystem.chomiyeon.controller;

import com.ecosystem.chomiyeon.configuration.CurrentUser;
import com.ecosystem.chomiyeon.configuration.UserPrincipal;
import com.ecosystem.chomiyeon.entity.AdminUser;
import com.ecosystem.chomiyeon.exception.ResourceNotFoundException;
import com.ecosystem.chomiyeon.payload.AdminUserIdentityAvailability;
import com.ecosystem.chomiyeon.payload.AdminUserProfile;
import com.ecosystem.chomiyeon.payload.AdminUserSummary;
import com.ecosystem.chomiyeon.repository.IAdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin-users")
public class AdminUserController {
    @Autowired
    private IAdminUserRepository iAdminUserRepository;

    @GetMapping("/current/admin-user")
    @PreAuthorize("hasRole('USER')")
    public AdminUserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        AdminUserSummary adminUserSummary = new AdminUserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return adminUserSummary;
    }

    @GetMapping("/check-username-availability/admin-user")
    public AdminUserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !this.iAdminUserRepository.existsByUsername(username);
        return new AdminUserIdentityAvailability(isAvailable);
    }

    @GetMapping("/check-email-availability/admin-user")
    public AdminUserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !this.iAdminUserRepository.existsByEmail(email);
        return new AdminUserIdentityAvailability(isAvailable);
    }

    @GetMapping("/{username}/admin-user")
    public AdminUserProfile getAdminUserProfile(@PathVariable(value = "username") String username) {
        AdminUser adminUser = this.iAdminUserRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Admin User", "username", username));

        AdminUserProfile userProfile = new AdminUserProfile(adminUser.getId(), adminUser.getUsername(), adminUser.getName(),adminUser.getCreatedAt());

        return userProfile;
    }

}
