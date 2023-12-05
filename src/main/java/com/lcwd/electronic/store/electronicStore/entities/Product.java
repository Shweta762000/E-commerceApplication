package com.lcwd.electronic.store.electronicStore.entities;

import com.lcwd.electronic.store.electronicStore.dtos.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Products")
public class Product {
    @Id
    private String productId;

    private String title;
    private String description;
    private Integer price;
    private Integer discountedprice;
    private Integer quantity;
    @CreationTimestamp
    private Date addedaDte;
    private boolean live;
    private boolean stock;
    private String productImage;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

}
