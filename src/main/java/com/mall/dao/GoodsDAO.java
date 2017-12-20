package com.mall.dao;

import com.mall.entity.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GoodsDAO extends PagingAndSortingRepository<Goods, Long> {

    Goods findByBarcode(String barcode);

}