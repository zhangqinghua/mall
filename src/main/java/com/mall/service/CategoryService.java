package com.mall.service;

import com.mall.dao.CategoryDAO;
import com.mall.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;


@Component
public class CategoryService {

    @Autowired
    private CategoryDAO dao;

    public Page<Category> findAll() {
        Page<Category> page = dao.findAll(new PageRequest(0, 1000, Sort.Direction.ASC, "sort"));

        for (Category category : page) {
            int len = category.getSort().split("-").length;
            String prefix = "";
            for (int i = 1; i < len; i++) {
                prefix += "==";
            }
            category.setName(prefix + category.getName());
        }
        return page;
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

        if (category.getParent() != null) {
            category.setSort(category.getParent().getSort() + "-" + category.getId());
        } else {
            category.setSort(category.getId() + "");
        }

        dao.save(category);

        return true;
    }

    public boolean delete(Long id) {
        Category category = dao.findOne(id);

        // 有子分类，不能删除
        if (category != null && dao.findByParentId(category.getId()).size() !=0) {
            return false;
        }

        dao.delete(id);
        return true;
    }
}
