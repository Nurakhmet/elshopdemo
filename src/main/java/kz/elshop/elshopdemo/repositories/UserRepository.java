package kz.elshop.elshopdemo.repositories;

import kz.elshop.elshopdemo.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);

//    Users addUser(Users user);
//    List<Users> getAllUsers();
//    Users getUser(Long id);
//    Users saveUser(Users user);
}
