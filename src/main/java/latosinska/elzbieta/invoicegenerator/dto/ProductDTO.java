package latosinska.elzbieta.invoicegenerator.dto;

public class ProductDTO {
    private String name;
    private String category;
    private Double netPrice;

    public ProductDTO() {
    }

    public ProductDTO(String name, String categoryId) {
        this.name = name;
        this.category = categoryId;
    }

    public ProductDTO(String name, String categoryId, Double netPrice) {
        this.name = name;
        this.category = categoryId;
        this.netPrice = netPrice;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Double getNetPrice() {
        return netPrice;
    }
}
