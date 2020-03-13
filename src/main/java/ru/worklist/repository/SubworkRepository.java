package ru.worklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.worklist.entites.SubworkEntity;

public interface SubworkRepository extends JpaRepository<SubworkEntity, Long> {
}
