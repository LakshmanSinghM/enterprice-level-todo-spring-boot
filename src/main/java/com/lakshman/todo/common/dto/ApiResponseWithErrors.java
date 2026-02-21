package com.lakshman.todo.common.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseWithErrors<T> extends ApiResponse<T> {
    Map<String, String> errors;
}