package dat.routes;

import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class UserRoute {

    private final UserController userController = new UserController();

    protected EndpointGroup getRoutes() {

        return () -> {
            get("/populate", userController::populate);
            post("/", userController::create, Role.USER);
            get("/", userController::readAll);
            get("/{id}", userController::read);
            put("/{id}", userController::update);
            delete("/{id}", userController::delete);
        };
    }
}
