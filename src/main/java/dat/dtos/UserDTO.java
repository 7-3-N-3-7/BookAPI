package dat.dtos;

import dat.entities.Book;
import dat.entities.User;
import dat.security.entities.Role;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UserDTO {

    private User user;
    private Set<Book> books = new HashSet<>();

    public UserDTO(User user, Set<Book> books)
    {
        this.user = user;
        this.books = books;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.user, this.books});
    }
}
