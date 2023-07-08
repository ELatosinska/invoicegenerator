package latosinska.elzbieta.invoicegenerator.dto;

public class ProductDTO {
    private String name;
    private long categoryId;
    private Double netPrice;

    public ProductDTO() {
    }

    public ProductDTO(String name, Long categoryId) {
        this.name = name;
        this.categoryId = categoryId;
    }

    public ProductDTO(String name, Long categoryId, Double netPrice) {
        this.name = name;
        this.categoryId = categoryId;
        this.netPrice = netPrice;
    }

    public String getName() {
        return name;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public Double getNetPrice() {
        return netPrice;
    }
}
