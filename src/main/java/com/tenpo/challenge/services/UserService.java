package com.tenpo.challenge.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.challenge.exceptions.ValidationException;
import com.tenpo.challenge.models.domain.User;
import com.tenpo.challenge.models.dtos.UserRequestDto;
import com.tenpo.challenge.models.dtos.UserResponseDto;
import com.tenpo.challenge.models.persistence.UserPersistence;
import com.tenpo.challenge.repositories.UsersPersistenceRepository;
import com.tenpo.challenge.statics.ErrorCode;
import com.tenpo.challenge.statics.Operations;
import com.tenpo.challenge.translators.UsersTranslator;
import com.tenpo.challenge.utils.EncryptUtil;
import com.tenpo.challenge.utils.GlobalsUtil;
import com.tenpo.challenge.utils.JwtUtil;
import com.tenpo.challenge.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UsersPersistenceRepository usersPersistenceRepository;

    @Autowired
    private UsersTranslator usersTranslator;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EncryptUtil encryptUtil;

    public UserResponseDto createUser(UserRequestDto userRequestDto) throws ValidationException, JsonProcessingException {
        userValidator.validateCreateUserRequestDto(userRequestDto);
        GlobalsUtil.setRequest(objectMapper.writeValueAsString(userRequestDto));
        GlobalsUtil.setOperation(Operations.SING_UP.name());

        if (usersPersistenceRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new ValidationException(ErrorCode.REPEAT_EMAIL);
        }

        User user = usersTranslator.toDomain(userRequestDto)
                .toBuilder()
                .lastTokenApi(jwtUtil.createApiToken(userRequestDto.getEmail()))
                .build();

        UserPersistence userPersistence = usersPersistenceRepository.save(usersTranslator.toPersistence(user));

        GlobalsUtil.setResponse(objectMapper.writeValueAsString(userPersistence));
        return usersTranslator.toResponse(userPersistence);
    }

    public UserResponseDto login(UserRequestDto request) throws ValidationException, JsonProcessingException {
        userValidator.validateLoginRequestDto(request);
        GlobalsUtil.setOperation(Operations.LOGIN.name());
        GlobalsUtil.setRequest(objectMapper.writeValueAsString(request));

        Optional<UserPersistence> userSaved = usersPersistenceRepository.findByEmail(request.getEmail());
        if (!userSaved.isPresent() || !encryptUtil.checkHash(request.getEmail(), request.getPassword(), userSaved.get().getHashPassword())) {
            throw new ValidationException(ErrorCode.COMBINATION_FAIL);
        }

        if (jwtUtil.validate(userSaved.get().getLastTokenApi())) {
            GlobalsUtil.setResponse(objectMapper.writeValueAsString(userSaved.get()));
            return usersTranslator.toResponse(userSaved.get());
        }

        UserResponseDto userPersistence = usersTranslator.toResponse(userSaved.get().toBuilder().lastTokenApi(jwtUtil.createApiToken(request.getEmail())).build());

        GlobalsUtil.setResponse(objectMapper.writeValueAsString(userPersistence));
        return userPersistence;
    }

}
