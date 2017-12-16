package com.mall.service;

import com.mall.dao.CategoryDAO;
import com.mall.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


@Component
public class CategoryService {

    @Autowired
    private CategoryDAO dao;

    public Page<Category> find(Pageable pageable) {
        return dao.findAll(pageable);
    }

    public boolean save(Category category) {

        /**
         * 1. 防止前端传过来的parent不为null而id为null保存失败
         * 2. 查询出parent复制缓存只有id
         */
        if (category.getParent() != null) {
            if (category.getParent().getId() == null) {
                category.setParent(null);
            } else {
                category.setParent(dao.findOne(category.getParent().getId()));
            }
        }

        dao.save(category);
        return true;
    }

    public boolean delete(Long id) {
        dao.delete(id);
        return true;
    }
}
