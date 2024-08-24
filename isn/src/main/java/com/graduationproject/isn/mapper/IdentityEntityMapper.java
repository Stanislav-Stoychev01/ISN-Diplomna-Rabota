package com.graduationproject.isn.mapper;

import com.graduationproject.isn.domain.entity.IdentityEntity;
import com.graduationproject.isn.domain.records.request.SignInRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IdentityEntityMapper {

    IdentityEntityMapper INSTANCE = Mappers.getMapper(IdentityEntityMapper.class);

    @Mapping(target = "id", ignore = true)
    IdentityEntity signUpRequestToIdentityEntity(SignInRequest signUpRequest, String password);
}
