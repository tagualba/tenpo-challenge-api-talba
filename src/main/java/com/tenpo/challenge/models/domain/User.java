package com.tenpo.challenge.models.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    private Long id;

    private String name;

    private String email;

    private String hashPassword;

    private String lastTokenApi;

}
