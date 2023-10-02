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
    private Boolean isObjective;
    @Column(columnDefinition = "LONGTEXT")
    private String exImg;
    private String exText;
    private String opt;
    private Integer cnt;

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
    private Problem(String question, String answer, Boolean isObjective, String exImg, String exText, String opt) {
        this.question = question;
        this.answer = answer;
        this.isObjective = isObjective;
        this.exImg = exImg;
        this.exText = exText;
        this.opt = opt;
        this.cnt = 0;
    }

    public static Problem createProblem(String question, String answer, Boolean isObjective, String exImg, String exText, String opt, Exam exam) {
        Problem problem = new Problem(question, answer, isObjective, exImg, exText, opt);
        problem.setExam(exam);
        return problem;
    }

    public void addCnt(){
        this.cnt++;
    }

}
