package ru.worklist.entites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tags")
@Builder
@Data
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class TagEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tag")
    private String tagText;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    private Set<WorkEntity> works;

    TagEntity() {
    }


}
