package pro.sky.AdBoard.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "ads")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;

    @Column(nullable = false, length = 32)
    private String title;

    @Column(nullable = false)
    private Integer price;

    @Column(length = 64)
    private String description;

    @Column
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Ad() {
    }

    public Ad(Integer pk, String title, Integer price, String description,
              String image, User author, LocalDateTime createdAt) {
        this.pk = pk;
        this.title = title;
        this.price = price;
        this.description = description;
        this.image = image;
        this.author = author;
        this.createdAt = createdAt;
    }

    public Integer getPk() {
        return pk;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public User getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ad ad = (Ad) o;
        return Objects.equals(pk, ad.pk) &&
                Objects.equals(title, ad.title) &&
                Objects.equals(price, ad.price) &&
                Objects.equals(description, ad.description) &&
                Objects.equals(image, ad.image) &&
                Objects.equals(author, ad.author) &&
                Objects.equals(createdAt, ad.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, title, price, description, image, author, createdAt);
    }

    @Override
    public String toString() {
        return "Ad{" +
                "pk=" + pk +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", author=" + (author != null ? author.getUsername() : "null") +
                ", createdAt=" + createdAt +
                '}';
    }
}