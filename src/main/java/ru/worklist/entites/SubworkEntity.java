package ru.worklist.entites;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "subworks")
@Builder
@Getter
@Setter
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

    SubworkEntity() {
    }

}
