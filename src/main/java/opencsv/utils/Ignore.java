package opencsv.utils;

import java.lang.annotation.*;

@Documented

@Retention(RetentionPolicy.RUNTIME)

@Target({ElementType.FIELD})
public @interface Ignore {
}