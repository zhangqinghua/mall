package com.mall.service;

import com.mall.dao.CategoryDAO;
import com.mall.dao.GoodsDAO;
import com.mall.dao.GoodsSupplierDAO;
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

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

@Component
public class GoodsService {

    @Autowired
    private GoodsDAO goodsDAO;
    @Autowired
    private CategoryDAO categoryDAO;
    @Autowired
    private SupplierDAO supplierDAO;
    @Autowired
    private GoodsSupplierDAO goodsSupplierDAO;


    public Page<Goods> findAll() {
        return find(new PageRequest(0, 1000, Sort.Direction.ASC, "id"));
    }

    public Page<Goods> find(Pageable pageable) {
        return goodsDAO.findAll(pageable);
    }

    @Transactional
    public boolean add(Goods goods) {
        /*
         * 判断是否已经存在此条形码产品
         */
        Goods goodsByBarcode = goodsDAO.findByBarcode(goods.getBarcode());
        if (goodsByBarcode != null) {
            return false;
        }

        /*
         * 分类预处理
         * 防止前段已经null的分类
         */
        if (goods.getCategory().getId() == null) {
            goods.setCategory(null);
        }

        goodsDAO.save(goods);

        /*
         * 因为goodsSupplier是一对多关系的发出端，只能由它自己保存这份关系。先查出所有报价，然后比较是删除还是修改
         * 这里删除需要先把goods持久化之后才能进行操作，即goods.getId() != null
         */
        goodsSupplierDAO.deleteByGoods(goods);
        for (int i = 0; goods.getGoodsSuppliers() != null && i < goods.getGoodsSuppliers().size(); i++) {
            GoodsSupplier goodsSupplier = goods.getGoodsSuppliers().get(i);
            if (goodsSupplier == null || goodsSupplier.getSupplier() == null) {
                continue;
            } else {
                goodsSupplier.setGoods(goods);
                goodsSupplierDAO.save(goodsSupplier);
            }
        }

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
