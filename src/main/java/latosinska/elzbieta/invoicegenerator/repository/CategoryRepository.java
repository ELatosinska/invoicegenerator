package latosinska.elzbieta.invoicegenerator.repository;


import latosinska.elzbieta.invoicegenerator.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
