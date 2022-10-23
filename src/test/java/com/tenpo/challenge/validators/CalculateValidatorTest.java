package com.tenpo.challenge.validators;

import com.tenpo.challenge.exceptions.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CalculateValidatorTest {

    @Test
    public void validateValuesOk() throws ValidationException {
        CalculateValidator.validateValues(1.0,2.0);
    }

    @Test
    public void validateValuesErrorWithValueANull() throws ValidationException {
        assertThrows(ValidationException.class, () -> CalculateValidator.validateValues(null,2.0));
    }

    @Test
    public void validateValuesErrorWithValueBNull() throws ValidationException {
        assertThrows(ValidationException.class, () -> CalculateValidator.validateValues(1.0,null));
    }

}
