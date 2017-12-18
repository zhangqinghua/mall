package com.mall;

import com.mall.dao.CategoryDAO;
import com.mall.dao.GoodsDAO;
import com.mall.entity.Category;
import com.mall.entity.Goods;
import com.mall.entity.GoodsSupplier;
import com.mall.service.CategoryService;
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
    private GoodsDAO goodsDAO;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testGoods() {
        Goods goods = new Goods();
        goods.setBarcode("123");
        goods.setImg("goods.img");

        goods.setPurchasePrice(100);
        goods.setSalePrice(300);
        goods.setDescription("可口可乐，好喝");

        List<GoodsSupplier> goodsSuppliers = new ArrayList<>();

        GoodsSupplier goodsSupplier = new GoodsSupplier();
        goodsSupplier.setSupplierId(1l);
        goodsSupplier.setPurchasePrice(100);
        goodsSuppliers.add(goodsSupplier);

        goods.setGoodsSuppliers(goodsSuppliers);
        goods.setName("可口可乐1");
        goodsDAO.save(goods);

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
