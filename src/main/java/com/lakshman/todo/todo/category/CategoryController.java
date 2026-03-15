package com.lakshman.todo.todo.category;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lakshman.todo.common.dto.ApiResponse;
import com.lakshman.todo.todo.category.dto.CategoryReponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@Slf4j
public class CategoryController {

    private final CategoryServiceResolver resolver;

    // @PreAuthorize("hasAuthority('todo:create')")
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryReponseDto>> createTodoCategory(@Valid @RequestBody Long id) {
        log.info("the request coming to the category controller ");
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}