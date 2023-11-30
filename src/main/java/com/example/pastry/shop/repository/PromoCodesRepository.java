package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.entity.PromoCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoCodesRepository extends JpaRepository<PromoCodes, Long> {


}
