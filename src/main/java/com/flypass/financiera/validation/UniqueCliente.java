package com.flypass.financiera.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueClienteValidator.class)
@Documented
public @interface UniqueCliente {
    String message() default "Ya existe un cliente registrado con este tipo y número de identificación";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
