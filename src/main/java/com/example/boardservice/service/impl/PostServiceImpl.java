package com.example.boardservice.service.impl;

import com.example.boardservice.dto.CommentDTO;
import com.example.boardservice.dto.PostDTO;
import com.example.boardservice.dto.TagDTO;
import com.example.boardservice.dto.UserDTO;
import com.example.boardservice.mapper.CommentMapper;
import com.example.boardservice.mapper.PostMapper;
import com.example.boardservice.mapper.TagMapper;
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
    private final CommentMapper commentMapper;
    private final TagMapper tagMapper;

    @Override
    public void register(String userId, PostDTO postDTO) {
        UserDTO memberInfo = userProfileMapper.getUserProfile(userId);
        postDTO.setUserId(memberInfo.getId());
        postDTO.setCreateTime(new Date());

        if (memberInfo != null) {
            postMapper.register(postDTO);
            Integer postId = postDTO.getId();
            for(int i=0; i<postDTO.getTagDTOList().size(); i++) {
                TagDTO tagDTO = postDTO.getTagDTOList().get(i);
                tagMapper.register(tagDTO);
                Integer tagId = tagDTO.getId();
                tagMapper.createPostTag(tagId, postId);

            }
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

    @Override
    public void registerComment(CommentDTO commentDTO) {
        if(commentDTO.getPostId() != 0) {
            commentMapper.register(commentDTO);
        } else {
            log.error("registerComment ERROR! {}", commentDTO);
            throw new RuntimeException("registerComment ERROR! 댓글 등록 메서드를 확인해주세요." + commentDTO);
        }
    }

    @Override
    public void updateComment(CommentDTO commentDTO) {
        if(commentDTO.getId() != 0) {
            commentMapper.updateComments(commentDTO);
        } else {
            log.error("updateComment ERROR!");
            throw new RuntimeException("updateComment ERROR! 댓글 수정 메서드를 확인해주세요.");
        }
    }

    @Override
    public void deletePostComment(int userId, int commendId) {
        if(userId != 0 && commendId != 0) {
            commentMapper.deleteComments(commendId);
        } else {
            log.error("deletePostComment ERROR! {}", commendId);
            throw new RuntimeException("deletePostComment ERROR! 댓글 삭제 메서드를 확인해주세요. " + commendId);
        }
    }

    @Override
    public void registerTag(TagDTO tagDTO) {
        if(tagDTO != null) {
            tagMapper.register(tagDTO);
        } else {
            log.error("registerTag ERROR! {}", tagDTO);
            throw new RuntimeException("registerTag ERROR! 태그 등록 메서드를 확인해주세요." + tagDTO);
        }
    }

    @Override
    public void updateTag(TagDTO tagDTO) {
        if(tagDTO != null) {
            tagMapper.updateTags(tagDTO);
        } else {
            log.error("updateTag ERROR! {}", tagDTO);
            throw new RuntimeException("updateTag ERROR! 태그 수정 메서드를 확인해주세요." + tagDTO);
        }
    }

    @Override
    public void deletePostTag(int userId, int tagId) {
        if(userId != 0 && tagId != 0) {
            tagMapper.deleteTags(tagId);
        } else {
            log.error("deletePostTag ERROR! {}", tagId);
            throw new RuntimeException("deletePostTag ERROR! 태그 삭제 메서드를 확인해주세요." + tagId);
        }
    }
}
