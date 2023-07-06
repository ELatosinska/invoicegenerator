package latosinska.elzbieta.invoicegenerator.controller;

import latosinska.elzbieta.invoicegenerator.model.Category;
import latosinska.elzbieta.invoicegenerator.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class CategoryController{
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories(@RequestParam(required = false) String name) {
        try {
            List<Category> categories = new ArrayList<>();
            if (name == null) {
                categories.addAll(categoryRepository.findAll());
            } else {
                categories.addAll(categoryRepository.findByName(name));
            }
            if(categories.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
