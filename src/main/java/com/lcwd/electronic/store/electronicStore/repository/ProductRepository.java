package com.lcwd.electronic.store.electronicStore.repository;

import com.lcwd.electronic.store.electronicStore.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product,String> {
    Page<Product> findByTitleContaining(String subTitle, Pageable pageable);

    Page<Product>findByliveTrue(Pageable pageable);
    Page<Product> findByCategoryCategoryId(String categoryId, Pageable pageable);

}
