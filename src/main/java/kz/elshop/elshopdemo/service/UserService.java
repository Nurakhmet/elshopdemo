package kz.elshop.elshopdemo.service;

import kz.elshop.elshopdemo.entities.Comments;
import kz.elshop.elshopdemo.entities.Roles;
import kz.elshop.elshopdemo.entities.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    Users getUserByEmail(String email);

    List<Roles> findByRole(String role);
    Roles addRole(Roles role);
    List<Roles> getAllRole();
    Roles getRole(Long id);
    void deleteRole(Roles role);
    Roles saveRole(Roles role);

    Users addUser(Users user);
    List<Users> getAllUsers();
    Users getUser(Long id);
    void deleteUser(Users user);
    Users saveUser(Users user);

}
