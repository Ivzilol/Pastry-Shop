package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByUsersId(Long id);
}
