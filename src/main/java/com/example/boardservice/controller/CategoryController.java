package com.example.boardservice.controller;

import com.example.boardservice.aop.LoginCheck;
import com.example.boardservice.aop.LoginCheck.UserType;
import com.example.boardservice.dto.CategoryDTO;
import com.example.boardservice.dto.CategoryDTO.SortStatus;
import com.example.boardservice.service.impl.CategoryServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Log4j2
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public void registerCategory(String accountId, @RequestBody CategoryDTO categoryDTO) {
        categoryService.register(accountId, categoryDTO);
    }

    @PutMapping("{categoryId}")
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public void updateCategories(String accountId, @PathVariable(name = "categoryId") int categoryId,
                                 @RequestBody CategoryRequest categoryRequest) {
        CategoryDTO categoryDTO = new CategoryDTO(categoryId, categoryRequest.getName(), SortStatus.NEWEST, 10, 1);
        categoryService.update(categoryDTO);
    }

    @DeleteMapping("{categoryId}")
    @LoginCheck(type = UserType.ADMIN)
    public void deleteCategory(String accountId, @PathVariable(name = "categoryId") int categoryId) {
        categoryService.delete(categoryId);
        }

    // --- request 객체
    @Getter
    @Setter
    private static class CategoryRequest {
        private int id;
        private String name;

    }



}
