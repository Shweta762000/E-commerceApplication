package com.lcwd.electronic.store.electronicStore.controller;

import com.lcwd.electronic.store.electronicStore.dtos.CategoryDto;
import com.lcwd.electronic.store.electronicStore.dtos.ImageResponse;
import com.lcwd.electronic.store.electronicStore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicStore.dtos.ProductDto;
import com.lcwd.electronic.store.electronicStore.services.CategoryService;
import com.lcwd.electronic.store.electronicStore.services.FileService;
import com.lcwd.electronic.store.electronicStore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private FileService fileService;
    @Value("${category.image.path}")
    private String path;
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable String id, @Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto updatedCategory = categoryService.update(id, categoryDto);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        categoryService.delete(id);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<CategoryDto> getCategorybyid(@PathVariable String id) {
        return new ResponseEntity<>(categoryService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(@RequestParam int pageNumber,
                                                                        @RequestParam int pageSize, @RequestParam String sortBy, @RequestParam String sortDir) {
        return new ResponseEntity<>(categoryService.getall(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadImg(@PathVariable String categoryId, @RequestParam("image") MultipartFile img) throws IOException {
        String s = fileService.uploadFile(img, path, categoryId);
        CategoryDto categoryDto = categoryService.getById(categoryId);
        categoryDto.setCoverImage(s);
        CategoryDto updated = categoryService.update(categoryId, categoryDto);
        ImageResponse imageResponse = ImageResponse.builder().massage("image uploaded sucessful!").httpStatus(HttpStatus.CREATED).status(true).build();
        return new ResponseEntity<>(imageResponse, HttpStatus.OK);

    }

    @GetMapping("/image/{categoryId}")
    public void serveImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {

        CategoryDto categoryDto = categoryService.getById(categoryId);

        InputStream resource = fileService.getResource(path,categoryDto.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());
    }
    //create product with category
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithcategory(
            @PathVariable String categoryId,
            @RequestBody ProductDto productDto
    ){

        ProductDto withCategory = productService.createWithCategory(productDto, categoryId);
        return new ResponseEntity<>(withCategory,HttpStatus.CREATED);
    }
    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> assignCategoryToProduct(
            @PathVariable String productId,
            @PathVariable String categoryId) {

        ProductDto assignedProduct = productService.assignCategoryToProduct(categoryId, productId);

        return new ResponseEntity<>(assignedProduct, HttpStatus.OK);
    }

    @GetMapping("/getbycategory/{categoryId}")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProductsByCategoryId(
            @PathVariable String categoryId,@RequestParam int pageNumber,
            @RequestParam int pageSize, @RequestParam String sortBy, @RequestParam String sortDir ) {
        PageableResponse<ProductDto> productsByCategory = productService.getAllProductsByCategoryId(categoryId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(productsByCategory, HttpStatus.OK);
    }
}

