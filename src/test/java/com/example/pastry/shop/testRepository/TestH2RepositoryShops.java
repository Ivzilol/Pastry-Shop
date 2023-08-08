package com.example.pastry.shop.testRepository;

import com.example.pastry.shop.model.entity.Shops;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestH2RepositoryShops extends JpaRepository<Shops, Long> {
}
