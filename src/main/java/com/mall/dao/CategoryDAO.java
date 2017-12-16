package com.mall.dao;

import com.mall.entity.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryDAO extends PagingAndSortingRepository<Category, Long> {
}
