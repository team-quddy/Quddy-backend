package com.team_quddy.quddy.exam.domain;

import com.team_quddy.quddy.problem.domain.Problem;
import com.team_quddy.quddy.user.domain.Users;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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
    @ColumnDefault("0")
    private Integer scrap;
    private Integer cnt;
    @ColumnDefault("0")
    private String ref;
    @Column(columnDefinition = "LONGTEXT")
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

    public void addScrap() {
        this.scrap++;
    }

    private Exam(String title, String createdDate, Boolean isPublic, Integer cnt, String ref, String thumbnail) {
        this.title = title;
        this.createdDate = createdDate;
        this.isPublic = isPublic;
        this.scrap = 0;
        this.cnt = cnt;
        this.ref = ref;
        this.thumbnail = thumbnail;
    }

    public static Exam createExam(String title, String createdDate, Boolean isPublic, Integer cnt, String ref, String thumbnail, Users users) {
        Exam exam = new Exam(title, createdDate, isPublic, cnt, ref, thumbnail);
        exam.setUsers(users);
        return exam;
    }
}
