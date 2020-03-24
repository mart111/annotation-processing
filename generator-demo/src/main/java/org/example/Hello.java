package org.example;

import com.annotation.processing.annotations.Component;
import com.annotation.processing.annotations.Generator;
import com.annotation.processing.annotations.PostProcessor;

@Generator
@Component
public class Hello {

    @PostProcessor
    public void init() {
        System.out.println("Hello from method");
    }

    public void hello() {
        System.out.println("Hello");

    }
}
