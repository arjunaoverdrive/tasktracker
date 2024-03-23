package org.arjunaoverdrive.tasktracker.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;
import org.arjunaoverdrive.tasktracker.validation.annotations.AtLeastOneFilled;

import java.lang.reflect.Field;

public class AtLeastOneFilledValidator implements ConstraintValidator<AtLeastOneFilled, Object> {
    @SneakyThrows
    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context) {

        Field[] declaredFields = dto.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            Object value = declaredField.get(dto);
            if(value != null){
                return true;
            }
        }
        return false;
    }
}
