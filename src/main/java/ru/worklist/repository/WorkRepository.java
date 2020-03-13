package ru.worklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.worklist.entites.UserEntity;
import ru.worklist.entites.WorkEntity;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public interface WorkRepository extends JpaRepository<WorkEntity, Long> {
    WorkEntity findBySummaryAndUser(String summary, UserEntity user);

    List<WorkEntity> findByIsDoneAndUser(boolean isDone, UserEntity user);

    @Query("SELECT w from WorkEntity w WHERE w.isDone = :isDone and w.planFinishedDate < :date and w.user = :user")
    List<WorkEntity> findAllBeforeDateByUser(@Param("isDone") boolean isDone, @Param("date") ZonedDateTime date, @Param("user") UserEntity user);

    @Query("SELECT w from WorkEntity w WHERE w.planFinishedDate between '2020-04-02 00:00:00' and " +
            "'2020-04-02 23:59:59' " +
            "and w.user = :user")
    List<WorkEntity> findAllWillDoneTomorrow(@Param("user") UserEntity user);


    @Query("SELECT w from WorkEntity w WHERE w.planFinishedDate between '2020-05-01 00:00:00' and '2020-05-31 23:59:59' and w.user =:user")
    List<WorkEntity> findAllWillDoneInNextMonth(@Param("user") UserEntity user);


    List<WorkEntity> findAllWorksWithSubworks(UserEntity user);
}
