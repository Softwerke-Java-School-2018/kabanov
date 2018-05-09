package com.softwerke.salesregister.io;

import org.apache.logging.log4j.LogManager;

public class Logger {
    private static final org.apache.logging.log4j.Logger srLogger = LogManager.getLogger("SalesRegisterLogger");

    public static void trace(String string) {
        srLogger.trace(string);
    }

    public static void info(String string) {
        srLogger.info(string);
    }

    public static void warning(String string) {
        srLogger.warn(string);
    }

    public static void error(String string) {
        srLogger.error(string);
    }

    public static void debug(String string) {
        srLogger.debug(string);
    }

    public static void fatal(String string) {
        srLogger.fatal(string);
    }

    private Logger() {
    }
}
