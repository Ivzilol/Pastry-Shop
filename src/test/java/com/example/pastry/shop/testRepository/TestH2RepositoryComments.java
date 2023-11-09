package com.example.pastry.shop.testRepository;

import com.example.pastry.shop.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2RepositoryComments extends JpaRepository<Comment, Long> {
}
