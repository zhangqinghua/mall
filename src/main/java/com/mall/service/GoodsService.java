package com.mall.service;

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



    public boolean add(Goods goods) {
        goodsDAO.save(goods);
        return true;
    }


}
