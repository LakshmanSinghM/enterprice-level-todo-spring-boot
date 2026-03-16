package com.lakshman.todo.todo.category;

import org.springframework.stereotype.Service;

import com.lakshman.todo.common.dto.ApiResponse;
import com.lakshman.todo.todo.category.dto.CategoryReponseDto;
import com.lakshman.todo.todo.category.dto.CategoryRequestDto;

@Service
public class CategoryCacheServiceImpl implements CategoryService {

    @Override
    public ApiResponse<CategoryReponseDto> createCategory(CategoryRequestDto request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createCategory'");
    }

    // @Override
    // public CategoryResponseDTO get(Long id){
    // System.out.println("Fetching from CACHE");
    // return new CategoryResponseDTO();
    // }
}