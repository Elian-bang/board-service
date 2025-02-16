package com.example.boardservice.service;

import com.example.boardservice.dto.CategoryDTO;

public interface CategoryService {

    void register(String accountId, CategoryDTO categoryDto);

    void update(CategoryDTO categoryDTO);

    void delete(int categoryId);

}
