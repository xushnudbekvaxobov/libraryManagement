package project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.enums.UserRole;
import project.enums.UserStatus;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surname", nullable = false)
    private String surname;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "username",unique = true,nullable = false)
    private String username;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "role",nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    @OneToMany(mappedBy = "userEntity")
    private List<BorrowEntity> borrowEntity;

}
