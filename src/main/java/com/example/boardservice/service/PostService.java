package com.example.boardservice.service;

import com.example.boardservice.dto.CommentDTO;
import com.example.boardservice.dto.PostDTO;
import com.example.boardservice.dto.TagDTO;

import java.util.List;

public interface PostService {

    void register(String userId, PostDTO postDTO);

    List<PostDTO> getMyPosts(int accountId);

    void updatePosts(PostDTO postDTO);

    void deletePosts(int userId, int postId);

    void registerComment(CommentDTO commentDTO);

    void updateComment(CommentDTO commentDTO);

    void deletePostComment(int userId, int commendId);

    void registerTag(TagDTO tagDTO);

    void updateTag(TagDTO tagDTO);

    void deletePostTag(int userId, int tagId);
}
