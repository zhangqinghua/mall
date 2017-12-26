package com.mall.service;

import com.mall.repository.GoodsRepository;
import com.mall.entity.Goods;
import com.mall.entity.GoodsSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class GoodsService extends BaseService<Goods> implements GoodsRepository {

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private GoodsSupplierService goodsSupplierService;


    /**
     * 保存产品
     * 1. 判断条形码是否已经存在
     * 2. 分类处理，产品类作为发出端
     * 3. 报价表处理，需要保存产品后
     *
     * @param goods 产品实例
     * @return 产品实例
     */
    @Transactional
    public Goods save(Goods goods) {
        /*
         * 判断是否已经存在此条形码产品
         */
        Goods goodsByBarcode = findByBarcode(goods.getBarcode());
        if (goodsByBarcode != null && goodsByBarcode.getId().equals(goods.getId())) {
            return goods;
        }

        /*
         * 分类预处理
         * 防止前段已经null的分类
         */
        if (goods.getCategory().getId() == null) {
            goods.setCategory(null);
        }

        super.save(goods);

        /*
         * 因为goodsSupplier是一对多关系的发出端，只能由它自己保存这份关系。先查出所有报价，然后比较是删除还是修改
         * 这里删除需要先把goods持久化之后才能进行操作，即goods.getId() != null
         */
        goodsSupplierService.deleteByGoods(goods);
        for (int i = 0; goods.getGoodsSuppliers() != null && i < goods.getGoodsSuppliers().size(); i++) {
            GoodsSupplier goodsSupplier = goods.getGoodsSuppliers().get(i);
            if (goodsSupplier != null && goodsSupplier.getSupplier() != null) {
                goodsSupplier.setGoods(goods);
                goodsSupplierService.save(goodsSupplier);
            }
        }

        return goods;
    }


    /**
     * 根据条形码查找产品
     *
     * @param barcode 条形码
     * @return 产品实例
     */
    public Goods findByBarcode(String barcode) {
        return goodsRepository.findByBarcode(barcode);
    }


    /**
     * 删除产品
     * 1. 删除报价表
     * 2. 删除产品
     *
     * @param id 产品ID
     */
    public void delete(Long id) {
        Goods goods = super.findOne(id);
        if (goods != null) {
            goodsSupplierService.deleteByGoods(goods);
            super.delete(goods);
        }
    }
}
