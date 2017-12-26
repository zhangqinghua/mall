package com.mall.service;

import com.mall.repository.GoodsSupplierRepository;
import com.mall.entity.Goods;
import com.mall.entity.GoodsSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoodsSupplierService extends BaseService<GoodsSupplier> implements GoodsSupplierRepository {

    @Autowired
    private GoodsSupplierRepository goodsSupplierRepository;

    @Override
    public void deleteByGoods(Goods goods) {
        goodsSupplierRepository.deleteByGoods(goods);
    }
}
