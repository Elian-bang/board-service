package com.example.boardservice.mapper;

import com.example.boardservice.dto.PostDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {

    void register(PostDTO postDTO);

    List<PostDTO> selectMyPosts(int accountId);

    void updatePosts(PostDTO postDTO);

    void deletePosts(int postId);
}
