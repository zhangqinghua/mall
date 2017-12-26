package com.mall.dao;

import com.mall.entity.Goods;
import com.mall.entity.GoodsSupplier;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface GoodsSupplierDAO extends PagingAndSortingRepository<GoodsSupplier, Long> {


    void deleteByGoods(Goods goods);

}
