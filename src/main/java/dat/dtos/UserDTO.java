package dat.dtos;

import dat.entities.Book;
import dat.security.entities.Role;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UserDTO {

    private String username;
    private Set<Book> books = new HashSet<>();

    public UserDTO(String username, Set<Book> books)
    {
        this.username = username;
        this.books = books;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.username, this.books});
    }
}
