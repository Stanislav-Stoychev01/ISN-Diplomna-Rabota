package com.graduationproject.isn.mappers;

import com.graduationproject.isn.domain.entity.IdentityEntity;
import com.graduationproject.isn.domain.records.request.SignInRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IdentityEntityMapper {

    IdentityEntityMapper INSTANCE = Mappers.getMapper(IdentityEntityMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", source = "password")
    IdentityEntity signUpRequestToIdentityEntity(SignInRequest signUpRequest, String password);
}
