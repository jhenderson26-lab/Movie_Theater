package org.example.movie_theater.User;

import org.example.movie_theater.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their username.
     * Spring Security uses this during the login process to verify credentials.
     * We return Optional to handle the "user not found" case safely.
     */
    Optional<User> findByUsername(String username);

    /**
     * Useful for checking if a username is already taken during signup.
     */
    boolean existsByUsername(String username);
}