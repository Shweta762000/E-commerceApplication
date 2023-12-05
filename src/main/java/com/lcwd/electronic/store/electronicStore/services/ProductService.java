package com.lcwd.electronic.store.electronicStore.services;

import com.lcwd.electronic.store.electronicStore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicStore.dtos.ProductDto;
import com.lcwd.electronic.store.electronicStore.entities.Category;

public interface ProductService {

        ProductDto createProduct(ProductDto productDto);

        ProductDto updateProduct(ProductDto productDto ,String productId);

        ProductDto getProduct(String productId);

        void deleteProduct(String productId);
        PageableResponse<ProductDto> getAll(int pageSize, int pageNumber, String sortBy, String sortDir);

        PageableResponse<ProductDto>getAllLive(boolean b, int pageSize, int pageNumber, String sortBy, String sortDir);

        PageableResponse<ProductDto>searchProductByTitle(String subTitle,int pageSize, int pageNumber, String sortBy, String sortDir);

        ProductDto createWithCategory(ProductDto productDto,String categoryId);
        ProductDto assignCategoryToProduct(String categoryId,String productId);

        PageableResponse<ProductDto> getAllProductsByCategoryId(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir);



}
