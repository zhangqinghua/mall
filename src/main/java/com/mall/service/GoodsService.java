package com.mall.service;

import com.mall.dao.CategoryDAO;
import com.mall.dao.GoodsDAO;
import com.mall.dao.SupplierDAO;
import com.mall.entity.Goods;
import com.mall.entity.GoodsSupplier;
import com.mall.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class GoodsService {

    @Autowired
    private GoodsDAO goodsDAO;
    @Autowired
    private CategoryDAO categoryDAO;
    @Autowired
    private SupplierDAO supplierDAO;


    public Page<Goods> findAll() {
        return find(new PageRequest(0, 1000, Sort.Direction.ASC, "id"));
    }

    public Page<Goods> find(Pageable pageable) {
        return goodsDAO.findAll(pageable);
    }

    public boolean add(Goods goods) {
        if (goods.getCategory() != null && goods.getCategory().getId() != null) {
            goods.setCategory(categoryDAO.findOne(goods.getCategory().getId()));
        } else {
            goods.setCategory(null);
        }

        if (goods.getGoodsSuppliers() != null) {
            for (int i = 0; i < goods.getGoodsSuppliers().size(); i++) {
                GoodsSupplier goodsSupplier = goods.getGoodsSuppliers().get(i);
                if (goodsSupplier == null || goodsSupplier.getSupplier() == null) {
                    goods.getGoodsSuppliers().remove(goodsSupplier);
                    i--;
                } else {
                    goodsSupplier.setSupplier(supplierDAO.findOne(goodsSupplier.getSupplier().getId()));
                }
            }
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


    public boolean delete(Long id) {
        goodsDAO.delete(id);
        return true;
    }
}
