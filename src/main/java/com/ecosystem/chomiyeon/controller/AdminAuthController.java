package com.ecosystem.chomiyeon.controller;

import com.ecosystem.chomiyeon.configuration.JwtTokenProvider;
import com.ecosystem.chomiyeon.entity.AdminRole;
import com.ecosystem.chomiyeon.entity.AdminUser;
import com.ecosystem.chomiyeon.enumaration.RoleName;
import com.ecosystem.chomiyeon.exception.AppException;
import com.ecosystem.chomiyeon.payload.ApiResponse;
import com.ecosystem.chomiyeon.payload.JwtAuthenticationResponse;
import com.ecosystem.chomiyeon.payload.LoginRequest;
import com.ecosystem.chomiyeon.payload.SignUpRequest;
import com.ecosystem.chomiyeon.repository.IAdminRoleRepository;
import com.ecosystem.chomiyeon.repository.IAdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/admin-auth")
public class AdminAuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IAdminUserRepository iAdminUserRepository;

    @Autowired
    IAdminRoleRepository iAdminRoleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(iAdminUserRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(iAdminUserRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        AdminUser adminUser = new AdminUser(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        adminUser.setPassword(passwordEncoder.encode(adminUser.getPassword()));

        AdminRole userRole = iAdminRoleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        adminUser.setRoles(Collections.singleton(userRole));

        AdminUser result = iAdminUserRepository.save(adminUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/admin-users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

}
