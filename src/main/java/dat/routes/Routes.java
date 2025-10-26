package dat.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    private final UserRoute userRoute = new UserRoute();
    private final BookRoute bookRoute = new BookRoute();

    public EndpointGroup getRoutes() {
        return () -> {
                path("/users", userRoute.getRoutes());
                path("/books", bookRoute.getRoutes());
        };

    }
}
