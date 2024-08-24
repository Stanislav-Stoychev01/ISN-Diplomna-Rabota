package com.graduationproject.isn.domain.records.response;

import lombok.NonNull;

public record SignUpResponse(@NonNull int expiresAfter, @NonNull String token) {
}
