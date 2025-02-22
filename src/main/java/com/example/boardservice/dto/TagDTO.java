package com.example.boardservice.dto;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {
    private int id;
    private String name;
    private String url;
    private int postId;
}
