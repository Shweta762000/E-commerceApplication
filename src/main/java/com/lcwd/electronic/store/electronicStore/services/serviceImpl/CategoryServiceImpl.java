package com.lcwd.electronic.store.electronicStore.services.serviceImpl;

import com.lcwd.electronic.store.electronicStore.dtos.CategoryDto;
import com.lcwd.electronic.store.electronicStore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicStore.entities.Category;
import com.lcwd.electronic.store.electronicStore.helper.Helper;
import com.lcwd.electronic.store.electronicStore.repository.CategoryRepository;
import com.lcwd.electronic.store.electronicStore.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Value("${category.image.path}")
    private  String path;
    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        String Generatedid = UUID.randomUUID().toString();
        categoryDto.setCategoryId(Generatedid);
        Category category = modelMapper.map(categoryDto, Category.class);
        category.setCategoryId(Generatedid);
        Category savedCategory = categoryRepository.save(category);
        CategoryDto cateDto = modelMapper.map(savedCategory,CategoryDto.class);
        return cateDto;
    }

    @Override
    public CategoryDto update(String id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found by this Id"));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatedCategory = categoryRepository.save(category);
        CategoryDto updatedDto = modelMapper.map(updatedCategory, CategoryDto.class);
        return updatedDto;
    }

    @Override
    public void delete(String id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("categoryid is not found0"));
        String coverImage = category.getCoverImage();
        String fullpath=path+coverImage;
        try {
            Path path1= Paths.get(fullpath);
            Files.delete(path1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        categoryRepository.delete(category);
    }

    @Override
    public CategoryDto getById(String id) {
        Category byId = categoryRepository.findById(id).orElseThrow(()->new RuntimeException("Category not found"));
        CategoryDto catdto = modelMapper.map(byId, CategoryDto.class);
        return catdto;
    }

    @Override
    public PageableResponse<CategoryDto> getall(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sortby = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable= PageRequest.of(pageNumber, pageSize,sortby);
        Page<Category> page=categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
        return pageableResponse;
    }
}
