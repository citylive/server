package com.citylive.server.validation;

import com.citylive.server.dao.UserRepository;
import com.citylive.server.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UserValidator implements ConstraintValidator<UserExistsValidator, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UserExistsValidator constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Optional<User> user = userRepository.findById(value);
        return user.isPresent();
    }
}
