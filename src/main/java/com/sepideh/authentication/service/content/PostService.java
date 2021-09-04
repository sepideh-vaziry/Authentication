package com.sepideh.authentication.service.content;

import com.sepideh.authentication.dto.content.PostDto;
import com.sepideh.authentication.mapper.content.PostMapper;
import com.sepideh.authentication.model.content.Post;
import com.sepideh.authentication.repository.PostRepository;
import javax.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService {

  private final PostRepository postRepository;
  private final PostMapper postMapper;

  public PostService(PostRepository postRepository, PostMapper postMapper) {
    this.postRepository = postRepository;
    this.postMapper = postMapper;
  }

  public PostDto createPost(PostDto postDto) {
    postDto.setId(null);
    Post post = postRepository.save(postMapper.toModel(postDto));

    return postMapper.toDto(post);
  }

  public PostDto update(PostDto postDto) {
    Post post = findById(postDto.getId());

    post = postRepository.save(postMapper.update(postDto, post));

    return postMapper.toDto(post);
  }

  public PostDto getPost(Long id) {
    Post post = findById(id);

    return postMapper.toDto(post);
  }

  public Page<PostDto> getAllPost(Pageable pageable) {
    return postRepository.findAll(pageable)
        .map(postMapper::toDto);
  }

  public boolean delete(Long postId) {
    postRepository.deleteById(postId);

    return true;
  }

  private Post findById(Long id) {
    return postRepository.findById(id)
        .orElseThrow(EntityNotFoundException::new);
  }

}
