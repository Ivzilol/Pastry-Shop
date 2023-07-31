package com.example.pastry.shop.testRepository;

import com.example.pastry.shop.model.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2RepositoryAuthority extends JpaRepository<Authority, Long> {
}
