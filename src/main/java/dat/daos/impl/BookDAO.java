package dat.daos.impl;


import dat.daos.IDAO;
import dat.dtos.HotelDTO;
import dat.dtos.BookDTO;
import dat.entities.Hotel;
import dat.entities.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class BookDAO implements IDAO<BookDTO, Integer> {

    private static BookDAO instance;
    private static EntityManagerFactory emf;

    public static BookDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BookDAO();
        }
        return instance;
    }

    public UserDTO addBookToUser(Integer userId, BookDTO bookDTO ) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Book book = new Book(bookDTO);
            User user = em.find(User.class, userId);
            User.addBook(book);
            em.persist(book);
            User mergedUser = em.merge(user);
            em.getTransaction().commit();
            return new UserDTO(mergedUser);
        }
    }

    @Override
    public BookDTO read(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Book book = em.find(Book.class, id);
            return book != null ? new BookDTO(book) : null;
        }
    }

    @Override
    public List<BookDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<BookDTO> query = em.createQuery("SELECT new dat.dtos.BookDTO(b) FROM Book b", BookDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public BookDTO create(BookDTO bookDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Book book = new Book(bookDTO);
            em.persist(book);
            em.getTransaction().commit();
            return new BookDTO(book);
        }
    }

    @Override
    public BookDTO update(Integer id, BookDTO bookDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Book book = em.find(Book.class, id);
            book.setTitle(bookDTO.getTitle());
            book.setPrice(bookDTO.getPrice());
            book.setAuthor(bookDTO.getAuthor());
            book.setUser(bookDTO.getUser());
            Book mergedBook = em.merge(book);
            em.getTransaction().commit();
            return new BookDTO(mergedBook);
        }
    }

    @Override
    public void delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Book book = em.find(Book.class, id);
            if (book != null){
                em.remove(book);
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean validatePrimaryKey(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Book book = em.find(Book.class, id);
            return book != null;
        }
    }
}
