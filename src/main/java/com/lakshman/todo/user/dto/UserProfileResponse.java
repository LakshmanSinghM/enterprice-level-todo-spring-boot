package com.lakshman.todo.user.dto;

import java.time.LocalDateTime;

import com.lakshman.todo.contants.enums.ProviderType;
import com.lakshman.todo.contants.enums.StatusType;

public interface UserProfileResponse {
    Long getId();

    String getEmail();

    String getFirstName();

    String getLastName();

    String getProfileImage();

    ProviderType getProviderType();

    StatusType getStatus();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

    // admin level cols to be needed
    // private Long createdBy;
    // private Long updatedBy;
    // private Boolean isDeleted = false;
    // private Boolean isActive = true;
}