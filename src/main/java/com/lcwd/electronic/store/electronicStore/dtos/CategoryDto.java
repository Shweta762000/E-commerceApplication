package com.lcwd.electronic.store.electronicStore.dtos;

import com.lcwd.electronic.store.electronicStore.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private String categoryId;
    @NotBlank(message = "Title must not be blank")
    @Size(min=4 ,message = "title must be minimum 4 characters!")
    private String title;
    @NotBlank
    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    private String coverImage;
   // private List<ProductDto> product=new ArrayList<>();

}
