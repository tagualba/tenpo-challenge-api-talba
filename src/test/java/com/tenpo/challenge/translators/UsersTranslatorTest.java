package com.tenpo.challenge.translators;

import com.tenpo.challenge.models.domain.User;
import com.tenpo.challenge.models.dtos.UserRequestDto;
import com.tenpo.challenge.models.dtos.UserResponseDto;
import com.tenpo.challenge.models.persistence.UserPersistence;
import com.tenpo.challenge.utils.EncryptUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsersTranslatorTest {

    @Mock
    private EncryptUtil encryptUtil;
    @InjectMocks
    UsersTranslator usersTranslator;


    @Test
    public void toDomainOk(){
        UserRequestDto requestDto = UserRequestDto.builder().name("pepe").password("Pepe.44444").email("pepe@.com").build();

        when(encryptUtil.createHash(any(), any())).thenReturn("adasdsad");

        User user = usersTranslator.toDomain(requestDto);
        assertEquals(user.getName(), requestDto.getName());
        assertEquals(user.getEmail(), requestDto.getEmail());
    }

    @Test
    public void toPersistenceOk(){
        User user = User.builder().name("pepe").hashPassword("Pepe.44444").email("pepe@.com").lastTokenApi("123").build();
        UserPersistence userPersistence = usersTranslator.toPersistence(user);
        assertEquals(userPersistence.getName(), user.getName());
        assertEquals(userPersistence.getEmail(), userPersistence.getEmail());
        assertEquals(userPersistence.getHashPassword(), user.getHashPassword());
        assertEquals(userPersistence.getLastTokenApi(), user.getLastTokenApi());
    }

    @Test
    public void persistencetToDomainOk(){
        UserPersistence userPersistence = UserPersistence.builder().id(1l).name("pepe").hashPassword("Pepe.44444").email("pepe@.com").lastTokenApi("asdasd").build();
        User user = usersTranslator.toDomain(userPersistence);
        assertEquals(user.getId(), userPersistence.getId());
        assertEquals(user.getName(), userPersistence.getName());
        assertEquals(user.getEmail(), userPersistence.getEmail());
        assertEquals(user.getHashPassword(), userPersistence.getHashPassword());
        assertEquals(user.getLastTokenApi(), userPersistence.getLastTokenApi());
    }

    @Test
    public void toResponseOk(){
        UserPersistence userPersistence = UserPersistence.builder().id(1l).name("pepe").hashPassword("Pepe.44444").email("pepe@.com").lastTokenApi("asdasd").build();
        UserResponseDto response = usersTranslator.toResponse(userPersistence);
        assertEquals(userPersistence.getId(), response.getId());
        assertEquals(userPersistence.getLastTokenApi(), response.getTokenApiKey());
    }
}
