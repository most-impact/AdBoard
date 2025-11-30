package pro.sky.AdBoard.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "auth_info")
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Конструкторы
    public AuthInfo() {}

    public AuthInfo(User user, String refreshToken, LocalDateTime expiresAt) {
        this.user = user;
        this.refreshToken = refreshToken;
        this.expiresAt = expiresAt;
        this.createdAt = LocalDateTime.now();
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }

    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // equals, hashCode, toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthInfo authInfo = (AuthInfo) o;
        return Objects.equals(id, authInfo.id) &&
                Objects.equals(user, authInfo.user) &&
                Objects.equals(refreshToken, authInfo.refreshToken) &&
                Objects.equals(expiresAt, authInfo.expiresAt) &&
                Objects.equals(createdAt, authInfo.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, refreshToken, expiresAt, createdAt);
    }

    @Override
    public String toString() {
        return "AuthInfo{" +
                "id=" + id +
                ", user=" + (user != null ? user.getUsername() : "null") +
                ", expiresAt=" + expiresAt +
                ", createdAt=" + createdAt +
                '}';
    }
}