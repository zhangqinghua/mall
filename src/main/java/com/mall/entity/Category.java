package com.mall.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor // 自动生成无参数构造函数
@AllArgsConstructor // 自动生成全参数构造函数
@Data //  自动为所有字段添加@ToString, @EqualsAndHashCode, @Getter方法，为非final字段添加@Setter,和@RequiredArgsConstructor!
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parentId", referencedColumnName = "id")
    private Category parent;

    @Column(nullable = false, length = 50)
    private String name;

}
