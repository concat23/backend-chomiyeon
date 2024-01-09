package com.ecosystem.chomiyeon.controller;

import com.ecosystem.chomiyeon.configuration.JwtTokenProvider;
import com.ecosystem.chomiyeon.entity.AdminRole;
import com.ecosystem.chomiyeon.entity.AdminUser;
import com.ecosystem.chomiyeon.entity.Token;
import com.ecosystem.chomiyeon.enumaration.RoleName;
import com.ecosystem.chomiyeon.exception.AppException;
import com.ecosystem.chomiyeon.payload.ApiResponse;
import com.ecosystem.chomiyeon.payload.JwtAuthenticationResponse;
import com.ecosystem.chomiyeon.payload.LoginRequest;
import com.ecosystem.chomiyeon.payload.SignUpRequest;
import com.ecosystem.chomiyeon.repository.IAdminRoleRepository;
import com.ecosystem.chomiyeon.repository.IAdminUserRepository;
import javax.validation.Valid;

import com.ecosystem.chomiyeon.repository.IAdminUserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static com.ecosystem.chomiyeon.constant.GenerateValue.ACCESS_TOKEN_EXPIRATION_TIME;

@RestController
@RequestMapping(path="/administrator-auth")
public class AdminAuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IAdminUserRepository iAdminUserRepository;

    @Autowired
    IAdminRoleRepository iAdminRoleRepository;

    @Autowired
    IAdminUserTokenRepository iAdminUserTokenRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsernameOrEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.generateToken(authentication);

            if (jwt != null) {
                // Extract AdminUser from Optional
                Optional<AdminUser> adminUserOptional = iAdminUserRepository.findByUsernameOrEmail(loginRequest.getUsernameOrEmail(), loginRequest.getUsernameOrEmail());

                if (adminUserOptional.isPresent()) {
                    AdminUser adminUser = adminUserOptional.get();

                    // Create an AdminUserToken entity
                    Token adminUserToken = new Token();

                    // Set the AdminUser associated with the token
                    adminUserToken.setAdminUser(adminUser);

                    // Set the access token
                    adminUserToken.setAccessToken(jwt);

                    // Set the token expiration dates (adjust based on your requirements)
                    adminUserToken.setAccessTokenExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME));

                    // Save the AdminUserToken to the database
                    iAdminUserTokenRepository.save(adminUserToken);
                }
            }

            return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
        } catch (AuthenticationException e) {
            // Handle authentication failure
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
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

        AdminRole userRole = iAdminRoleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new AppException("User Role not set."));

        adminUser.setRoles(Collections.singleton(userRole));

        AdminUser result = iAdminUserRepository.save(adminUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/admin-users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

}
