package com.mall;

import com.mall.dao.CategoryDAO;
import com.mall.dao.GoodsDAO;
import com.mall.dao.SupplierDAO;
import com.mall.entity.Category;
import com.mall.entity.Goods;
import com.mall.entity.GoodsSupplier;
import com.mall.entity.Supplier;
import com.mall.service.CategoryService;
import com.mall.service.GoodsService;
import com.mall.service.SupplierService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MallApplicationTests {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SupplierDAO supplierDAO;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testGoods() {
        Goods goods = goodsService.findOne(4L);

        List<GoodsSupplier> goodsSuppliers = new ArrayList<>();

        GoodsSupplier goodsSupplier = new GoodsSupplier();
        goodsSupplier.setSupplier(supplierDAO.findOne(1l));
        goodsSupplier.setPurchasePrice(100);

        goodsSuppliers.add(goodsSupplier);

        goods.setGoodsSuppliers(goodsSuppliers);

        goodsService.add(goods);

    }


    @Test
    public void testCategory() {
        Category category = new Category();
        category.setId(4l);
        category.setName("测试分类2");

        Category parent = new Category();
        parent.setId(2l);
        category.setParent(parent);
        categoryService.save(category);

    }



}
