package dat.dtos;

import dat.entities.Book;
import dat.entities.User;
import lombok.*;

import java.util.List;
import java.math.BigDecimal;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
public class BookDTO {

    private Integer bookId;
    private String title;
    private BigDecimal price;
    private String author;
    private User user;

    public BookDTO(Book book) {
        this.bookId = book.getBookId();
        this.title = book.getTitle();
        this.price = book.getPrice();
        this.author = book.getAuthor();
        this.user = book.getUser();
    }

    public BookDTO(String title, BigDecimal price, String author, User user){
        this.title = title;
        this.price = price;
        this.author = author;
        this.user = user;
    }

    public static List<BookDTO> toBookDTOList(List<Book> books) {
        return books.stream().map(BookDTO::new).toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        BookDTO bookDTO = (BookDTO) o;
        return Objects.equals(bookId, bookDTO.bookId) &&
                Objects.equals(title, bookDTO.title) &&
                Objects.equals(price, bookDTO.price) &&
                Objects.equals(author, bookDTO.author) &&
                Objects.equals(user, bookDTO.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, title, price, author, user);
    }
}
