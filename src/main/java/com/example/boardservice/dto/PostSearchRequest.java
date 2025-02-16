package com.example.boardservice.dto;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostSearchRequest {

    private int id;
    private String name;
    private String contents;
    private int views;
    private int categoryId;
    private int userId;
    private CategoryDTO.SortStatus sortStatus;
}
