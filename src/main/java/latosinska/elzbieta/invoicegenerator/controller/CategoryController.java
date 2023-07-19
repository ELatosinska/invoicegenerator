package latosinska.elzbieta.invoicegenerator.controller;

import jakarta.annotation.Resource;
import latosinska.elzbieta.invoicegenerator.dto.CategoryDTO;
import latosinska.elzbieta.invoicegenerator.exception.NoSuchCategoryException;
import latosinska.elzbieta.invoicegenerator.model.Category;
import latosinska.elzbieta.invoicegenerator.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "localhost:8081")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategories(@RequestParam(required = false) String name) {
        List<CategoryDTO> categories = categoryService.getCategories().stream()
                .map(categoryService::getDTOFromCategory)
                .toList();
        if (categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CategoryDTO> getCategoryByName(@RequestParam String name) {
        Optional<Category> category = categoryService.getCategoryByName(name);
        if (category.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categoryService.getDTOFromCategory(category.get()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") Long id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            return new ResponseEntity<>(categoryService.getDTOFromCategory(category.get()), HttpStatus.OK); //TODO: change object in response to DTO
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO category) {
        return new ResponseEntity<>(categoryService.getDTOFromCategory(categoryService.createCategory(category)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO category, @PathVariable("id") Long id) {
        try {
            Category updatedCategory = categoryService.updateCategory(category, id);
            return new ResponseEntity<>(categoryService.getDTOFromCategory(updatedCategory), HttpStatus.OK);
        } catch (NoSuchCategoryException ex) {
            Category createdCategory = categoryService.createCategory(new CategoryDTO(id, category.name(), category.taxRateInPercent()));
            return new ResponseEntity<>(categoryService.getDTOFromCategory(createdCategory), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable("id") Long id) {
        try {
            categoryService.deleteCategory(id);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllCategories() {
        try {
            categoryService.deleteAllCategories();
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}