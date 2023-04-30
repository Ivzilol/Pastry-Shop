package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);

    Optional<Users> findByEmail(String email);

    @Query("select u from Users as u" +
            " join Orders as o on u.id = o.users.id" +
            " where o.keyOrderProduct = :id" +
            " group by o.keyOrderProduct")
    Optional<Users> findUserBayKey(Long id);
    @Query("select u from Users as u" +
            " join Authority as a on u.id = a.users.id" +
            " where a.authority = 'user'")
    List<Users> findAllUsers();
}
