package com.example.boardservice.controller;

import com.example.boardservice.aop.LoginCheck;
import com.example.boardservice.aop.LoginCheck;
import com.example.boardservice.dto.CommentDTO;
import com.example.boardservice.dto.PostDTO;
import com.example.boardservice.dto.TagDTO;
import com.example.boardservice.dto.UserDTO;
import com.example.boardservice.dto.response.CommonResponse;
import com.example.boardservice.service.impl.PostServiceImpl;
import com.example.boardservice.service.impl.UserServiceImpl;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.example.boardservice.aop.LoginCheck.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Log4j2
public class PostController {

    private final UserServiceImpl userService;
    private final PostServiceImpl postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = UserType.USER)
    public ResponseEntity<CommonResponse<PostDTO>> registerPost(String userId, @RequestBody PostDTO postDTO) {
        postService.register(userId, postDTO);
        CommonResponse commonResponse = new CommonResponse(HttpStatus.CREATED, "SUCCESS", "registerPost", postDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @GetMapping("my-posts")
    @LoginCheck(type = UserType.USER)
    public ResponseEntity<CommonResponse<PostDTO>> getMyPosts(String accountId) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        List<PostDTO> myPosts = postService.getMyPosts(memberInfo.getId());
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "SUCCESS", "getMyPosts", myPosts);
        return ResponseEntity.ok(commonResponse);
    }

    @PatchMapping("{postId}")
    @LoginCheck(type = UserType.USER)
    public ResponseEntity<CommonResponse<PostResponse>> updatePosts(String accountId, @PathVariable(name = "postId") int postId,
                                                               @RequestBody PostRequest postRequest) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        PostDTO postDTO = PostDTO.builder()
                .id(postId)
                .name(postRequest.getName())
                .contents(postRequest.getContents())
                .views(postRequest.getViews())
                .categoryId(postRequest.getCategoryId())
                .userId(memberInfo.getId())
                .fileId(postRequest.getFileId())
                .updateTime(new Date())
                .build();

        postService.updatePosts(postDTO);

        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "SUCCESS", "updatePosts", postDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("{postId}")
    @LoginCheck(type = UserType.USER)
    public ResponseEntity<CommonResponse<PostDeleteRequest>> deletePosts(String accountId, @PathVariable(name = "postId") int postId) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        postService.deletePosts(memberInfo.getId(), postId);
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "SUCCESS", "deletePosts", PostDeleteRequest.builder().id(postId).accountId(accountId).build());
        return ResponseEntity.ok(commonResponse);
    }

    @PostMapping("comments")
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = UserType.USER)
    public ResponseEntity<CommonResponse<PostDTO>> registerPostComment(String accountId, @RequestBody CommentDTO commentDTO) {
        postService.registerComment(commentDTO);
        CommonResponse commonResponse = new CommonResponse(HttpStatus.CREATED, "SUCCESS", "registerPostComment", commentDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @PatchMapping("comments/{commentId}")
    @LoginCheck(type = UserType.USER)
    public ResponseEntity<CommonResponse<PostDTO>> updatePostComment(String accountId, @PathVariable(name = "commentId") int commentId,
                                                               @RequestBody CommentDTO commentDTO) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        if(memberInfo != null) {
            postService.updateComment(commentDTO);
        }
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "SUCCESS", "updatePostComment", commentDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("comments/{commentId}")
    @LoginCheck(type = UserType.USER)
    public ResponseEntity<CommonResponse<PostDTO>> deletePostComment(String accountId, @PathVariable(name = "commentId") int commentId,
                                                                     @RequestBody CommentDTO commentDTO) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        postService.deletePostComment(memberInfo.getId(), commentId);
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "SUCCESS", "deletePostComment", commentDTO);
        return ResponseEntity.ok(commonResponse);
    }

    // -- tags -- //
    @PostMapping("tags")
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = UserType.USER)
    public ResponseEntity<CommonResponse<TagDTO>> registerPostTag(String accountId, @RequestBody TagDTO tagDTO) {
        postService.registerTag(tagDTO);
        CommonResponse commonResponse = new CommonResponse(HttpStatus.CREATED, "SUCCESS", "registerPostTag", tagDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @PatchMapping("tags/{tagId}")
    @LoginCheck(type = UserType.USER)
    public ResponseEntity<CommonResponse<TagDTO>> updatePostTag(String accountId, @PathVariable(name = "tagId") int tagId,
                                                               @RequestBody TagDTO tagDTO) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        if(memberInfo != null) {
            postService.updateTag(tagDTO);
        }
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "SUCCESS", "updatePostTag", tagDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("tags/{tagId}")
    @LoginCheck(type = UserType.USER)
    public ResponseEntity<CommonResponse<TagDTO>> deletePostTag(String accountId, @PathVariable(name = "tagId") int tagId,
                                                               @RequestBody TagDTO tagDTO) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        postService.deletePostTag(memberInfo.getId(), tagId);
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "SUCCESS", "deletePostTag", tagDTO);
        return ResponseEntity.ok(commonResponse);
    }


    // -- response 객체 -- //

    @Getter
    @AllArgsConstructor
    private static class PostResponse {
        private List<PostDTO> postDTOS;
    }

    // -- request 객체 -- //

    @Getter
    @Setter
    private static class PostRequest {
        private String name;
        private String contents;
        private int views;
        private int categoryId;
        private int userId;
        private int fileId;
        private Date updateTime;
    }

    @Builder
    @Setter
    @Getter
    private static class PostDeleteRequest {
        private int id;
        private String accountId;
    }

}
