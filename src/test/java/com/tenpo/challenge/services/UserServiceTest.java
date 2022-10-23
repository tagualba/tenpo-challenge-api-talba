package com.tenpo.challenge.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.challenge.exceptions.ValidationException;
import com.tenpo.challenge.models.domain.User;
import com.tenpo.challenge.models.dtos.UserRequestDto;
import com.tenpo.challenge.models.dtos.UserResponseDto;
import com.tenpo.challenge.models.persistence.UserPersistence;
import com.tenpo.challenge.repositories.UsersPersistenceRepository;
import com.tenpo.challenge.translators.UsersTranslator;
import com.tenpo.challenge.utils.EncryptUtil;
import com.tenpo.challenge.utils.JwtUtil;
import com.tenpo.challenge.validators.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserValidator userValidator;

    @Mock
    private UsersPersistenceRepository usersPersistenceRepository;

    @Mock
    private UsersTranslator usersTranslator;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private EncryptUtil encryptUtil;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private UserService userService;


    @Test
    public void createUserOk() throws ValidationException, JsonProcessingException {
        UserRequestDto userRequestDto = UserRequestDto.builder().name("tagualba").email("tom@gmail.com").password("Looo.4481").build();
        User user = User.builder().name("tagualba").email("tom@gmail.com").hashPassword("adasdsada89d9a8d98sa").lastTokenApi("lskeinr").build();
        UserPersistence userPersistence = UserPersistence.builder().id(1l).name("tagualba").email("tom@gmail.com").hashPassword("ad98asd98sa").lastTokenApi("lskeinr").build();
        UserResponseDto userResponseDto = UserResponseDto.builder().id(1l).tokenApiKey("lskeinr").build();

        doNothing().when(userValidator).validateCreateUserRequestDto(userRequestDto);
        when(usersPersistenceRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(usersPersistenceRepository.save(userPersistence)).thenReturn(userPersistence);
        when(usersTranslator.toDomain(userRequestDto)).thenReturn(user);
        when(usersTranslator.toPersistence(user)).thenReturn(userPersistence);
        when(usersTranslator.toResponse(userPersistence)).thenReturn(userResponseDto);
        when(jwtUtil.createApiToken(any())).thenReturn("lskeinr");
        when(objectMapper.writeValueAsString(any())).thenReturn(userResponseDto.toString());

        UserResponseDto response = userService.createUser(userRequestDto);

        assertTrue(response.getId() == userResponseDto.getId());
        assertTrue(response.getTokenApiKey() == userResponseDto.getTokenApiKey());

    }

    @Test
    public void createUserErroRepatEmailOk() throws ValidationException, JsonProcessingException {
        UserRequestDto userRequestDto = UserRequestDto.builder().name("tagualba").email("tom@gmail.com").password("Looo.4481").build();
        User user = User.builder().name("tagualba").email("tom@gmail.com").hashPassword("adasdsada89d9a8d98sa").lastTokenApi("lskeinr").build();
        UserPersistence userPersistence = UserPersistence.builder().id(1l).name("tagualba").email("tom@gmail.com").hashPassword("ad98asd98sa").lastTokenApi("lskeinr").build();
        UserResponseDto userResponseDto = UserResponseDto.builder().id(1l).tokenApiKey("lskeinr").build();

        doNothing().when(userValidator).validateCreateUserRequestDto(userRequestDto);
        when(usersPersistenceRepository.findByEmail(any())).thenReturn(Optional.of(UserPersistence.builder().build()));

        assertThrows(ValidationException.class, () -> userService.createUser(userRequestDto));
    }

    @Test
    public void loginUserOk() throws ValidationException, JsonProcessingException {
        UserRequestDto userRequestDto = UserRequestDto.builder().email("tom@gmail.com").password("Looo.4481").build();
        UserPersistence userPersistence = UserPersistence.builder().id(1l).name("tagualba").email("tom@gmail.com").hashPassword("ad98asd98sa").lastTokenApi("lskeinr").build();
        UserResponseDto userResponseDto = UserResponseDto.builder().id(1l).tokenApiKey("lskeinr").build();

        doNothing().when(userValidator).validateLoginRequestDto(userRequestDto);
        when(usersPersistenceRepository.findByEmail(any())).thenReturn(Optional.of(userPersistence));
        when(objectMapper.writeValueAsString(any())).thenReturn(userResponseDto.toString());
        when(jwtUtil.validate(any())).thenReturn(true);
        when(usersTranslator.toResponse(userPersistence)).thenReturn(userResponseDto);
        when(encryptUtil.checkHash(any(), any(), any())).thenReturn(true);
        UserResponseDto response = userService.login(userRequestDto);

        assertTrue(response.getId() == 1l);
        assertTrue(response.getTokenApiKey() == "lskeinr");
    }

    @Test
    public void loginUserConbinationFailEmail() throws ValidationException, JsonProcessingException {
        UserRequestDto userRequestDto = UserRequestDto.builder().email("tom@gmail.com").password("Looo.4481").build();

        doNothing().when(userValidator).validateLoginRequestDto(userRequestDto);
        when(usersPersistenceRepository.findByEmail(any())).thenReturn(Optional.empty());

        assertThrows(ValidationException.class, () -> userService.login(userRequestDto));
    }

    @Test
    public void loginUserConbinationFailPass() throws ValidationException, JsonProcessingException {
        UserRequestDto userRequestDto = UserRequestDto.builder().email("tom@gmail.com").password("Looo.4481").build();
        UserPersistence userPersistence = UserPersistence.builder().id(1l).name("tagualba").email("tom@gmail.com").hashPassword("ad98asd98sa").lastTokenApi("lskeinr").build();

        doNothing().when(userValidator).validateLoginRequestDto(userRequestDto);
        when(usersPersistenceRepository.findByEmail(any())).thenReturn(Optional.of(userPersistence));
        when(encryptUtil.checkHash(any(), any(), any())).thenReturn(false);

        assertThrows(ValidationException.class, () -> userService.login(userRequestDto));
    }

}
