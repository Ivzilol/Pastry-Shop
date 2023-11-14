package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.dto.UsersDTO;
import com.example.pastry.shop.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("select new com.example.pastry.shop.model.dto.UsersDTO(" +
            " u.id, u.username, u.firstName, u.lastName, u.email, u.address as address)" +
            " from Users as u" +
            " join Authority as a on u.id = a.users.id" +
            " where a.authority = :auth_u")
    List<UsersDTO> findAllUsers(@Param("auth_u") String auth_u);

    @Query("select u from Users as u" +
            " where u.id = :id")
    Users findByUserId(Long id);

    Users findByVerificationCode(String code);

    @Query("select u from Users as u" +
            " where u.username = :username and u.isValidate = true")
    Optional<Users> findByUsernameAndIsValidate(String username);

    @Query("select new com.example.pastry.shop.model.dto.UsersDTO(" +
            " u.id, u.username, u.firstName, u.lastName, u.email, u.address as address, u.phoneNumber)" +
            " from Users as u" +
            " where u.username = :username")
    Optional<UsersDTO> findUserByUsername(String username);
    @Query("select new com.example.pastry.shop.model.dto.UsersDTO(" +
            " u.id, u.username, u.firstName, u.lastName, u.email, u.address as address, u.phoneNumber)" +
            " from Users as u" +
            " where u.id = :id")
    Optional<UsersDTO> findUserById(Long id);

    @Query("select new com.example.pastry.shop.model.dto.UsersDTO(" +
            " u.id, u.username, u.firstName, u.lastName, u.email, u.address as address, u.phoneNumber)" +
            " from Users as u" +
            " where u.username = :username")
    UsersDTO findCurrentUserByUsername(String username);
}
