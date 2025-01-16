package com.example.dems.exception;

public class DuplicateDataEntry extends RuntimeException {
    public DuplicateDataEntry(String message) {
        super(message);
    }
}
