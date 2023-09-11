package com.team_quddy.quddy.problem.domain;

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
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String question;
    private String answer;
    private String types;
    private String exImg;
    private String exText;
    private String options;

    @OneToMany(mappedBy = "problem")
    List<Submit> submits = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "exam_id")
    private Exam exam;

    // 연관 관계
    public void setExam(Exam exam) {
        if (this.exam != null) {
            exam.getProblems().remove(this);
        }
        this.exam = exam;
        exam.getProblems().add(this);
    }

}
