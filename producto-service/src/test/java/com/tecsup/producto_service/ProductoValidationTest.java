package com.tecsup.producto_service;
import com.tecsup.producto_service.Dto.ProductoRequest;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductoValidationTest {
    @Test
    void exigeNombreYPrecioPositivo() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            var validator = factory.getValidator();
            assertEquals(2, validator.validate(new ProductoRequest("", 0.0, 10)).size());
            assertFalse(validator.validate(new ProductoRequest("Laptop", null, 10)).isEmpty());
            assertFalse(validator.validate(new ProductoRequest("Laptop", -1.0, 10)).isEmpty());
            assertTrue(validator.validate(new ProductoRequest("Laptop", 2500.0, 10)).isEmpty());
        }
    }
}
