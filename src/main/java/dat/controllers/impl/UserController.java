package dat.controllers.impl;

import dat.config.HibernateConfig;
import dat.controllers.IController;
import dat.daos.impl.UserDAO;
import dat.dtos.UserDTO;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class UserController implements IController<UserDTO, Integer> {

    private final UserDAO dao;

    public UserController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = UserDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx)  {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // DTO
        UserDTO dto = dao.read(id);
        // response
        ctx.res().setStatus(200);
        ctx.json(dto, UserDTO.class);
    }

    @Override
    public void readAll(Context ctx) {
        // Set of DTOS
        java.util.Set<UserDTO> dtos = dao.readAll();
        // response
        ctx.res().setStatus(200);
        ctx.json(dtos, UserDTO.class);
    }

    @Override
    public void create(Context ctx) {
        // request
        UserDTO jsonRequest = ctx.bodyAsClass(UserDTO.class);
        // DTO
        UserDTO dto = dao.create(jsonRequest);
        // response
        ctx.res().setStatus(201);
        ctx.json(dto, UserDTO.class);
    }

    @Override
    public void update(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // dto
        UserDTO dto = dao.update(id, validateEntity(ctx));
        // response
        ctx.res().setStatus(200);
        ctx.json(dto, UserDTO.class);
    }

    @Override
    public void delete(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        dao.delete(id);
        // response
        ctx.res().setStatus(204);
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        return dao.validatePrimaryKey(integer);
    }

    @Override
    public UserDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(UserDTO.class)
                .check( user -> user.getId() != null, "user id MUST be set")
                .check( user -> user.getUsername() != null && !user.getUsername().isEmpty(), "username MUST be set")
                .check( user -> user.getUserRole() != null, "User role MUST be set")
                .get();
    }

    public void populate(Context ctx) {
        dao.populate();
        ctx.res().setStatus(200);
        ctx.json("{ \"message\": \"Database has been populated\" }");
    }
}

