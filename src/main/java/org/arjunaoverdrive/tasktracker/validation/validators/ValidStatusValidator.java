package org.arjunaoverdrive.tasktracker.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.arjunaoverdrive.tasktracker.model.TaskStatus;
import org.arjunaoverdrive.tasktracker.validation.annotations.ValidStatus;

import java.util.Arrays;
import java.util.List;

public class ValidStatusValidator implements ConstraintValidator<ValidStatus, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<String> values = Arrays.stream(TaskStatus.values()).map(Enum::name).toList();
        return values.contains(value);
    }
}
