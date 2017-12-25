//package com.mall;
//
//import com.mall.dao.*;
//import com.mall.entity.*;
//import com.mall.service.CategoryService;
//import com.mall.service.GoodsService;
//import com.mall.service.SupplierService;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class MallApplicationTests {
//
//    @Autowired
//    private GoodsService goodsService;
//
//    @Autowired
//    private CategoryService categoryService;
//
//    @Autowired
//    private SupplierDAO supplierDAO;
//
//    public void contextLoads() {
//    }
//
//    public void testGoods() {
////        Goods goods = goodsService.findOne(4L);
////
////        List<GoodsSupplier> goodsSuppliers = new ArrayList<>();
////
////        GoodsSupplier goodsSupplier = new GoodsSupplier();
////        goodsSupplier.setSupplier(supplierDAO.findOne(1l));
////        goodsSupplier.setPurchasePrice(100);
////
////        goodsSuppliers.add(goodsSupplier);
////
////        goods.setGoodsSuppliers(goodsSuppliers);
////
////        goodsService.add(goods);
//
//    }
//
//
////    public void testCategory() {
////        Category category = new Category();
////        category.setId(4l);
////        category.setName("测试分类2");
////
////        Category parent = new Category();
////        parent.setId(2l);
////        category.setParent(parent);
////        categoryService.save(category);
////    }
////
////    @Test
////    public void testSupplier() {
////        Supplier supplier = supplierDAO.findOne(4l);
////        System.out.println(supplier);
////        System.out.println("Hello");
////    }
//
//
//
//}
