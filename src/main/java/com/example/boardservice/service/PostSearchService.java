package com.example.boardservice.service;

import com.example.boardservice.dto.PostDTO;
import com.example.boardservice.dto.PostSearchRequest;

import java.util.List;

public interface PostSearchService {

    List<PostDTO> getPosts(PostSearchRequest postSearchRequest);

    List<PostDTO> getPostByTag(String tagName);
}
