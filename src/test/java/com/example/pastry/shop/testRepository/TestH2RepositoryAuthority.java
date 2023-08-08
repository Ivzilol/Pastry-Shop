package com.example.pastry.shop.testRepository;

import com.example.pastry.shop.model.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestH2RepositoryAuthority extends JpaRepository<Authority, Long> {
    @Query("select a from Authority as a" +
            " where a.authority = 'user'")
    List<Authority> findUsers();
    @Query("select a from Authority as a" +
            " where a.authority = 'admin'")
    List<Authority> findAdmin();
}
