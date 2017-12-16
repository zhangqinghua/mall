package com.mall.entity;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor // 自动生成无参数构造函数
@AllArgsConstructor // 自动生成全参数构造函数
@Data
public class RespBody {

    private boolean status;

    private String msg = "错误，未处理！";

    private Object data;
}
