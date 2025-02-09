package com.example.boardservice.mapper;

import com.example.boardservice.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserProfileMapper {

    UserDTO getUserProfile(@Param("id") String id);

    int deleteUserProfile(@Param("id") String id);

    int register(UserDTO userDTO);

    UserDTO findByIdAndPassword(@Param("id") String id, @Param("password") String password);

    int idCheck(String id);

    int updatePassword(UserDTO userDTO);

}
