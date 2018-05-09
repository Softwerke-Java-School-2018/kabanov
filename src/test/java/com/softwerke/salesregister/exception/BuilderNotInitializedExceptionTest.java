package com.softwerke.salesregister.exception;

import org.junit.Test;

import static com.softwerke.salesregister.io.ConsoleIOStreamTest.STUB;

public class BuilderNotInitializedExceptionTest {
    @Test(expected = BuilderNotInitializedException.class)
    public void askTest1() throws BuilderNotInitializedException {
        try {
            throw new BuilderNotInitializedException(STUB);
        } catch (BuilderNotInitializedException e) {
            throw new BuilderNotInitializedException(STUB + STUB);
        }
    }
}
