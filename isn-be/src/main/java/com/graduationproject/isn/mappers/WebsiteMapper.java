package com.graduationproject.isn.mappers;

import com.graduationproject.isn.domain.entity.WebsiteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WebsiteMapper {

    WebsiteMapper INSTANCE = Mappers.getMapper(WebsiteMapper.class);

    @Mapping(target = "id", ignore = true)
    WebsiteEntity toWebsiteEntity(String url);
}
