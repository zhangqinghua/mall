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
public class Supplier {

    /**
     * 主键，自动增长
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 供应商名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 供应商所拥有的产品报价
     */
    @OneToMany(mappedBy = "supplier")
    private List<GoodsSupplier> goodsSuppliers;
}
