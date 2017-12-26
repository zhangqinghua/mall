package com.mall.repository;

import com.mall.entity.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {


}