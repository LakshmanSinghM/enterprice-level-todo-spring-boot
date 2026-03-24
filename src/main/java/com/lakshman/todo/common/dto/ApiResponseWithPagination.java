package com.lakshman.todo.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseWithPagination<T> extends ApiResponse<T>  {
    Pagination pagination;
}