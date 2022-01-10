package com.example.springblog.repositories;

import com.example.springblog.entities.Role;
import com.example.springblog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    void deleteByUsername(String username);

    @Modifying
    @Query("update User set role = :role where username = :username")
    void updateUserRole(@Param("username") String username, @Param("role") Role role);

    @Modifying
    @Query("update User set last_login = :lastLoginTime where username = :username")
    void updateLoginTime(@Param("username") String username, @Param("lastLoginTime") LocalDateTime dateTime);

//    @Modifying
//    @Query("update User set canPost = :canPost where username = :username")
//    void updateUserRights(@Param("username") String username, @Param("canPost") boolean canPost);


}
