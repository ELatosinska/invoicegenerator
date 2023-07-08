package latosinska.elzbieta.invoicegenerator.controller;

import latosinska.elzbieta.invoicegenerator.dto.ProductDTO;
import latosinska.elzbieta.invoicegenerator.model.Category;
import latosinska.elzbieta.invoicegenerator.model.Product;
import latosinska.elzbieta.invoicegenerator.repository.CategoryRepository;
import latosinska.elzbieta.invoicegenerator.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "localhost:8081")
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false, name="category") String categoryName) {
        try {
            Optional<Category> productsCategory = Optional.ofNullable(categoryRepository.findByName(categoryName));
            List<Product> products = new ArrayList<>();
            if(categoryName.isEmpty()) {
                products = productRepository.findAll();
            }else {
                if (productsCategory.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } else {
                    products.addAll(productRepository.findAllByCategory(productsCategory.get()));
                }
            }
            if (products.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            else
                return new ResponseEntity<>(products, HttpStatus.OK);


        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO product) {
        try{
            Product createdProduct;
            Optional<Category> productCategory = categoryRepository.findById(product.getCategoryId());
            if(productCategory.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            if(product.getNetPrice()==null) {
                createdProduct = productRepository.save(new Product(product.getName(), productCategory.get()));
            } else {
                createdProduct = productRepository.save(new Product(product.getName(), product.getNetPrice(), productCategory.get()));
            }
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        }catch(Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
