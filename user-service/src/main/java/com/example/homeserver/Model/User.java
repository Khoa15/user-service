package com.example.homeserver.Model;

import com.example.homeserver.Util.SecurityUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

@Entity
@Table(name = "users")
@PrimaryKeyJoinColumn(name = "user_id") // <-- Quan trọng: Cột khóa chính của bảng 'users' cũng là khóa ngoại trỏ tới 'persons'
@Getter
public class User extends Person {
    @Email
    @Null
    private String email;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank
    @Nonnull
    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT+7")

    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;

    @PrePersist
    protected void onCreate() {
        this.createdBy = SecurityUtil.getCurrentUserLogin().isPresent() ?
                SecurityUtil.getCurrentUserLogin().get() : null;
        this.createdAt = Instant.now();
    }

    public void setPassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }
}
