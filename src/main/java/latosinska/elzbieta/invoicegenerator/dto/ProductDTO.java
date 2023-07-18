package latosinska.elzbieta.invoicegenerator.dto;

import lombok.Getter;

@Getter
public class ProductDTO {
    private String name;
    private Long categoryId;
    private Double netPrice;


    public ProductDTO(String name, Long categoryId) {
        this.name = name;
        this.categoryId = categoryId;
    }

    public ProductDTO(String name, Long categoryId, Double netPrice) {
        this.name = name;
        this.categoryId = categoryId;
        this.netPrice = netPrice;
    }
}
