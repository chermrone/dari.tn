package tn.dari.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.dari.spring.entity.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

}
