package com.tenpo.challenge.models.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonDeserialize(builder = UserResponseDto.UserResponseDtoBuilder.class)
public class UserResponseDto {

    private Long id;

    private String tokenApiKey;

}
