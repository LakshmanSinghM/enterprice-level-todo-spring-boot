package com.lakshman.todo.todo.category;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceResolver {

    private final CategoryDBServiceImpl dbService;
    private final CategoryCacheServiceImpl cacheService;

    public CategoryService resolve(boolean useCache) {
        return useCache ? cacheService : dbService;
    }
}