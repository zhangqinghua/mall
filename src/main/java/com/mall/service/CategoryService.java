package com.mall.service;

import com.mall.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;


@Component
public class CategoryService extends BaseService<Category> {


    /**
     * 查找所有分类
     * 1. 只要一页就够了
     * 2. 分类名称要有层次关系
     *
     * @return 分类页实例
     */
    public Page<Category> findAll() {
        Page<Category> page = super.findAll(new PageRequest(0, 1000, Sort.Direction.ASC, "sort"));
        for (Category category : page) {
            int len = category.getSort().split("-").length;
            StringBuilder prefix = new StringBuilder();
            for (int i = 1; i < len; i++) {
                prefix.append("==");
            }
            category.setName(prefix + category.getName());
        }
        return page;
    }

    /**
     * 保存分类
     * 1. 因为前端传了Parent过来，需要作null判断
     * 2. sort排序生成
     *
     * @param category 分类实例
     * @return 分类实例
     */
    @Override
    public Category save(Category category) {
        if (category.getParent().getId() != null) {
            category.setParent(super.findOne(category.getParent().getId()));
        } else {
            category.setParent(null);
        }


        super.save(category);

        if (category.getParent() != null) {
            category.setSort(category.getParent().getSort() + "-" + category.getId());
        } else {
            category.setSort(category.getId() + "");
        }

        super.save(category);

        return category;
    }

    /**
     * 删除分类
     * 如果此分类有子分类，无法删除
     * 如果此分类有产品，无法删除
     *
     * @param id 分类编号
     */
    @Override
    public void delete(Long id) {
        Category category = super.findOne(id);

        if (category.getChildren().size() != 0) {
            return;
        }

        if (category.getGoodss().size() != 0) {
            return;
        }

        super.delete(category);
    }
}
