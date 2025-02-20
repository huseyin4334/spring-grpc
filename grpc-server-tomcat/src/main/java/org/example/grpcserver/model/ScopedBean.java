package org.example.grpcserver.model;

import lombok.Getter;

@Getter
public class ScopedBean {
    private int value = 0;

    public int increment() {
        return ++value;
    }
}
