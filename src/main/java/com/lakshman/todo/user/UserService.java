package com.lakshman.todo.user;

import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lakshman.todo.common.contants.ResponseOrErrorMessages;
import com.lakshman.todo.common.contants.SystemCustomCode;
import com.lakshman.todo.common.dto.ApiResponse;
import com.lakshman.todo.common.utils.ResponseBuilders;
import com.lakshman.todo.user.dto.UserProfileResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserHelper userHelper;

    public ApiResponse<UserProfileResponse> getUserProfile() {
        Optional<UserProfileResponse> userResponse =
                // userRepository.findProjectedByEmail(userHelper.getUserEmailFromSecurity());
                userRepository.findProjectedByEmail("lslakshman2024@gmail.com");

        if (!userResponse.isPresent())
            throw new UsernameNotFoundException(ResponseOrErrorMessages.USER_NOT_FOUND);

        return ResponseBuilders.buildSuccessResponse(userResponse.get(), ResponseOrErrorMessages.USER_FETCHED,
                SystemCustomCode.USER_FETCHED);
    }
}