package com.github.products.repository;

import com.github.products.entity.Product;
import com.github.products.entity.EntityStatus;
import com.github.products.entity.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepo extends PagingAndSortingRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {

    Page<Product> findByStatus(EntityStatus status, Pageable pageable);

    Page<Product> findAllBySubcategoryName(String name, Pageable pageable);

    List<Product> findByNameContainingOrDescriptionContaining(String name, String description);

    @Modifying
    @Query(value = "UPDATE Product p SET p.status =:status WHERE p.id =:id")
    void updateStatus(
            @Param(value = "status") EntityStatus status,
            @Param(value = "id") Long id
    );

    @Modifying
    @Query(value = "UPDATE Product p SET p.percentBought =:percentBought WHERE p.id =:id")
    void updatePercentBough(
            @Param(value = "id") Long id,
            @Param(value = "percentBought") Double percentBought
    );

}
