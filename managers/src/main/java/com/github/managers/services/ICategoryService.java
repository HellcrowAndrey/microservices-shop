package com.github.managers.services;

import com.github.managers.dto.CategoryDto;
import com.github.managers.services.impl.CategoryService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(
        name = "products",
        fallback = CategoryService.class,
        contextId = "categoryId"
)
public interface ICategoryService {

    @PostMapping(
            path = "/v1/categories/edit",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    Optional<CategoryDto> create(@RequestBody CategoryDto c);

    @GetMapping(
            path = "/v1/categories/fetch/{categoryName}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Optional<CategoryDto> readByName(@PathVariable String categoryName);

    @GetMapping(
            path = "/v1/categories",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Optional<List<CategoryDto>> readAll();

    @PutMapping(
            path = "/v1/categories/edit"
    )
    void update(@RequestBody CategoryDto c);

    @DeleteMapping(
            path = "/v1/categories/edit/{id}"
    )
    void delete(@PathVariable Long id);

}
