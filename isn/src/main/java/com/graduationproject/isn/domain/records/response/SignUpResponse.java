package com.graduationproject.isn.domain.records.response;

import lombok.NonNull;
import org.antlr.v4.runtime.misc.NotNull;

public record SignUpResponse(@NonNull int expiresAfter, @NonNull String token) {
}
