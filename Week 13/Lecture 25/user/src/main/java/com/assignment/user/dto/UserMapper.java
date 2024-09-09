package com.assignment.user.dto;

import java.util.List;

import org.mapstruct.Mapper;

import com.assignment.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto dto);
    UserDto toDto(User user);
    List<UserDto> toListDto(List<User> listUser);
    
}
