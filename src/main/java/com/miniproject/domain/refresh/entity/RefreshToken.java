package com.miniproject.domain.refresh.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(of = "id")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memberEmail;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @Builder
    public RefreshToken(Long id, String memberEmail, String token, LocalDateTime expiryDate) {
        this.id = id;
        this.memberEmail = memberEmail;
        this.token = token;
        this.expiryDate = expiryDate;
    }
}