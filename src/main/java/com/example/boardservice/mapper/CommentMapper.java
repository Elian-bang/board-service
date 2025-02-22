package com.example.boardservice.mapper;

import com.example.boardservice.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {

    public int register(CommentDTO commentDTO);
    public void updateComments(CommentDTO commentDTO);
    public void deleteComments(int commentId);
}
