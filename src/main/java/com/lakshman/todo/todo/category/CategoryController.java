package com.lakshman.todo.todo.category;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lakshman.todo.common.dto.ApiResponse;
import com.lakshman.todo.common.dto.ApiResponseWithPagination;
import com.lakshman.todo.todo.category.dto.CategoryReponseDto;
import com.lakshman.todo.todo.category.dto.CategoryRequestDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/categories")
@Slf4j
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasAuthority('category:create') or hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryReponseDto>> createTodoCategory(
            @Valid @RequestBody CategoryRequestDto categoryRequestDto) {

        log.info("The request coming to the category controller ");

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryRequestDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponseWithPagination<List<CategoryReponseDto>>> getTodos(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        log.info("The request coming to the category controller ");
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.getTodos(page, size));
    }
}