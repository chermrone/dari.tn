package tn.dari.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



import tn.dari.spring.entity.Role;
import tn.dari.spring.enumeration.Usertype;

public interface RoleRepository extends JpaRepository<Role, Long> {
	   Optional<Role> findByName(Usertype roleName);

}
