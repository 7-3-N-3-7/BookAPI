package dat.entities;

import dat.dtos.BookDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", nullable = false, unique = true)
    private Integer bookId;

    @Setter
    @Column(name = "title", nullable = false)
    private String title;

    @Setter
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Setter
    @Column(name = "author", nullable = false)
    private String author;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Book(String title, BigDecimal price, String author, User user) {
        this.title = title;
        this.price = price;
        this.author = author;
        this.user = user;
    }

    public Book(BookDTO bookDTO){
        this.bookId = bookDTO.getBookId();
        this.title = bookDTO.getTitle();
        this.price = bookDTO.getPrice();
        this.author = bookDTO.getAuthor();
        this.user = bookDTO.getUser();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(bookId, book.bookId) && Objects.equals(user, book.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, user);
    }
}
