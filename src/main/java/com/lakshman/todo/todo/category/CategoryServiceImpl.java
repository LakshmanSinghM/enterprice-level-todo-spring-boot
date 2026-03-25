package com.lakshman.todo.todo.category;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.lakshman.todo.common.dto.ApiResponse;
import com.lakshman.todo.common.dto.ApiResponseWithPagination;
import com.lakshman.todo.common.dto.Pagination;
import com.lakshman.todo.common.utils.ResponseBuilders;
import com.lakshman.todo.exception.ResourceAlreadyExists;
import com.lakshman.todo.todo.category.constant.CategoryCacheNames;
import com.lakshman.todo.todo.category.dto.CategoryListView;
import com.lakshman.todo.todo.category.dto.CategoryReponseDto;
import com.lakshman.todo.todo.category.dto.CategoryRequestDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    @CacheEvict(value = CategoryCacheNames.CATEGORY_LIST, allEntries = true)
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

    @Override
    @Cacheable(value = CategoryCacheNames.CATEGORY_LIST, key = "#page + '-' + #size")
    public ApiResponseWithPagination<List<CategoryReponseDto>> getTodos(int page, int size) {

        Page<CategoryListView> categoryPage = categoryRepository.findAllBy(PageRequest.of(page, size));

        // implement the distributing locking mechanism here RedisLockService so that once expired 10K req dont hit simul..
        List<CategoryReponseDto> list = categoryPage.getContent()
                .stream().map(p -> new CategoryReponseDto(p.getId(), p.getName(), p.getDescription(), p.getImage()))
                .toList();

        return ResponseBuilders.buildSuccessPaginatedResponse(categoryPage, list, "Fetched the category",
                "FETCHED-CATEGORY");
    }
}