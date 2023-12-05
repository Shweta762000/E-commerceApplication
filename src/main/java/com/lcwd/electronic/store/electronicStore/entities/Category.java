package com.lcwd.electronic.store.electronicStore.entities;

import com.lcwd.electronic.store.electronicStore.dtos.ProductDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Category")
public class Category {
    @Id
    @Column(name="category_id")
    private String categoryId;
    @Column(name = "category_title",length = 10,nullable = false)
    private String title;
    @Column(name = "category_description")
    private String Description;
    @Column(name = "Category_cover_image")
    private String coverImage;
    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Product> product=new ArrayList<>();
}
