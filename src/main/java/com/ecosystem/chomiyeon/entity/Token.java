package com.ecosystem.chomiyeon.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "admin_user_tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "admin_user_id", nullable = false)
    private AdminUser adminUser;

    @Column
    private String accessToken;

    @Column
    private String refreshToken;

    @Column
    private Date accessTokenExpiration;

    @Column
    private Date refreshTokenExpiration;

}
