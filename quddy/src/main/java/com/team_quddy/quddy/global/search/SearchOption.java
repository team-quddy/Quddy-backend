package com.team_quddy.quddy.global.search;

import lombok.Getter;

@Getter
public class SearchOption {
    private String keyword;
    private String target;
    private String sort;
    private Integer page;
    private Integer size;
}
