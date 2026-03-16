package com.lakshman.todo.todo.category;

import org.springframework.stereotype.Service;

import com.lakshman.todo.common.dto.ApiResponse;
import com.lakshman.todo.common.utils.ResponseBuilders;
import com.lakshman.todo.exception.ResourceAlreadyExists;
import com.lakshman.todo.todo.category.dto.CategoryReponseDto;
import com.lakshman.todo.todo.category.dto.CategoryRequestDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryDBServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public ApiResponse<CategoryReponseDto> createCategory(CategoryRequestDto request) {

        String name = request.name().toLowerCase().trim();

        if (categoryRepository.existsByName(name)) {
            throw new ResourceAlreadyExists("Category already exists with name: " + name);
        }

        CategoryEntity category = new CategoryEntity();
        category.setName(name);
        category.setDescription(request.description());
        category.setImage(request.image());

        CategoryEntity savedCategory = categoryRepository.save(category);
        CategoryReponseDto responseDto = new CategoryReponseDto();
        responseDto.setName(savedCategory.getName());
        responseDto.setId(savedCategory.getId());
        responseDto.setDescription(savedCategory.getDescription());
        responseDto.setImage(savedCategory.getImage());

        return ResponseBuilders.buildSuccessResponse(responseDto, "Category created successfully", "CREATED");
    }
}