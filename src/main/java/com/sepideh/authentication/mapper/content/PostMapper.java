package com.sepideh.authentication.mapper.content;

import com.sepideh.authentication.dto.content.PostDto;
import com.sepideh.authentication.dto.content.PostSimpleDto;
import com.sepideh.authentication.model.content.Post;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostMapper {

  PostDto toDto(Post post);

  PostSimpleDto toSimpleDto(Post post);

  Post toModel(PostDto postDto);

  Post toModel(PostSimpleDto postSimpleDto);

  List<PostSimpleDto> toListDto(List<Post> postList);

  Post update(PostDto postDto, @MappingTarget Post post);

}
