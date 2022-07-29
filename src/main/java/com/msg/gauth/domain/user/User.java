package com.msg.gauth.domain.user;

import com.msg.gauth.domain.enums.Gender;
import com.msg.gauth.domain.enums.UserRole;
import com.msg.gauth.domain.enums.UserState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer grade;

    private Integer classNum;

    private Integer num;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private UserState state;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}
