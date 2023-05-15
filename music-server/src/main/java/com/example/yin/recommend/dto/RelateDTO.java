package com.example.yin.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author root
 * @Description:关系数据类
 * @Package com.supermarket.product.dto
 * @date 2021/4/22 15:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelateDTO {
    //用户id
    private Integer userId;
    //业务id
    private Integer productId;
    //指数
    private Integer index;

}
