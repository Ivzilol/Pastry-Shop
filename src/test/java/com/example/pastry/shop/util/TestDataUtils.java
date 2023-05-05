package com.example.pastry.shop.util;

import com.example.pastry.shop.repository.*;
import org.springframework.stereotype.Component;

@Component
public class TestDataUtils {

    private final AuthorityRepository authorityRepository;

    private final CommentRepository commentRepository;

    private final OrdersProcessingRepository ordersProcessingRepository;

    private final OrdersRepository ordersRepository;

    private final ProductRepository productRepository;

    private final ShopsRepository shopsRepository;

    private final UsersRepository usersRepository;

    public TestDataUtils(AuthorityRepository authorityRepository, CommentRepository commentRepository, OrdersProcessingRepository ordersProcessingRepository, OrdersRepository ordersRepository, ProductRepository productRepository, ShopsRepository shopsRepository, UsersRepository usersRepository) {
        this.authorityRepository = authorityRepository;
        this.commentRepository = commentRepository;
        this.ordersProcessingRepository = ordersProcessingRepository;
        this.ordersRepository = ordersRepository;
        this.productRepository = productRepository;
        this.shopsRepository = shopsRepository;
        this.usersRepository = usersRepository;
    }
}
