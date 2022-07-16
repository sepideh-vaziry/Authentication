package com.sepideh.authentication.service.user;

import com.sepideh.authentication.dto.user.RegisterUserDto;
import com.sepideh.authentication.dto.user.UserDto;
import com.sepideh.authentication.mapper.user.UserMapper;
import com.sepideh.authentication.model.user.User;
import com.sepideh.authentication.model.user.UserRole;
import com.sepideh.authentication.repository.UserRepository;
import com.sepideh.authentication.repository.UserSpecificationsBuilder;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  public UserService(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      UserMapper userMapper
  ) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.userMapper = userMapper;
  }

  public UserDto registerUser(RegisterUserDto registerUserDto) {
    User user = userMapper.toModel(registerUserDto);
    //Encrypt password
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    //Set role for user
    List<UserRole> roles = new ArrayList<>(1);
    roles.add(UserRole.USER);
    user.setRoles(roles);

    return userMapper.toDto(
        userRepository.save(user)
    );
  }

  public UserDto updateUser(UserDto userDto, String username) throws Exception {
    User user = findById(userDto.getId());

    if (!Objects.equals(user.getUsername(), username)) {
      throw new AccessDeniedException("you do not have permission to perform this action");
    }

    return userMapper.toDto(
        userRepository.save(userMapper.update(userDto, user))
    );
  }

  public Page<UserDto> getAllUser(Pageable pageable, String search) {
    UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
    Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
    Matcher matcher = pattern.matcher(search + ",");

    while (matcher.find()) {
      builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
    }

    Specification<User> specification = builder.build();

    return userRepository.findAll(specification, pageable)
        .map(userMapper::toDto);
  }

  public boolean delete(long id, String username) throws AccessDeniedException {
    User user = findById(id);
    if (!Objects.equals(user.getUsername(), username)) {
      throw new AccessDeniedException("you do not have permission to perform this action");
    }

    user.setEnabled(false);
    userRepository.save(user);

    return true;
  }

  private User findById(long id) throws EntityNotFoundException {
    return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

}
