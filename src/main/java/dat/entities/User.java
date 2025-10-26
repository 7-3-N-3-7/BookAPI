package dat.entities;

import dat.entities.Book;
import dat.security.entities.ISecurityUser;
import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.mindrot.jbcrypt.BCrypt;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Purpose: To handle security in the API
 * Author: Thomas Hartmann
 */
@Entity
@Table(name = "users")
@NamedQueries(@NamedQuery(name = "User.deleteAllRows", query = "DELETE from User"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable, ISecurityUser {

    @Serial
    private static final long serialVersionUID = 1L;
    private java.lang.String username;

    private dat.security.enums.Role userRole = dat.security.enums.Role.ANYONE;

    public User(dat.dtos.UserDTO dto)
    {
        this.books = dto.getBooks();
    }

    public User (java.lang.String username,
                 Set<Book> books,
                 dat.security.enums.Role userRole)
    {
        this.username   =   username;
        this.books      =   books;
        this.userRole   =   userRole;
    }

    @Id
    @Basic(optional = false)
    @Column(name = "username", length = 25)
    private String username;

    @Basic(optional = false)
    @Column(name = "password")
    private String password;


    @JoinTable(name = "user_books", joinColumns = {@JoinColumn(name = "user_name", referencedColumnName = "username")}, inverseJoinColumns = {@JoinColumn(name = "book_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Book> books = new HashSet<>();

    public Set<String> getBookTitles() {
        if (books.isEmpty()) {
            return null;
        }
        Set<String> bookTitles = new HashSet<>();
        books.forEach((book) -> {
            bookTitles.add(book.getTitle());
        });
        return bookTitles;
    }


    public boolean verifyPassword(String pw) {
        return BCrypt.checkpw(pw, this.password);
    }

    public User(String userName, String userPass) {
        this.username = userName;
        this.password = BCrypt.hashpw(userPass, BCrypt.gensalt());
    }

    public User(String userName, Set<Book> books) {
        this.username = userName;
        this.books = books;
    }

    public void addBook(@NotNull Book book) {
        books.add(book);
        book.setUser(this);
    }

    public void removeBook(Book book) {
        books.stream()
                .filter(books -> book.getTitle().equals(book.getTitle()))
                .findFirst()
                .ifPresent(role -> {
                    books.remove(role);
                });
    }

    public void setUsername(java.lang.String username)
    {
        this.username = username;
    }

    public dat.security.enums.Role getUserRole()
    {
        return this.userRole;
    }

    public void setUserRole(dat.security.enums.Role role)
    {
        this.userRole = role;
    }
}

