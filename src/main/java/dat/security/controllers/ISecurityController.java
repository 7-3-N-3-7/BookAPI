package dat.security.controllers;

import dat.dtos.UserDTO;
import io.javalin.http.Handler;
import io.javalin.security.RouteRole;

import java.util.Set;

/**
 * Purpose: To handle security in the API
 * Author: Thomas Hartmann
 */
public interface ISecurityController {
    Handler login(); // to get a token
    Handler register(); // to get a user
    Handler authenticate(); // to verify roles inside token
    boolean authorize(dat.dtos.UserDTO userDTO,
                      Set<RouteRole> allowedRoles); // to verify user roles
    String createToken(dat.dtos.UserDTO user) throws dat.exceptions.ApiException;
    dat.dtos.UserDTO verifyToken(String token) throws dat.exceptions.ApiException;
}
