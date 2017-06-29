package com.gmail.socraticphoenix.listclasses;

public @interface ListClass {

    boolean ignore() default false;

    boolean supplyList() default false;

    String listName() default "class_index.txt";

}
