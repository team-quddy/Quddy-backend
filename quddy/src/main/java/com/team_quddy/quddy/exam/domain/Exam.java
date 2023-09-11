package com.team_quddy.quddy.exam.domain;

import com.team_quddy.quddy.problem.domain.Problem;
import com.team_quddy.quddy.user.domain.Users;
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
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String createdDate;
    private Boolean isPublic;
    private Integer scrap;
    private Integer cnt;
    private Integer ref;
    private String thumbnail;

    @OneToMany(mappedBy = "exam")
    List<Problem> problems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "users_id")
    private Users users;

    // 연관 관계 설정
    public void setUsers(Users users) {
        if (this.users != null)
            this.users.getExams().remove(this);
        this.users = users;
        users.getExams().add(this);
    }
}
