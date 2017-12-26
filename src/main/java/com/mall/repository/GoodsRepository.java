package com.mall.repository;

import com.mall.entity.Goods;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GoodsRepository extends PagingAndSortingRepository<Goods, Long> {

    Goods findByBarcode(String barcode) throws Exception;

}