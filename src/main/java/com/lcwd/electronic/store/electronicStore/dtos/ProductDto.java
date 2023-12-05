package com.lcwd.electronic.store.electronicStore.dtos;

import com.lcwd.electronic.store.electronicStore.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto { private String productId;
    @NotBlank(message = "Enter product title!")
    private String title;
    @NotBlank(message = "Write product Description!")
    private String description;

    @NotBlank(message = "Enter Amount!")
    private Integer price;
    private Integer discountedprice;
    private Integer quantity;
    private Date addedaDte;
    private boolean live;
    private boolean stock;
    private String productImage;
    private CategoryDto category;
}
