package com.lcwd.electronic.store.electronicStore.services;

import com.lcwd.electronic.store.electronicStore.dtos.CategoryDto;
import com.lcwd.electronic.store.electronicStore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicStore.entities.Category;

public interface CategoryService {

    //create
    CategoryDto create(CategoryDto categoryDto);
    //update
    CategoryDto update(String id,CategoryDto categoryDto);
    //delete
    void delete(String id);
    //getsinglecategory
    CategoryDto getById(String id);
    //getall
    PageableResponse<CategoryDto> getall(int pageNumber, int pageSize, String sortBy, String sortDir);
    //search

}
