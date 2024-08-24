package com.graduationproject.isn.controllers;

import com.graduationproject.isn.domain.records.request.SignInRequest;
import com.graduationproject.isn.domain.records.request.SignUpRequest;
import com.graduationproject.isn.domain.records.response.SignUpResponse;
import com.graduationproject.isn.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController("/auth" /* Does not work? */)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<SignUpResponse> signUp(@Valid SignUpRequest signUpRequest) {
        SignUpResponse signUpResponse = authService.signUp(signUpRequest);
        return new ResponseEntity<>(signUpResponse, HttpStatus.OK);
    }

    @PostMapping("/signIn")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignInRequest signInRequest) {
        authService.signIn(signInRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
