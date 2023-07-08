package latosinska.elzbieta.invoicegenerator.repository;

import latosinska.elzbieta.invoicegenerator.model.Category;
import latosinska.elzbieta.invoicegenerator.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByCategory(Category category);
}
