package latosinska.elzbieta.invoicegenerator.service;

import jakarta.annotation.Resource;
import latosinska.elzbieta.invoicegenerator.dto.CategoryDTO;
import latosinska.elzbieta.invoicegenerator.exception.InvalidTaxRateException;
import latosinska.elzbieta.invoicegenerator.exception.NoSuchCategoryException;
import latosinska.elzbieta.invoicegenerator.model.Category;
import latosinska.elzbieta.invoicegenerator.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Resource
    CategoryRepository categoryRepository;

    public List<Category> getCategories() {
            return new ArrayList<>(categoryRepository.findAll());
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category createCategory(CategoryDTO category) throws InvalidTaxRateException {
            return categoryRepository.save(new Category(category.id(), category.name(), category.taxRateInPercent()));
    }

    public Category updateCategory(CategoryDTO updatedCategory, Long categoryToUpdateId) throws NoSuchCategoryException, InvalidTaxRateException {
        Optional<Category> categoryToChange = categoryRepository.findById(categoryToUpdateId);
        if (categoryToChange.isEmpty()) throw new NoSuchCategoryException();

        Category changedCategory = categoryToChange.get();
        changedCategory.setName(updatedCategory.name());
        changedCategory.setTaxRateInPercent(updatedCategory.taxRateInPercent());
        return categoryRepository.save(changedCategory);

    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public void deleteAllCategories() {
        categoryRepository.deleteAll();
    }
    public CategoryDTO getDTOFromCategory(Category category) {
        return new CategoryDTO(category.getId(), category.getName(), category.getTaxRateInPercent());
    }
}
