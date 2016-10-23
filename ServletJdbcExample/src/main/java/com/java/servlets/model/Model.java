package com.java.servlets.model;

public abstract class Model {
    private Long id;

    public Model() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}