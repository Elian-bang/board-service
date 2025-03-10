package com.example.boardservice.service.impl;

import com.example.boardservice.dto.CategoryDTO;
import com.example.boardservice.mapper.CategoryMapper;
import com.example.boardservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper mapper;

    @Override
    public void register(String accountId, CategoryDTO categoryDTO) {
        if(accountId != null) {
            try {
                mapper.register(categoryDTO);
            }catch (RuntimeException e) {
                log.error("register ERROR! {}", e.getMessage());
                throw new RuntimeException("register ERROR! 게시글 카테고리 등록 메서드를 확인해주세요. " + e.getMessage());
            }
        }else {
            log.error("register ERROR! {}", categoryDTO);
            throw new RuntimeException("register ERROR! 게시글 카테고리 등록 메서드를 확인해주세요. " + categoryDTO);
        }
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        if(categoryDTO != null) {
            try {
                mapper.updateCategory(categoryDTO);
            }catch (RuntimeException e) {
                log.error("update ERROR! {}", categoryDTO);
                throw new RuntimeException("update ERROR! 게시글 카테고리 수정 메서드를 확인해주세요. " + categoryDTO);
            }
        }else {
            log.error("update ERROR! {}", categoryDTO);
            throw new RuntimeException("update ERROR! 게시글 카테고리 수정 메서드를 확인해주세요. " + categoryDTO);
        }
    }

    @Override
    public void delete(int categoryId) {
        if (categoryId != 0) {
            try {
                mapper.deleteCategory(categoryId);
            }catch (RuntimeException e) {
                log.error("delete ERROR! {}", categoryId);
                throw new RuntimeException("delete ERROR! 게시글 카테고리 삭제 메서드를 확인해주세요. " + categoryId);
            }
        } else {
            log.error("delete ERROR! {}", categoryId);
            throw new RuntimeException("delete ERROR! 게시글 카테고리 삭제 메서드를 확인해주세요. " + categoryId);
        }
    }
}
