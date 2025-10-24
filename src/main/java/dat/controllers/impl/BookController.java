package dat.controllers.impl;

import dat.config.HibernateConfig;
import dat.controllers.IController;
import dat.daos.impl.BookDAO;
import dat.dtos.HotelDTO;
import dat.dtos.BookDTO;
import dat.exceptions.Message;
import dat.entities.Book;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.math.BigDecimal;
import java.util.List;

public class BookController implements IController<BookDTO, Integer> {

    private BookDAO dao;

    public BookController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = BookDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class)
                .check(this::validatePrimaryKey, "Not a valid id")
                .get();
        // entity
        BookDTO bookDTO = dao.read(id);
        // response
        ctx.res().setStatus(200);
        ctx.json(bookDTO, BookDTO.class);
    }

    @Override
    public void readAll(Context ctx) {
        // entity
        List<BookDTO> bookDTOS = dao.readAll();
        // response
        ctx.res().setStatus(200);
        ctx.json(bookDTOS, BookDTO.class);
    }

    @Override
    public void create(Context ctx) {
        int userId = ctx.pathParamAsClass("id", Integer.class)
                    .check(id -> id > 0, "Not a valid user id")
                    .get();

        BookDTO bookDTO = validateEntity(ctx);

        UserDTO result = dao.addBookToUser(userId, bookDTO);

        ctx.status(201);
        ctx.json(result);
    }

    @Override
    public void update(Context ctx) {

        int id = ctx.pathParamAsClass("id", Integer.class)
                    .check(this::validatePrimaryKey, "Not a valid id")
                    .get();

        BookDTO incoming = validateEntity(ctx);
        BookDTO updated = dao.update(id, incoming);

        ctx.status(200);
        ctx.json(updated, BookDTO.class);
    }

    @Override
    public void delete(Context ctx) {

        int id = ctx.pathParamAsClass("id", Integer.class)
                    .check(this::validatePrimaryKey, "Not a valid id")
                    .get();

        dao.delete(id);

        ctx.status(204);
        ctx.json(new Message(204, "Book with id " + id + " deleted succesfully"));
    }

    @Override
    public boolean validatePrimaryKey(Integer id) {
        return id != null && id > 0 && dao.validatePrimaryKey(id);
    }

    @Override
    public BookDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(BookDTO.class)
                .check(b -> b.getTitle() != null && !b.getTitle().isBlank(), "Not a valid title!")
                .check(b -> b.getAuthor() != null && !b.getAuthor().isBlank(), "Not a valid author!")
                .check(b -> b.getPrice() != null , "Not a valid price!")
                .check(b -> isNonNegative(b.getPrice()), "Price must be >= 0")
                .get();
    }

    private boolean isNonNegative(BigDecimal price) {
        return price.compareTo(BigDecimal.ZERO) >= 0;
    }
}
