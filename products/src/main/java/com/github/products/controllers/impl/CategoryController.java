package com.github.products.controllers.impl;

import com.github.products.controllers.ICategoryController;
import com.github.products.dto.CategoryDto;
import com.github.products.entity.Category;
import com.github.products.services.ICategoryService;
import com.github.products.utils.Logging;
import com.github.products.utils.TransferObj;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.products.utils.TransferObj.fromCategory;
import static com.github.products.utils.TransferObj.toCategory;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/categories")
public class CategoryController implements ICategoryController {

    private final ICategoryService categoryService;

    @Override
    @HystrixCommand
    @Logging(isTime = true, isReturn = false)
    public List<CategoryDto> findAllCategories() {
        return this.categoryService.read().stream()
                .map(TransferObj::fromCategory)
                .collect(Collectors.toList());
    }

    @Override
    @HystrixCommand
    @Logging(isTime = true, isReturn = false)
    public CategoryDto saveCategory(CategoryDto payload) {
        return fromCategory(this.categoryService.create(toCategory(payload)));
    }

    @Override
    @HystrixCommand
    @Logging(isTime = true, isReturn = false)
    public CategoryDto findByName(String name) {
        return fromCategory(this.categoryService.readByName(name));
    }

    @Override
    @HystrixCommand
    @Logging(isTime = true, isReturn = false)
    public void update(CategoryDto payload) {
        Category category = this.categoryService.readById(payload.getId());
        category.setName(payload.getName());
        this.categoryService.update(category);
    }

    @Override
    @HystrixCommand
    @Logging(isTime = true, isReturn = false)
    public void remove(Long id) {
        this.categoryService.remove(id);
    }

}
