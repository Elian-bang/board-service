package com.example.boardservice.service.impl;

import com.example.boardservice.dto.PostDTO;
import com.example.boardservice.dto.UserDTO;
import com.example.boardservice.mapper.PostMapper;
import com.example.boardservice.mapper.UserProfileMapper;
import com.example.boardservice.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final UserProfileMapper userProfileMapper;

    @Override
    public void register(String userId, PostDTO postDTO) {
        UserDTO memberInfo = userProfileMapper.getUserProfile(userId);
        postDTO.setUserId(memberInfo.getId());
        postDTO.setCreateTime(new Date());

        if (memberInfo != null) {
            postMapper.register(postDTO);
        } else {
            log.error("register ERROR! {}", postDTO);
            throw new RuntimeException("register ERROR! 게시글 등록 메서드를 확인해주세요." + postDTO);
        }
    }

    @Override
    public List<PostDTO> getMyPosts(int accountId) {
        List<PostDTO> postDTOList = postMapper.selectMyPosts(accountId);
        return postDTOList;
    }

    @Override
    public void updatePosts(PostDTO postDTO) {
        if (postDTO != null && postDTO.getId() != 0) {
            postMapper.updatePosts(postDTO);
        } else {
            log.error("updatePosts ERROR! {}", postDTO);
            throw new RuntimeException("updatePosts ERROR! 게시글 수정 메서드를 확인해주세요." + postDTO);
        }

    }

    @Override
    public void deletePosts(int userId, int postId) {
        if (userId != 0 && postId != 0) {
            postMapper.deletePosts(postId);
        } else {
            log.error("deletePosts ERROR! {}", postId);
            throw new RuntimeException("deletePosts ERROR! 게시글 삭제 메서드를 확인해주세요." + postId);
        }
    }
}
