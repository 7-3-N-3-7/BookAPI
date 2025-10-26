package dat.daos.impl;

import dat.daos.IDAO;
import dat.dtos.UserDTO;
import dat.entities.Book;
import dat.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class UserDAO implements IDAO<dat.dtos.UserDTO, Integer> {

    private static UserDAO instance;
    private static EntityManagerFactory emf;

    Set<UserDTO> allBooks = readAll();

    public static UserDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserDAO();
        }
        return instance;
    }

    @Override
    public UserDTO read(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            dat.entities.User user = em.find(dat.entities.User.class, integer);
            return new UserDTO(user);
        }
    }

    @Override
    public Set<dat.dtos.UserDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<UserDTO> query = em.createQuery("SELECT new dat.dtos.UserDTO(e) FROM dat.entities.User e", dat.dtos.UserDTO.class);
            return new HashSet<>(query.getResultList());
        }
    }

    @Override
    public UserDTO create(UserDTO dto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = new User(dto);
            em.persist(user);
            em.getTransaction().commit();
            return new UserDTO(user);
        }
    }

    @Override
    public UserDTO update(Integer integer, UserDTO dto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = em.find(User.class, integer);
            user.setUsername(dto.getUser().getUsername());
            user.setUserRole(dto.getUserRole());
            User mergedUser = em.merge(user);
            em.getTransaction().commit();
            return mergedUser != null ? new UserDTO(mergedUser) : null;
        }
    }

    @Override
    public void delete(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = em.find(User.class, integer);
            if (user != null) {
                em.remove(user);
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            User user = em.find(User.class, integer);
            return user != null;
        }
    }

    public void populate() {
        try (var em = emf.createEntityManager()) {
            em
                    .getTransaction()
                    .begin();
            User user1 = new User("user1", null, dat.security.enums.Role.SUPERMAN);
            Book book1 = new Book("Harry Potter", new BigDecimal(99.95), "JK Rowling", user1);

            User user2 = new User("User2",null, dat.security.enums.Role.USER);
            Book book2 = new Book("IT", new BigDecimal(199.95), "Stephen King", user2);
            em.persist(user1);
            em.persist(book1);
            em.persist(user2);
            em.persist(book2);
            em
                    .getTransaction()
                    .commit();
        }
    }


    private  Set<Book> readUsersBooks(java.lang.Integer id) {
        return read(id).getBooks();
    }
}
