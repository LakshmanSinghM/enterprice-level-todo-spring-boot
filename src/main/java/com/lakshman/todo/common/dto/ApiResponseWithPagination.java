package com.lakshman.todo.common.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseWithPagination<T> extends ApiResponse<T> {
    Pagination pagination;
}