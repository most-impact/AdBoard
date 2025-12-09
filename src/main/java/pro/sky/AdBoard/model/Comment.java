package pro.sky.AdBoard.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ad_id", nullable = false)
    private Ad ad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(nullable = false, length = 2000)
    private String text;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Comment() {
    }

    public Comment(Integer pk, Ad ad, User author, String text, LocalDateTime createdAt) {
        this.pk = pk;
        this.ad = ad;
        this.author = author;
        this.text = text;
        this.createdAt = createdAt;
    }

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment comment)) return false;
        return Objects.equals(pk, comment.pk) &&
                Objects.equals(ad, comment.ad) &&
                Objects.equals(author, comment.author) &&
                Objects.equals(text, comment.text) &&
                Objects.equals(createdAt, comment.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, ad, author, text, createdAt);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "pk=" + pk +
                ", ad=" + (ad != null ? ad.getPk() : null) +
                ", author=" + (author != null ? author.getUsername() : null) +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
