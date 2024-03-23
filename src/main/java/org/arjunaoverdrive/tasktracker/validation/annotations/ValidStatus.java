package org.arjunaoverdrive.tasktracker.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.arjunaoverdrive.tasktracker.validation.validators.ValidStatusValidator;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = ValidStatusValidator.class)
public @interface ValidStatus {
    String message() default "Task status can only have one of the following values (case sensitive): TODO, IN_PROGRESS, DONE";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
