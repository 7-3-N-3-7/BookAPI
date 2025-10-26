package dat.dtos;

import dat.entities.Book;
import dat.entities.User;
import dat.security.entities.Role;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@lombok.Getter
public class UserDTO {

    private User user;
    private Set<Book> books = new HashSet<>();
    private java.lang.String username;
    private java.lang.Integer id;
    private dat.security.enums.Role userRole = dat.security.enums.Role.ANYONE;


    public UserDTO(@org.jetbrains.annotations.NotNull User      user,
                   @org.jetbrains.annotations.NotNull Set<Book> books)
    {
        this.user = user;
        this.books = books;
    }

    public UserDTO(@org.jetbrains.annotations.NotNull java.lang.Integer    id,
                   @org.jetbrains.annotations.NotNull java.lang.String     username)
    {
        this.id = id;
        this.username = username;
    }

    public UserDTO(@org.jetbrains.annotations.NotNull User      user)
    {
        this.user = user;
    }

    public java.lang.String getPassword()
    {
        return user.getPassword();
    }

    public dat.security.enums.Role getRole(){
        return userRole;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.user, this.books});
    }

    public dat.security.enums.Role getUserRole()
    {
        return this.user.getUserRole();
    }
}
