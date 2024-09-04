package com.graduationproject.isn.domain.records.response;

import lombok.NonNull;

import java.util.Date;

public record SignUpResponse(@NonNull Date expirationDate, @NonNull String token) {
}
