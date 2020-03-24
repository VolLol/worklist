package ru.worklist.entites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Table(name = "works")
@Builder
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = {"id"})

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "work_id_tag_id", joinColumns = @JoinColumn(name = "work_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<TagEntity> tags;

    public WorkEntity() {
    }
}
