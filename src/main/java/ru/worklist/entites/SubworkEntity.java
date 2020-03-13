package ru.worklist.entites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "subworks_test")
@Data
@Builder
@AllArgsConstructor
public class SubworkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "work_id")
    private WorkEntity work;

    //StringType
    @Column(name = "summary")
    private String summary;

    @Column(name = "is_done", columnDefinition = "Boolean default false")
    private boolean isDone;

    @Column(name = "is_deleted", columnDefinition = "Boolean default false")
    private boolean isDeleted;

    @CreationTimestamp
    @Column(name = "create_At")
    //как задать timestamp with timezone
    private Timestamp createAt;

    @UpdateTimestamp
    @Column(name = "update_At")
    private Timestamp updateAt;

}