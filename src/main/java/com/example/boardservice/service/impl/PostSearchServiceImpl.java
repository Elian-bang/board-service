package com.example.boardservice.service.impl;

import com.example.boardservice.dto.PostDTO;
import com.example.boardservice.dto.PostSearchRequest;
import com.example.boardservice.exception.BoardServerException;
import com.example.boardservice.mapper.PostSearchMapper;
import com.example.boardservice.service.PostSearchService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class PostSearchServiceImpl implements PostSearchService {

    @Autowired PostSearchMapper mapper;

    @Async
    @Cacheable(value = "getPosts", key = "'getPosts' + #postSearchRequest.getName() + #postSearchRequest.getCategoryId()")
    @Override
    public List<PostDTO> getPosts(PostSearchRequest postSearchRequest) {
        List<PostDTO> postList = null;

        try {
            postList = mapper.selectPosts(postSearchRequest);
        } catch (Exception e) {
            log.error("getPost 메서드 실패: {}", e.getMessage());
            throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return postList;
    }

    @Override
    public List<PostDTO> getPostByTag(String tagName) {
        List<PostDTO> postList = null;
        try {
            postList = mapper.getPostByTag(tagName);
        }catch (RuntimeException e) {
            log.error("getPostByTag 메서드 실패: {}", e.getMessage());
            throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return postList;
    }

}
