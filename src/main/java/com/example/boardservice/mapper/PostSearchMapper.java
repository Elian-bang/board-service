package com.example.boardservice.mapper;

import com.example.boardservice.dto.PostDTO;
import com.example.boardservice.dto.PostSearchRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostSearchMapper {

    public List<PostDTO> selectPosts(PostSearchRequest postSearchRequest);

    public List<PostDTO> getPostByTag(String tagName);
}
