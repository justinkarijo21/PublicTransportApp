package com.example.demo.model;

public enum TransportType {
    BUS("Bus"),
    TREIN("Trein"); // choose displayed label here

    private final String label;

    TransportType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}