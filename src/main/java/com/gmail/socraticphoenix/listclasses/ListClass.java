package com.gmail.socraticphoenix.listclasses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface ListClass {

    boolean ignore() default false;

    boolean supplyList() default false;

    String listName() default "class_index.txt";

}
