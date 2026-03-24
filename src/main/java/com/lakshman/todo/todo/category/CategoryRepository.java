package com.lakshman.todo.todo.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lakshman.todo.todo.category.dto.CategoryListView;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsByName(String name);

    Page<CategoryListView> findAllBy(Pageable pageable);
}