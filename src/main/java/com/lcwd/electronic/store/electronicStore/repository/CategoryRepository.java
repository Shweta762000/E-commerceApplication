package com.lcwd.electronic.store.electronicStore.repository;

import com.lcwd.electronic.store.electronicStore.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,String> {

}

