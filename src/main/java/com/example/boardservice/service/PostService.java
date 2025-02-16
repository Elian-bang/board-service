package com.example.boardservice.service;

import com.example.boardservice.dto.PostDTO;

import java.util.List;

public interface PostService {

    void register(String userId, PostDTO postDTO);

    List<PostDTO> getMyPosts(int accountId);

    void updatePosts(PostDTO postDTO);

    void deletePosts(int userId, int postId);
}
