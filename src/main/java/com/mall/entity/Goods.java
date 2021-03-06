package com.mall.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Data
@Entity
public class Goods {

    /**
     * 主键，自动增长
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 商品条形码
     */
    @Column(nullable = false, length = 20)
    private String barcode;

    /**
     * 商品图片，多张用,分隔开
     */
    @Column(length = 1000)
    private String img = "";

    /**
     * 商品名称
     */
    @Column(length = 50)
    private String name;

    /**
     * 产品状态，0下架，1上架，2删除
     */
    private Integer status = 0;

    /**
     * 商品精选级别可设置1~5级，1级别最高，每个店铺必备，以后依次降低；1：必选，2：精选，3：普通，4：备选，5：参考。
     */
    private Integer rating = 3;

    /**
     * 参考进货价，单位分
     */
    private Integer purchasePrice;

    /**
     * 参考卖价，单位分
     */
    private Integer salePrice;

    /**
     * 简介
     */
    @Column(length = 500)
    private String shortDescription;

    /**
     * 商品描述，html形式
     */
    @Column(length = 20000)
    private String description;

    /**
     * 所属分类
     */
    @ManyToOne
    private Category category;

    /**
     * 所拥有的供应商报价表
     */
    @OneToMany(mappedBy = "goods")
    private List<GoodsSupplier> goodsSuppliers;


}
