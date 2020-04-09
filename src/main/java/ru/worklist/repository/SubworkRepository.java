package ru.worklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.worklist.entites.SubworkEntity;

import java.util.List;

public interface SubworkRepository extends JpaRepository<SubworkEntity, Long> {


    List<SubworkEntity> findAll();

}
