package com.sepideh.authentication.controller.user;

import com.sepideh.authentication.dto.base.GenericRestResponse;
import com.sepideh.authentication.dto.user.RegisterUserDto;
import com.sepideh.authentication.dto.user.UserDto;
import com.sepideh.authentication.service.user.UserService;
import java.nio.file.AccessDeniedException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(value = {"/register", "/register/"}, method = RequestMethod.POST)
  public GenericRestResponse<UserDto> registerUser(
      @RequestBody @Valid RegisterUserDto registerUserDto
  ) {
    return new GenericRestResponse<>(
        userService.registerUser(registerUserDto),
        "Success create",
        HttpStatus.CREATED
    );
  }

  @RequestMapping(value = "", method = RequestMethod.PUT)
  public GenericRestResponse<UserDto> update(
      @RequestBody @Valid UserDto userDto,
      Authentication authentication
  ) throws Exception {
    return new GenericRestResponse<>(
        userService.updateUser(userDto, authentication.getName()),
        "Success update",
        HttpStatus.OK
    );
  }

  @RequestMapping(value = "/all", method = RequestMethod.GET)
  public GenericRestResponse<List<UserDto>> getAllUser(
      @RequestParam("pageSize") int pageSize,
      @RequestParam("pageNumber") int pageNumber
  ) {
    Page<UserDto> page = userService.getAllUser(PageRequest.of(pageNumber, pageSize));

    return new GenericRestResponse<>(
        page.getContent(),
        HttpStatus.OK,
        page.getTotalPages(),
        page.getTotalElements()
    );
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public GenericRestResponse<Boolean> delete(
      @PathVariable("id") long id,
      Authentication authentication
  ) throws AccessDeniedException {

    return new GenericRestResponse<>(
        userService.delete(id, authentication.getName()),
        "Delete successfully",
        HttpStatus.OK
    );
  }

}
