package com.annotation.processing.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/***
 * Simple annotation {@code Generator} that generates class named
 * suffixed wih <blockquote>_New</blockquote> in which class it was put.
 * Ex.
 * @Generator
 * public class Demo {
 *
 * }
 * Will generate.
 * @Generated
 * public class Demo_New {
 *
 *     public void init() {
 *
 *     }
 * }
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Generator {
}
