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
public class Category {

    /**
     * 主键，自动增长
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 分类名称
     */
    @Column(nullable = false, length = 50)
    private String name;

    /**
     * 排序字符串
     * <p>
     * 手机（1）-》苹果手机（2）
     * 则苹果手机.sort='1-2'
     * 通过order by sort 可以很容易实现多级分类查询
     */
    @Column(nullable = false, length = 20)
    private String sort = "";

    /**
     * 分类目录
     */
    @ManyToOne
    private Category parent;

    /**
     * 子类目录
     */
    @OneToMany(mappedBy = "parent")
    private List<Category> children;

    /**
     * 分类下所有的产品
     */
    @OneToMany(mappedBy = "category")
    private List<Goods> goodss;
}
