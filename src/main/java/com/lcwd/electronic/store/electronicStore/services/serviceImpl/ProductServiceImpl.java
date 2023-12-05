package com.lcwd.electronic.store.electronicStore.services.serviceImpl;

import com.lcwd.electronic.store.electronicStore.dtos.CategoryDto;
import com.lcwd.electronic.store.electronicStore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicStore.dtos.ProductDto;
import com.lcwd.electronic.store.electronicStore.entities.Category;
import com.lcwd.electronic.store.electronicStore.entities.Product;
import com.lcwd.electronic.store.electronicStore.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.electronicStore.helper.Helper;
import com.lcwd.electronic.store.electronicStore.repository.CategoryRepository;
import com.lcwd.electronic.store.electronicStore.repository.ProductRepository;
import com.lcwd.electronic.store.electronicStore.services.ProductService;
import org.modelmapper.Converters;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelmapper;
    @Value("${product.image.path}")
    private String path;
    @Autowired
    private CategoryRepository catagoryRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        String id = UUID.randomUUID().toString();
        productDto.setProductId(id);
        Product product = modelmapper.map(productDto, Product.class);
        product.setProductId(id);
        Product savedproduct = productRepository.save(product);
        return modelmapper.map(savedproduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("product not found with this id"));
        product.setDescription(productDto.getDescription());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setQuantity(product.getQuantity());
        product.setDiscountedprice(productDto.getDiscountedprice());
        product.setAddedaDte(productDto.getAddedaDte());
        product.setProductImage(productDto.getProductImage());
        Product updatedproduct = productRepository.save(product);
        ProductDto pdto = modelmapper.map(updatedproduct, ProductDto.class);
        return pdto;
    }

    @Override
    public ProductDto getProduct(String productId) {
        return modelmapper.map(productRepository.findById(productId).orElseThrow(()->new RuntimeException("Product id is not found..")), ProductDto.class);
    }

    @Override
    public void deleteProduct(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException());
        String imageName = product.getProductImage();
        String fullPath= path+imageName;
        try{
            Path path1= Paths.get(fullPath);
            Files.delete(path1);
        } catch (NoSuchFileException ex){
            ex.printStackTrace();

        } catch (IOException ex){
            ex.printStackTrace();
        }
        productRepository.delete(product);

    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageSize, int pageNumber, String sortBy, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase("descending")?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<Product> page=productRepository.findAll(pageable);
        PageableResponse<ProductDto> pageableResponse1= Helper.getPageableResponse(page, ProductDto.class);
        return pageableResponse1;
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(boolean b, int pageSize, int pageNumber, String sortBy, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase("descending")?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<Product> page=productRepository.findByliveTrue(pageable);
        PageableResponse<ProductDto> pageableResponse1= Helper.getPageableResponse(page, ProductDto.class);
        return pageableResponse1;

    }

    @Override
    public PageableResponse<ProductDto> searchProductByTitle(String subTitle, int pageSize, int pageNumber, String sortBy, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase("descending")?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<Product> page=productRepository.findByTitleContaining(subTitle,pageable);
        PageableResponse<ProductDto> pageableResponse1= Helper.getPageableResponse(page, ProductDto.class);
        return pageableResponse1;

    }
    public ProductDto createWithCategory(ProductDto productDto,String categoryId){
        Category category = catagoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
        String id = UUID.randomUUID().toString();
        productDto.setProductId(id);
        Product product = modelmapper.map(productDto, Product.class);
        product.setCategory(category);
        product.setAddedaDte(new Date());
        product.setProductId(id);
        Product savedproduct = productRepository.save(product);
        return modelmapper.map(savedproduct, ProductDto.class);
    }
   public ProductDto assignCategoryToProduct(String categoryId,String productId){
       Category category = catagoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Given Id is Not Found"));

       Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Given Id is Not Found"));
       product.setCategory(category);
       Product saved = productRepository.save(product);

       return  modelmapper.map(saved, ProductDto.class);

   }

    @Override
    public PageableResponse<ProductDto> getAllProductsByCategoryId(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Page<Product> page = productRepository.findByCategoryCategoryId(categoryId, PageRequest.of(pageNumber, pageSize, Sort.by(sortDir.equalsIgnoreCase("descending") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy)));
        return Helper.getPageableResponse(page, ProductDto.class);
    }



}
