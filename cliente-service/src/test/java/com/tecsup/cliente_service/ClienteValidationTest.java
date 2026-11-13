package com.tecsup.cliente_service;
import com.tecsup.cliente_service.Dto.ClienteRequest;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteValidationTest {
    @Test
    void exigeCamposYExactamenteOchoDigitos() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            var validator = factory.getValidator();
            assertEquals(5, validator.validate(new ClienteRequest("", "", "", "", null)).size());
            for (String dni : new String[]{"1234567", "123456789", "1234567A"}) {
                assertFalse(validator.validate(new ClienteRequest("Carlos", "Ramirez", "Lima", "ACTIVO", dni)).isEmpty());
            }
            assertTrue(validator.validate(new ClienteRequest("Carlos", "Ramirez", "Lima", "ACTIVO", "01234567")).isEmpty());
        }
    }
}
