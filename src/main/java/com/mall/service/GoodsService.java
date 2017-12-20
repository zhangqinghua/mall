package com.mall.service;

import com.mall.dao.CategoryDAO;
import com.mall.dao.GoodsDAO;
import com.mall.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class GoodsService {

    @Autowired
    private GoodsDAO goodsDAO;
    @Autowired
    private CategoryDAO categoryDAO;


    public boolean add(Goods goods) {
        if (goods.getCategory() != null) {
            goods.setCategory(categoryDAO.findOne(goods.getCategory().getId()));
        }

        goodsDAO.save(goods);
        return true;
    }

    public Goods findOne(Long id) {
        return goodsDAO.findOne(id);
    }

    public Goods findByBarcode(String barcode) {
        return goodsDAO.findByBarcode(barcode);
    }

}
