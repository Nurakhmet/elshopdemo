package kz.elshop.elshopdemo.repositories;

import kz.elshop.elshopdemo.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Roles, Long> {

    List<Roles> findByRole(String role);
//    Roles addRole(Roles role);
//    List<Roles> getAllRole();
//    void deleteRole(Roles role);
//    Roles saveRole(Roles role);

}
