package ru.worklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.worklist.entites.SubworkEntity;
import ru.worklist.entites.UserEntity;

import java.util.List;

public interface SubworkRepository extends JpaRepository<SubworkEntity, Long> {


    List<SubworkEntity> findAll();

}
