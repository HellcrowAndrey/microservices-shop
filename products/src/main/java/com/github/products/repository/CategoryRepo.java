package com.github.products.repository;

import com.github.products.entity.Category;
import com.github.products.entity.EntityStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends PagingAndSortingRepository<Category, Long>, JpaRepository<Category, Long> {

    List<Category> findAll(Sort sort);

    Category findByName(String name);

    @Modifying
    @Query(value = "update Category c set c.status=:status where c.id=:id")
    void updateStatus(
            @Param(value = "id") Long id,
            @Param(value = "status") EntityStatus status
    );

}
