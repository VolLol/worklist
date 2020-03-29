package ru.worklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.worklist.entites.UserEntity;
import ru.worklist.entites.WorkEntity;

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


    @Query("SELECT w from WorkEntity w where w.user = :user and w.id in :ids")
    List<WorkEntity> findByIds(@Param("user") UserEntity user, @Param("ids") List<Long> ids);

    @Query("SELECT w from WorkEntity w where w.user = :user and w.planFinishedDate between '2020-04-02 00:00:00' and :timeEnd ")
    List<WorkEntity> findAllForNdays(@Param("user") UserEntity user, @Param("timeEnd") ZonedDateTime timeEnd);

    @Query("SELECT distinct w FROM WorkEntity w JOIN w.tags where w.user = :user")
    List <WorkEntity> findAllWorksWithTags(@Param("user") UserEntity user);


}