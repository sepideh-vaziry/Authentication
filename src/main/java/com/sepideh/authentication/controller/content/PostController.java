package com.sepideh.authentication.controller.content;

import com.sepideh.authentication.dto.base.GenericRestResponse;
import com.sepideh.authentication.dto.content.PostDto;
import com.sepideh.authentication.service.content.PostService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/content")
public class PostController {

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @PostMapping
  public GenericRestResponse<PostDto> create(
      @RequestBody @Valid PostDto postDto
  ) {
    return new GenericRestResponse<>(
        postService.createPost(postDto),
        "Success create",
        HttpStatus.CREATED
    );
  }

  @PutMapping
  public GenericRestResponse<PostDto> update(
      @RequestBody @Valid PostDto postDto
  ) {
    return new GenericRestResponse<>(
        postService.update(postDto),
        "Success update",
        HttpStatus.OK
    );
  }

  @GetMapping("/{id}")
  public GenericRestResponse<PostDto> get(
      @PathVariable("id") Long id
  ) {
    return new GenericRestResponse<>(
        postService.getPost(id),
        "Success",
        HttpStatus.OK
    );
  }

  @GetMapping("/all")
  public GenericRestResponse<List<PostDto>> getAll(
      @RequestParam("pageSize") int pageSize,
      @RequestParam("pageNumber") int pageNumber
  ) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Page<PostDto> page = postService.getAllPost(pageable);

    return new GenericRestResponse<>(
        page.getContent(),
        HttpStatus.OK,
        page.getTotalPages(),
        page.getTotalElements()
    );
  }

  @DeleteMapping("/{id}")
  public GenericRestResponse<Boolean> delete(
      @PathVariable("id") Long id
  ) {
    return new GenericRestResponse<>(
        postService.delete(id),
        "Delete successfully",
        HttpStatus.OK
    );
  }

}
