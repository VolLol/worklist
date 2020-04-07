package ru.worklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.worklist.entites.UserEntity;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findAll();

    UserEntity findByUsername(String username);

    @Query("SELECT u from UserEntity u where u.id = :iduser")
    UserEntity findByUserId(@Param("iduser") Long userId);
}
