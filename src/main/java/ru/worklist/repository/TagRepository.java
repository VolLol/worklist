package ru.worklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.worklist.entites.TagEntity;

import java.util.List;


public interface TagRepository extends JpaRepository<TagEntity, Long> {

    List <TagEntity> findAll();
}
