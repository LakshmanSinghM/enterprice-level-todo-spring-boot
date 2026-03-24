package com.lakshman.todo.todo.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor 
public class CategoryReponseDto {

    Long id;

    String name;

    String description;

    String image;
}