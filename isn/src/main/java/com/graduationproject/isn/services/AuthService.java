package com.graduationproject.isn.services;

import com.graduationproject.isn.domain.entity.IdentityEntity;
import com.graduationproject.isn.domain.enums.errorreasons.AuthErrorReason;
import com.graduationproject.isn.domain.records.request.SignInRequest;
import com.graduationproject.isn.domain.records.request.SignUpRequest;
import com.graduationproject.isn.domain.records.response.SignUpResponse;
import com.graduationproject.isn.exceptions.APIException;
import com.graduationproject.isn.mappers.IdentityEntityMapper;
import com.graduationproject.isn.repositories.IdentityRepository;
import com.graduationproject.isn.util.JwtSigningUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final IdentityRepository identityRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signIn(SignInRequest signInRequest) {
        Optional<IdentityEntity> existingUser = identityRepository.findByUsername(signInRequest.username());

        if (existingUser.isPresent()) {
            throw new APIException(AuthErrorReason.INVALID_CREDENTIALS, HttpStatus.CONFLICT);
        }

        String encodedPassword = passwordEncoder.encode(signInRequest.password());
        IdentityEntity identityEntity = IdentityEntityMapper.INSTANCE
            .signUpRequestToIdentityEntity(signInRequest, encodedPassword);

        identityRepository.save(identityEntity);
    }

    @Transactional
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        IdentityEntity existingUser = identityRepository.findByUsername(signUpRequest.username())
            .orElseThrow(() -> new APIException(AuthErrorReason.INVALID_CREDENTIALS, HttpStatus.CONFLICT));

        if (!passwordEncoder.matches(signUpRequest.password(), existingUser.getPassword())) {
            throw new APIException(AuthErrorReason.INVALID_CREDENTIALS, HttpStatus.CONFLICT);
        }

        String jwtToken = JwtSigningUtil.generateJWT(existingUser.getUsername());
        Date validUntil = JwtSigningUtil.extractExpirationOffset(jwtToken);

        return new SignUpResponse(validUntil, jwtToken);
    }

}
