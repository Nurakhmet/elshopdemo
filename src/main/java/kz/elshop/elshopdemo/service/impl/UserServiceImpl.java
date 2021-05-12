package kz.elshop.elshopdemo.service.impl;

import kz.elshop.elshopdemo.entities.Roles;
import kz.elshop.elshopdemo.entities.Users;
import kz.elshop.elshopdemo.repositories.RoleRepository;
import kz.elshop.elshopdemo.repositories.UserRepository;
import kz.elshop.elshopdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Users myUser = userRepository.findByEmail(s);

        if (myUser!=null){

            User secUser = new User(myUser.getEmail(), myUser.getPassword(), myUser.getRoles());
            return secUser;
        }

        throw new UsernameNotFoundException("User Name Not Found");
    }

    @Override
    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<Roles> findByRole(String role) {
        return roleRepository.findByRole(role);
    }


    @Override
    public Roles addRole(Roles role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Roles> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public Roles getRole(Long id) {
        return roleRepository.getOne(id);
    }

    @Override
    public void deleteRole(Roles role) {
        roleRepository.delete(role);
    }

    @Override
    public Roles saveRole(Roles role) {
        return roleRepository.save(role);
    }

    @Override
    public Users addUser(Users user) {
        return userRepository.save(user);
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Users getUser(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public void deleteUser(Users user) {
        userRepository.delete(user);
    }

    @Override
    public Users saveUser(Users user) {
        return userRepository.save(user);
    }


}
