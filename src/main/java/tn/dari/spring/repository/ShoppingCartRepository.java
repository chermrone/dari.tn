package tn.dari.spring.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.dari.spring.entity.ShoppingCart;
import tn.dari.spring.entity.User;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
	
    public Optional<Set<ShoppingCart>> findByUs(User user);
}
