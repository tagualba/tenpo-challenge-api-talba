package com.tenpo.challenge.validators;

import com.tenpo.challenge.exceptions.ValidationException;
import com.tenpo.challenge.models.dtos.UserRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserValidatorTest {

    UserValidator userValidator;

    UserRequestDto userRequestDto;


    @BeforeEach
    public void init(){
        userValidator = new UserValidator("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?="
                + ".*[@#$%^&+=.])(?=\\S+$).{8,}$");
    }

    @Test
    public void validateRequestCreateUserOk() throws ValidationException {
        userRequestDto = UserRequestDto.builder()
                .name("name_test")
                .email("email@test.com")
                .password("Passss.44819")
                .build();

        userValidator.validateCreateUserRequestDto(userRequestDto);
    }

    @Test
    public void validateRequestCreateUserWithValidationExceptionOk() {
        userRequestDto = UserRequestDto.builder()
                .email("email@test.com")
                .password("Passss.44819")
                .build();

        assertThrows(ValidationException.class, () -> userValidator.validateCreateUserRequestDto(userRequestDto)) ;
    }

    @Test
    public void validateRequestCreateUserWithValidationExceptionForRegexPass() {
        userRequestDto = UserRequestDto.builder()
                .name("name_test")
                .email("email@test.com")
                .password("assss44819")
                .build();

        assertThrows(ValidationException.class, () -> userValidator.validateCreateUserRequestDto(userRequestDto)) ;
    }

    @Test
    public void validateRequestCreateUserWithValidationExceptionForRegexEmail() {
        userRequestDto = UserRequestDto.builder()
                .name("pepe")
                .email("emailtest.com")
                .password("Passss.44819")
                .build();

        assertThrows(ValidationException.class, () -> userValidator.validateCreateUserRequestDto(userRequestDto)) ;
    }

    @Test
    public void validateLoginRequestOk() throws ValidationException {
        userRequestDto = UserRequestDto.builder()
                .email("email@test.com")
                .password("Passss.44819")
                .build();

        userValidator.validateLoginRequestDto(userRequestDto);
    }

    @Test
    public void validateLoginRequesWithValidationExceptionOk() {
        userRequestDto = UserRequestDto.builder()
                .build();

        assertThrows(ValidationException.class, () -> userValidator.validateLoginRequestDto(userRequestDto)) ;
    }

    @Test
    public void validateLoginRequesWithValidationExceptionForRegexEmail() {
        userRequestDto = UserRequestDto.builder()
                .email("emailtest.com")
                .password("Passss.44819")
                .build();

        assertThrows(ValidationException.class, () -> userValidator.validateLoginRequestDto(userRequestDto)) ;
    }


}
