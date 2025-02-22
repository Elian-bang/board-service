package com.example.boardservice.mapper;

import com.example.boardservice.dto.TagDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper {

    public int register(TagDTO tagDTO);

    public void updateTags(TagDTO tagDTO);

    public void deleteTags(int tagId);

    public void createPostTag(int tagId, int postId);
}
