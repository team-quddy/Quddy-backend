package com.team_quddy.quddy.submit.domain;

import com.team_quddy.quddy.problem.domain.Problem;
import com.team_quddy.quddy.user.domain.Users;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Submit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String result;
    private boolean isCorrect;
    private String createdDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "users_id")
    private Users users;

    public Submit(String result, boolean isCorrect, Problem problem, Users users) {
        this.result = result;
        this.isCorrect = isCorrect;
        setProblem(problem);
        setUsers(users);
    }

    // 연관 관계
    public void setProblem(Problem problem) {
        if (this.problem != null) {
            problem.getSubmits().remove(this);
        }
        this.problem = problem;
        problem.getSubmits().add(this);
    }

    public void setUsers(Users users) {
        if (this.users != null) {
            users.getSubmits().remove(this);
        }
        this.users = users;
        users.getSubmits().add(this);
    }
    @PrePersist
    public void preCreatedDate() {
        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
