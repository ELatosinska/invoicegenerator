package latosinska.elzbieta.invoicegenerator.service;

import jakarta.annotation.Resource;
import latosinska.elzbieta.invoicegenerator.dto.ProductDTO;
import latosinska.elzbieta.invoicegenerator.exceptions.NoSuchCategoryException;
import latosinska.elzbieta.invoicegenerator.model.Category;
import latosinska.elzbieta.invoicegenerator.model.Product;
import latosinska.elzbieta.invoicegenerator.repository.CategoryRepository;
import latosinska.elzbieta.invoicegenerator.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Resource
    private ProductRepository productRepository;
    @Resource
    private CategoryRepository categoryRepository;

    public static Double calculateGrossPrice(Product product) {
        return PriceService.roundPrice(product.getNetPrice() * ((double) product.getCategory().getTaxRateInPercent() / 100 + 1));
    }

    public ResponseEntity<List<Product>> getAllProducts(String categoryName) {
        try {
            List<Product> products;
            Optional<Category> productsCategory = categoryRepository.findByName(categoryName);
            if (productsCategory.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                products = new ArrayList<>(productRepository.findAllByCategory(productsCategory.get()));
            }

            if (products.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            else
                return new ResponseEntity<>(products, HttpStatus.OK);


        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products;
            products = productRepository.findAll();
            if (products.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            else
                return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Product> getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Product> createProduct(ProductDTO product) {
        try {
            Product createdProduct;
            Optional<Category> productCategory = categoryRepository.findById(product.getCategoryId());
            if (productCategory.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            if (product.getNetPrice() == null) {
                createdProduct = productRepository.save(new Product(product.getName(), productCategory.get()));
            } else {
                createdProduct = productRepository.save(new Product(product.getName(), product.getNetPrice(), productCategory.get()));
            }
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Product> updateProduct(ProductDTO product, Long id) {
        try {
            Optional<Product> productToUpdate = productRepository.findById(id);Category category = Optional.of(product)
                    .map(ProductDTO::getCategoryId)
                    .map(categoryId -> categoryRepository.findById(categoryId))
                    .flatMap(foundCategory -> foundCategory)
                    .orElseThrow(NoSuchCategoryException::new);
            if (productToUpdate.isEmpty()) {

                Product newProduct = productRepository.save(new Product(product.getName(), product.getNetPrice(), category));
                return  new ResponseEntity<>(newProduct, HttpStatus.CREATED);
            }
            Product modifingProduct = productToUpdate.get();
            modifingProduct.setName(product.getName());
            modifingProduct.setNetPrice(product.getNetPrice());
            modifingProduct.setCategory(category);
            return new ResponseEntity<>(productRepository.save(modifingProduct), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteAllProducts() {
        try {
            productRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
