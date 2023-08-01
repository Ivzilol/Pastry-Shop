package com.example.pastry.shop.testRepository;

import com.example.pastry.shop.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TestH2RepositoryUsers extends JpaRepository<Users, Long> {

    @Query("select u from Users as u" +
            " where u.id = :userId")
    Users findUser(Long userId);
}
