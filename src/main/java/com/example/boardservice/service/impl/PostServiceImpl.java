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
            try {
                postMapper.register(postDTO);
                Integer postId = postDTO.getId();
                for(int i=0; i<postDTO.getTagDTOList().size(); i++) {
                    TagDTO tagDTO = postDTO.getTagDTOList().get(i);
                    tagMapper.register(tagDTO);
                    Integer tagId = tagDTO.getId();
                    tagMapper.createPostTag(tagId, postId);

                }
            }catch (RuntimeException e) {
                log.error("register ERROR! {}", e.getMessage());
                throw new RuntimeException("register ERROR! 게시글 등록 메서드를 확인해주세요. " + e.getMessage());
            }
        } else {
            log.error("register ERROR! {}", postDTO);
            throw new RuntimeException("register ERROR! 게시글 등록 메서드를 확인해주세요." + postDTO);
        }
    }

    @Override
    public List<PostDTO> getMyPosts(int accountId) {
        List<PostDTO> postDTOList = null;
        try {
            postDTOList = postMapper.selectMyPosts(accountId);
        }catch (RuntimeException e) {
            log.error("getMyPosts ERROR! {}", e.getMessage());
            throw new RuntimeException("getMyPosts ERROR! 내 게시글 조회 메서드를 확인해주세요." + e.getMessage());
        }
        return postDTOList;
    }

    @Override
    public void updatePosts(PostDTO postDTO) {
        if (postDTO != null && postDTO.getId() != 0) {
            try {
                postMapper.updatePosts(postDTO);
            }catch (RuntimeException e) {
                log.error("updatePosts ERROR! {}", postDTO);
                throw new RuntimeException("updatePosts ERROR! 게시글 수정 메서드를 확인해주세요." + postDTO);
            }
        } else {
            log.error("updatePosts ERROR! {}", postDTO);
            throw new RuntimeException("updatePosts ERROR! 게시글 수정 메서드를 확인해주세요." + postDTO);
        }

    }

    @Override
    public void deletePosts(int userId, int postId) {
        if (userId != 0 && postId != 0) {
            try {
                postMapper.deletePosts(postId);
            }catch (RuntimeException e) {
                log.error("deletePosts ERROR! {}", e.getMessage());
                throw new RuntimeException("deletePosts ERROR! 게시글 삭제 메서드를 확인해주세요." + e.getMessage());
            }
        } else {
            log.error("deletePosts ERROR! {}", postId);
            throw new RuntimeException("deletePosts ERROR! 게시글 삭제 메서드를 확인해주세요." + postId);
        }
    }

    @Override
    public void registerComment(CommentDTO commentDTO) {
        if(commentDTO.getPostId() != 0) {
            try {
                commentMapper.register(commentDTO);
            }catch (RuntimeException e) {
                log.error("registerComment ERROR! {}", e.getMessage());
                throw new RuntimeException("registerComment ERROR! 댓글 등록 메서드를 확인해주세요." + e.getMessage());
            }
        } else {
            log.error("registerComment ERROR! {}", commentDTO);
            throw new RuntimeException("registerComment ERROR! 댓글 등록 메서드를 확인해주세요." + commentDTO);
        }
    }

    @Override
    public void updateComment(CommentDTO commentDTO) {
        if(commentDTO.getId() != 0) {
            try {
                commentMapper.updateComments(commentDTO);
            }catch (RuntimeException e) {
                log.error("updateComment ERROR! {}", e.getMessage());
                throw new RuntimeException("updateComment ERROR! 댓글 수정 메서드를 확인해주세요." + e.getMessage());
            }
        } else {
            log.error("updateComment ERROR!");
            throw new RuntimeException("updateComment ERROR! 댓글 수정 메서드를 확인해주세요.");
        }
    }

    @Override
    public void deletePostComment(int userId, int commendId) {
        if(userId != 0 && commendId != 0) {
            try {
                commentMapper.deleteComments(commendId);
            }catch (RuntimeException e) {
                log.error("deletePostComment ERROR! {}", e.getMessage());
                throw new RuntimeException("deletePostComment ERROR! 댓글 삭제 메서드를 확인해주세요." + e.getMessage());
            }
        } else {
            log.error("deletePostComment ERROR! {}", commendId);
            throw new RuntimeException("deletePostComment ERROR! 댓글 삭제 메서드를 확인해주세요. " + commendId);
        }
    }

    @Override
    public void registerTag(TagDTO tagDTO) {
        if(tagDTO != null) {
            try {
                tagMapper.register(tagDTO);
            }catch (RuntimeException e) {
                log.error("registerTag ERROR! {}", e.getMessage());
                throw new RuntimeException("registerTag ERROR! 태그 등록 메서드를 확인해주세요." + e.getMessage());
            }
        } else {
            log.error("registerTag ERROR! {}", tagDTO);
            throw new RuntimeException("registerTag ERROR! 태그 등록 메서드를 확인해주세요." + tagDTO);
        }
    }

    @Override
    public void updateTag(TagDTO tagDTO) {
        if(tagDTO != null) {
            try {
                tagMapper.updateTags(tagDTO);
            }catch (RuntimeException e) {
                log.error("updateTag ERROR! {}", e.getMessage());
                throw new RuntimeException("updateTag ERROR! 태그 수정 메서드를 확인해주세요." + e.getMessage());
            }
        } else {
            log.error("updateTag ERROR! {}", tagDTO);
            throw new RuntimeException("updateTag ERROR! 태그 수정 메서드를 확인해주세요." + tagDTO);
        }
    }

    @Override
    public void deletePostTag(int userId, int tagId) {
        if(userId != 0 && tagId != 0) {
            try {
                tagMapper.deleteTags(tagId);
            }catch (RuntimeException e) {
                log.error("deletePostTag ERROR! {}", e.getMessage());
                throw new RuntimeException("deletePostTag ERROR! 태그 삭제 메서드를 확인해주세요." + e.getMessage());
            }
        } else {
            log.error("deletePostTag ERROR! {}", tagId);
            throw new RuntimeException("deletePostTag ERROR! 태그 삭제 메서드를 확인해주세요." + tagId);
        }
    }
}
