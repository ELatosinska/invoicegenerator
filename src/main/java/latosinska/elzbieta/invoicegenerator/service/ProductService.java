package latosinska.elzbieta.invoicegenerator.service;

import jakarta.annotation.Resource;
import latosinska.elzbieta.invoicegenerator.dto.ProductDTO;
import latosinska.elzbieta.invoicegenerator.exception.NoSuchCategoryException;
import latosinska.elzbieta.invoicegenerator.exception.NoSuchProductException;
import latosinska.elzbieta.invoicegenerator.model.Category;
import latosinska.elzbieta.invoicegenerator.model.Product;
import latosinska.elzbieta.invoicegenerator.repository.CategoryRepository;
import latosinska.elzbieta.invoicegenerator.repository.ProductRepository;
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

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(ProductDTO product) throws NoSuchCategoryException {
        return productRepository.save(getProductFromDTO(product));
    }

    public Product createProductWithGivenId(ProductDTO product, Long id) throws NoSuchCategoryException {
        Product newProduct;
        Optional<Category> category = categoryRepository.findById(product.categoryId());
        if (category.isEmpty()) throw new NoSuchCategoryException();
        if (product.netPrice() != null) {
            newProduct = new Product(id, product.name(), product.netPrice(), category.get());
        } else {
            newProduct = new Product(id, product.name(), category.get());
        }
        return productRepository.save(newProduct);
    }



    public Product updateProduct(ProductDTO product, Long id) throws NoSuchProductException, NoSuchCategoryException {
        Optional<Product> productToUpdate = productRepository.findById(id);
        if (productToUpdate.isEmpty()) throw new NoSuchProductException();
        Category category = Optional.of(product)
                .map(ProductDTO::categoryId)
                .flatMap(categoryId -> categoryRepository.findById(categoryId))
                .orElseThrow(NoSuchCategoryException::new);

        Product modifingProduct = productToUpdate.get();
        modifingProduct.setName(product.name());
        modifingProduct.setNetPrice(product.netPrice());
        modifingProduct.setCategory(category);
        return productRepository.save(modifingProduct);
    }

    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private Product getProductFromDTO(ProductDTO product) throws NoSuchCategoryException {
        Optional<Category> category = categoryRepository.findById(product.categoryId());
        if (category.isEmpty()) throw new NoSuchCategoryException();
        if (product.netPrice() != null) {
            return new Product(product.name(), product.netPrice(), category.get());
        }
        return new Product(product.name(), category.get());
    }

    public ProductDTO getDtoFromProduct(Product product) {
        return new ProductDTO(product.getId(), product.getName(), product.getCategory().getId(), product.getNetPrice());
    }
}
