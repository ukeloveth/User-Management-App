package com.assessment.user.management.app.repository;

import com.assessment.user.management.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    Boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.name = :name")
     User getUserByUsername(@Param("name") String name);
}
