package com.softwerke.salesregister.io;

import org.junit.Test;

import static com.softwerke.salesregister.io.ConsoleIOStreamTest.STUB;

public class LoggerTest {
    @Test
    public void traceTest() {
        Logger.trace(STUB);
    }
    @Test
    public void debugTest() {
        Logger.debug(STUB);
    }
    @Test
    public void infoTest() {
        Logger.info(STUB);
    }
    @Test
    public void warningTest() {
        Logger.warning(STUB);
    }
    @Test
    public void errorTest() {
        Logger.error(STUB);
    }
    @Test
    public void fatalTest() {
        Logger.fatal(STUB);
    }
}
