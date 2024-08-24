package com.graduationproject.isn.domain.records.request;

import lombok.NonNull;

public record SignInRequest(@NonNull String username, @NonNull String password,
                            @NonNull String email, @NonNull String firstName, @NonNull String lastName) {
}
