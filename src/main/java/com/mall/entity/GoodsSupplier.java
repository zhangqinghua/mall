package com.mall.entity;

import lombok.*;

import javax.persistence.*;

/**
 * 产品报价
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Data
@Entity
public class GoodsSupplier {

    /**
     * 主键，自动增长
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 进货价
     */
    private Integer purchasePrice;

    /**
     * 此报价所对应的供应商
     */
    @ManyToOne
    private Supplier supplier;

    /**
     * 此报价所对应的产品
     */
    @ManyToOne
    private Goods goods;

}
