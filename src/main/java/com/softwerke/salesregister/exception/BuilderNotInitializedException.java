package com.softwerke.salesregister.exception;

public class BuilderNotInitializedException extends Exception {
    public BuilderNotInitializedException() {
        super();
    }

    public BuilderNotInitializedException(String message) {
        super(message);
    }
}
