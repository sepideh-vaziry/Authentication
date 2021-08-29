package com.sepideh.authentication.mapper.user;

import com.sepideh.authentication.dto.user.RegisterUserDto;
import com.sepideh.authentication.dto.user.UserDto;
import com.sepideh.authentication.model.user.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserDto toDto(User user);

  List<UserDto> toDto(List<User> users);

  User toModel(UserDto userDto);

  User toModel(RegisterUserDto registerUserDto);

  User update(UserDto userDto, @MappingTarget User user);

  User fromId(Long id);

}
