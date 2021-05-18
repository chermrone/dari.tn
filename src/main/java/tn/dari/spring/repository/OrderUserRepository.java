package tn.dari.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.dari.spring.entity.OrderUser;
import tn.dari.spring.entity.ShoppingCart;

@Repository
public interface OrderUserRepository extends JpaRepository<OrderUser, Long> {
    public Optional<OrderUser> findByShoppingCartAndStatusOrd(ShoppingCart shoppingCart,boolean statusOrd);
}
