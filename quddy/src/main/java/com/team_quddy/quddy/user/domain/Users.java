package com.team_quddy.quddy.user.domain;

import com.team_quddy.quddy.exam.domain.Exam;
import com.team_quddy.quddy.submit.domain.Submit;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nickname;

    @OneToMany(mappedBy = "users")
    List<Submit> submits = new ArrayList<>();

    @OneToMany(mappedBy = "users")
    List<Exam> exams = new ArrayList<>();

}
