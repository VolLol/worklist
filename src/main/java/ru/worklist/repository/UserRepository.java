package ru.worklist.repository;

import org.hibernate.type.LongType;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.worklist.entites.UserEntity;

import java.util.List;

public interface UserRepository extends JpaRepository <UserEntity,Long> {
    List <UserEntity> findAll ();

    UserEntity findByUsername(String username);
}
