package com.mall.dao;

import com.mall.entity.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CategoryDAO extends PagingAndSortingRepository<Category, Long> {

    List<Category> findByParentId(Long parentId);
}
