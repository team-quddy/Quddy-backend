package com.team_quddy.quddy.exam.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
public class ExamDto<T> {
    private String title;
    private List<T> problems;
}
