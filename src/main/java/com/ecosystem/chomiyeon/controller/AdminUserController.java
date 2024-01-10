package com.ecosystem.chomiyeon.controller;

import com.ecosystem.chomiyeon.configuration.CurrentUser;
import com.ecosystem.chomiyeon.configuration.EmailRequired;
import com.ecosystem.chomiyeon.configuration.UserPrincipal;
import com.ecosystem.chomiyeon.configuration.UsernameRequired;
import com.ecosystem.chomiyeon.constant.AppConstants;
import com.ecosystem.chomiyeon.entity.AdminUser;
import com.ecosystem.chomiyeon.entity.CmyUser;
import com.ecosystem.chomiyeon.exception.ResourceNotFoundException;
import com.ecosystem.chomiyeon.payload.*;
import com.ecosystem.chomiyeon.repository.IAdminUserRepository;
import com.ecosystem.chomiyeon.service.ICmyPollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;
@RestController
@RequestMapping("/admin-users")
public class AdminUserController {
    @Autowired
    private IAdminUserRepository iAdminUserRepository;

    private ICmyPollService iCmyPollService;
    @GetMapping("/current/admin-user")
    @PreAuthorize("hasRole('USER')")
    public AdminUserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        AdminUserSummary adminUserSummary = new AdminUserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return adminUserSummary;
    }

    @GetMapping("/check-username-availability/admin-user")
    public ResponseEntity<AdminUserIdentityAvailability> checkUsernameAvailability(@UsernameRequired String username) {
        if (!StringUtils.hasText(username)) {
            return ResponseEntity.badRequest().build();
        }

        Boolean isAvailable = !this.iAdminUserRepository.existsByUsername(username);
        return ResponseEntity.ok(new AdminUserIdentityAvailability(isAvailable));
    }


    @GetMapping("/check-email-availability/admin-user")
    public AdminUserIdentityAvailability checkEmailAvailability(@EmailRequired String email) {
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

    @GetMapping("/admin-users/{username}/cmy-polls")
    public PagedResponse<CmyPollResponse> getCmyPollsCreatedBy(@PathVariable(value = "username") String username,
                                                            @CurrentUser AdminUser currentUser,
                                                            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return this.iCmyPollService.getCmyPollsCreatedBy(username,currentUser,page,size);
    }

    @GetMapping("/admin-users/{username}/cmy-votes")
    public PagedResponse<CmyPollResponse> getPollsVotedBy(@PathVariable(value = "username") String username,
                                                       @CurrentUser AdminUser currentUser,
                                                       @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return this.iCmyPollService.getCmyPollsVotedBy(username, currentUser, page, size);
    }

}
