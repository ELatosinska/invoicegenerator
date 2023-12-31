package latosinska.elzbieta.invoicegenerator.controller;

import jakarta.annotation.Resource;
import latosinska.elzbieta.invoicegenerator.dto.ProductDTO;
import latosinska.elzbieta.invoicegenerator.exception.NoSuchCategoryException;
import latosinska.elzbieta.invoicegenerator.exception.NoSuchProductException;
import latosinska.elzbieta.invoicegenerator.model.Product;
import latosinska.elzbieta.invoicegenerator.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "localhost:4200")
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Resource
    ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts().stream().map(productService::getDtoFromProduct).toList();
        return products.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(productService.getDtoFromProduct(product.get()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO product) {
        try {
            Product newProduct = productService.createProduct(product);
            return new ResponseEntity<>(productService.getDtoFromProduct(newProduct), HttpStatus.CREATED);
        } catch (NoSuchCategoryException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO product, @PathVariable("id") Long id) {
        try {
            Product updatedProduct = productService.updateProduct(product, id);
            return new ResponseEntity<>(productService.getDtoFromProduct(updatedProduct), HttpStatus.OK);
        } catch (NoSuchProductException exception) {
            try {
                Product newProduct = productService.createProductWithGivenId(product, id);
                return new ResponseEntity<>(productService.getDtoFromProduct(newProduct), HttpStatus.CREATED);

            } catch (NoSuchCategoryException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (NoSuchCategoryException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllProducts() {
        productService.deleteAllProducts();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
