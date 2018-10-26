package hu.bme.szarch.ibdb.service;

import hu.bme.szarch.ibdb.domain.Category;
import hu.bme.szarch.ibdb.error.Errors;
import hu.bme.szarch.ibdb.error.ServerException;
import hu.bme.szarch.ibdb.repository.CategoryRepository;
import hu.bme.szarch.ibdb.service.dto.category.CategoryResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResult> getCategories() {
        return categoryRepository.findAll().stream().map(this::categoryToResult).collect(Collectors.toList());
    }

    public void addCategory(String name) {
        Category category = new Category();

        category.setName(name);

        categoryRepository.save(category);
    }

    public void deleteCategory(String id) {
        categoryRepository.delete(findCategoryById(id));
    }

    public Category findCategoryById(String id) {
        Optional<Category> category = categoryRepository.findById(id);

        if(!category.isPresent()) {
            throw new ServerException(Errors.NOT_FOUND);
        }

        return category.get();
    }

    private CategoryResult categoryToResult(Category category) {
        return CategoryResult.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

}
