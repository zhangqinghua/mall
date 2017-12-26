package com.mall.service;

import com.mall.repository.GoodsSupplierRepository;
import com.mall.entity.Goods;
import com.mall.entity.GoodsSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoodsSupplierService extends BaseService<GoodsSupplier> {

    @Autowired
    private GoodsSupplierRepository goodsSupplierRepository;


    /**
     * 根据产品删除报价
     *
     * @param goods 产品
     * @throws Exception 删除失败异常
     */
    public void deleteByGoods(Goods goods) throws Exception {
        goodsSupplierRepository.deleteByGoods(goods);
    }
}
