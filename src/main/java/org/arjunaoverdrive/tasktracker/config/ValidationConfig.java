package org.arjunaoverdrive.tasktracker.config;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

@Configuration
public class ValidationConfig {
    @Bean
    public DefaultErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {

            @Override
            public void storeErrorInformation(Throwable error, ServerWebExchange exchange) {
                super.storeErrorInformation(error, exchange);
            }

            @Override
            public Map<String, Object> getErrorAttributes(ServerRequest serverRequest, ErrorAttributeOptions options) {
                Map<String, Object> errorAttributes = super.getErrorAttributes(serverRequest, options);

                errorAttributes.put("message", getError(serverRequest).getMessage());
                errorAttributes.put("status", HttpStatus.BAD_REQUEST.value());

                return errorAttributes;
            }
        };
    }

    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory =
                Validation.byProvider(HibernateValidator.class)
                        .configure()
                        .failFast(true)
                        .buildValidatorFactory();
        return validatorFactory.getValidator();
    }


}
