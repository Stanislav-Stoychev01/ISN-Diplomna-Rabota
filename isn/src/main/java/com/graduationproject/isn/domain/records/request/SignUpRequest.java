package com.graduationproject.isn.domain.records.request;

import lombok.Data;
import lombok.NonNull;

public record SignUpRequest(String username, String password) {
}
