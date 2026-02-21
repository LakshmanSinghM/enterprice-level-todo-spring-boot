package com.lakshman.todo.common.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class Pagination {
    private Integer page;
    private Integer size;
    private Long totalElements;
    private Long totalPages;
    private boolean isLast;
}