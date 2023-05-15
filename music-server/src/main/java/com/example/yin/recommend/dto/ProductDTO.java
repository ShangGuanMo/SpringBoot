package com.example.yin.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author root
 * @Description:
 * @Package com.supermarket.product.dto
 * @date 2021/4/22 15:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Integer productId;

    private String productName;

    private String productPrice;

}
