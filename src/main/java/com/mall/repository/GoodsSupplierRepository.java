package com.mall.repository;

import com.mall.entity.Goods;
import com.mall.entity.GoodsSupplier;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GoodsSupplierRepository extends PagingAndSortingRepository<GoodsSupplier, Long> {
    void deleteByGoods(Goods goods);
}
