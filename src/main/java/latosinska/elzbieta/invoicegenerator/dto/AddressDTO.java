package latosinska.elzbieta.invoicegenerator.dto;

public record AddressDTO(Long id, String street, String buildingNumber, String apartmentNumber, String city, String postalCode, String country) {}