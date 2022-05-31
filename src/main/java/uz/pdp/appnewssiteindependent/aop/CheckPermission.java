package uz.pdp.appnewssiteindependent.aop;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD) //qayerda ishlasin - method ustida
@Retention(RetentionPolicy.RUNTIME) //qachon ishlasin - runtime vaqtida
public @interface CheckPermission {

    String value();

}
