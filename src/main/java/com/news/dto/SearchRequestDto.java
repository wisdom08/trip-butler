package com.news.dto;

import lombok.Getter;

@Getter
public class SearchRequestDto {
    private String[] section;
    private String[] press;
    private String searchTerm;
    private String startDate;
    private String endDate;
}
