package com.tenpo.challenge.validators;

public abstract class Validators {

    public static String missingFieldDescription(String field) {
        return String.format("missing field: %s ", field);
    }

}
