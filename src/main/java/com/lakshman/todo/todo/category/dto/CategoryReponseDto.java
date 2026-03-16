package com.lakshman.todo.todo.category.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryReponseDto {

    Long id;

    String name;

    String description;

    String image;
}