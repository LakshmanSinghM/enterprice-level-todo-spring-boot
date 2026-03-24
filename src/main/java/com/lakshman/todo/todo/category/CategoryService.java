package com.lakshman.todo.todo.category;

import java.util.List;

import com.lakshman.todo.common.dto.ApiResponse;
import com.lakshman.todo.common.dto.ApiResponseWithPagination;
import com.lakshman.todo.todo.category.dto.CategoryReponseDto;
import com.lakshman.todo.todo.category.dto.CategoryRequestDto;

public interface CategoryService {
    ApiResponse<CategoryReponseDto> createCategory(CategoryRequestDto request);

    ApiResponseWithPagination<List<CategoryReponseDto>> getTodos(int page, int size);
}