package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.dto.PromoCodesDTO;
import com.example.pastry.shop.model.entity.PromoCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PromoCodesRepository extends JpaRepository<PromoCodes, Long> {


    @Query("select new com.example.pastry.shop.model.dto.PromoCodesDTO(" +
            " pc.promoCode)" +
            " from PromoCodes as pc" +
            " where pc.user.id = :id and pc.isUsed = false")
    Set<PromoCodesDTO> findUserPromoCodes(Long id);
}
