package com.tenpo.challenge.translators;

import com.tenpo.challenge.models.domain.User;
import com.tenpo.challenge.models.dtos.UserRequestDto;
import com.tenpo.challenge.models.dtos.UserResponseDto;
import com.tenpo.challenge.models.persistence.UserPersistence;
import com.tenpo.challenge.utils.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsersTranslator {

    @Autowired
    private EncryptUtil encryptUtil;

    public User toDomain(UserRequestDto requestDto){
        return User.builder()
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .hashPassword(encryptUtil.createHash(requestDto.getEmail(), requestDto.getPassword()))
                .build();
    }

    public UserPersistence toPersistence(User user){
        return UserPersistence.builder()
                .name(user.getName())
                .email(user.getEmail())
                .hashPassword(user.getHashPassword())
                .lastTokenApi(user.getLastTokenApi())
                .build();
    }

    public User toDomain(UserPersistence userPersistencePersistence){
        return User.builder()
                .id(userPersistencePersistence.getId())
                .name(userPersistencePersistence.getName())
                .email(userPersistencePersistence.getEmail())
                .hashPassword(userPersistencePersistence.getHashPassword())
                .lastTokenApi(userPersistencePersistence.getLastTokenApi())
                .build();
    }

    public UserResponseDto toResponse(UserPersistence user){
        return UserResponseDto.builder()
                .id(user.getId())
                .tokenApiKey(user.getLastTokenApi())
                .build();
    }

}
