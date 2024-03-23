package org.arjunaoverdrive.tasktracker.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.arjunaoverdrive.tasktracker.validation.validators.AtLeastOneFilledValidator;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Documented
@Constraint(validatedBy = AtLeastOneFilledValidator.class)
public @interface AtLeastOneFilled {

    String message() default "At least one field must be specified!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
