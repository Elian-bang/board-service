package com.example.boardservice.controller;

import com.example.boardservice.dto.PostDTO;
import com.example.boardservice.dto.PostSearchRequest;
import com.example.boardservice.service.impl.PostSearchServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post/search")
@Log4j2
@RequiredArgsConstructor
public class PostSearchController {

    private final PostSearchServiceImpl postSearchService;

    @PostMapping
    public PostSearchResponse searchPosts(@RequestBody PostSearchRequest postSearchRequest) {
        List<PostDTO> postDTOList = postSearchService.getPosts(postSearchRequest);
        return new PostSearchResponse(postDTOList);
    }

    @GetMapping
    public PostSearchResponse searchByTagName(String tagName) {
        List<PostDTO> postDTOList = postSearchService.getPostByTag(tagName);
        return new PostSearchResponse(postDTOList);
    }

    // -- response 객체 --

    @Getter
    @AllArgsConstructor
    private static class PostSearchResponse {
        private List<PostDTO> postDTOList;
    }
}
