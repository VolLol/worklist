package ru.worklist.entites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "works_test")
@Builder
@AllArgsConstructor
@Data
public class WorkEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //TextType
    @Column(name = "describe")
    private String describe;

    //StringType
    @Column(name = "summary")
    private String summary;

    //IntegerType
    @Column(name = "remainder_before_sec", columnDefinition = "Integer default 0")
    private Integer remainderBeforeSec;

    //как задать timestamp with timezone
    @Column(name = "plan_finished_date")
    private ZonedDateTime planFinishedDate;

    @Column(name = "is_done", columnDefinition = "Boolean default false")
    private boolean isDone = false;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

    //как задать timestamp with timezone
    @CreationTimestamp
    @Column(name = "create_At")
    private ZonedDateTime createAt;

    @UpdateTimestamp
    @Column(name = "update_At")
    private ZonedDateTime updateAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column
    @OneToMany(mappedBy = "works")
    private List<SubworkEntity> subworks;

    public WorkEntity() {
    }
}
