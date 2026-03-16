package com.lakshman.todo.todo.category;

import com.lakshman.todo.common.dto.ApiResponse;
import com.lakshman.todo.todo.category.dto.CategoryReponseDto;
import com.lakshman.todo.todo.category.dto.CategoryRequestDto;

public interface CategoryService {
    ApiResponse<CategoryReponseDto> createCategory(CategoryRequestDto request);
}