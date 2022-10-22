package com.tenpo.challenge.validators;

import com.tenpo.challenge.exceptions.ValidationException;
import com.tenpo.challenge.models.dtos.UserRequestDto;
import com.tenpo.challenge.statics.ErrorCode;
import io.micrometer.common.util.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserValidator extends Validators{
    @Value("${regex.email}")
    private String regexEmail;
    @Value("${regex.password}")
    private String regexPassword;

    public void validateCreateUserRequestDto(UserRequestDto request) throws ValidationException {

        String propertiesFailMessage = "";

        if (StringUtils.isEmpty(request.getName())) {
            propertiesFailMessage += missingFieldDescription("name");
        }

        if (StringUtils.isEmpty(request.getEmail())) {
            propertiesFailMessage += missingFieldDescription("email");
        }

        if (StringUtils.isEmpty(request.getPassword())) {
            propertiesFailMessage += missingFieldDescription("password");
        }

        if(!validateRegex(request.getEmail(), regexEmail)){
            propertiesFailMessage += regexFail("email");
        }

        if(!validateRegex(request.getPassword(), regexPassword)){
            propertiesFailMessage += regexFail("password");
        }

        if (propertiesFailMessage.length() > 0) {
            throw new ValidationException(ErrorCode.INVALID_DATA, propertiesFailMessage);
        }
    }


    public void validateLoginRequestDto(UserRequestDto request) throws ValidationException {
        String propertiesFailMessage = "";

        if (StringUtils.isEmpty(request.getEmail())) {
            propertiesFailMessage += missingFieldDescription("email");
        }

        if (StringUtils.isEmpty(request.getPassword())) {
            propertiesFailMessage += missingFieldDescription("password");
        }

        if(!validateRegex(request.getEmail(), regexEmail)){
            propertiesFailMessage += regexFail("email");
        }

        if (StringUtils.isNotEmpty(propertiesFailMessage)) {
            throw new ValidationException(ErrorCode.INVALID_DATA, propertiesFailMessage);
        }
    }

    private boolean validateRegex(String value, String regex){
        if(Strings.isEmpty(value) || Strings.isEmpty( regex)){
            return false;
        }

        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(value).matches();
    }

    private static String regexFail(String field) {
        return String.format("missing format in %s ", field);
    }


}
