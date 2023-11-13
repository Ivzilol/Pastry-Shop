package com.example.pastry.shop.testRepository;

import com.example.pastry.shop.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TestH2RepositoryUsers extends JpaRepository<Users, Long> {

    Users findByUsername(String pesho);
}
