package com.softwerke.salesregister.io;

import org.apache.logging.log4j.LogManager;

public class Logger {
    private static org.apache.logging.log4j.Logger logger = LogManager.getLogger("SalesRegisterLogger");

    public static void trace(String string) {
        logger.trace(string);
    }

    public static void info(String string) {
        logger.info(string);
    }

    public static void warning(String string) {
        logger.warn(string);
    }

    public static void error(String string) {
        logger.error(string);
    }

    public static void debug(String string) {
        logger.debug(string);
    }

    public static void fatal(String string) {
        logger.fatal(string);
    }

    private Logger() {
    }
}
