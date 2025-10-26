package dat.security.daos;

import dat.entities.User;
import dat.security.exceptions.ValidationException;
import dat.dtos.UserDTO;

public interface ISecurityDAO {
    UserDTO getVerifiedUser(String username, String password) throws ValidationException;
    dat.entities.User createUser(String username, String password);
    //dat.entities.User addRole(UserDTO user, String newRole);
}
