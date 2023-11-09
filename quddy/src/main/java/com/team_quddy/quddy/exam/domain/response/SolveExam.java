package com.team_quddy.quddy.exam.domain.response;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class SolveExam implements Comparable<SolveExam>{
    private Integer id;
    private String title;
    private String thumbnail;
    private String date;

    @Override
    public int compareTo(SolveExam o) {
        return Integer.compare(this.id, o.id);
    }
}