package ru.worklist.entites;

import lombok.*;
import org.hibernate.type.LongType;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users_test")
@Builder
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "crypt_password")
    private String cryptPassword;

    public UserEntity() {
    }


}