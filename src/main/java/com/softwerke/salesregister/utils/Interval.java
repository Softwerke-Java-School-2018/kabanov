package com.softwerke.salesregister.utils;

import com.softwerke.salesregister.io.StringLiterals;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.function.Function;

public class Interval<T extends Comparable<T>> {
    private static final Logger logger = LogManager.getLogger(Interval.class);
    private final T leftBound;
    private final T rightBound;

    public Interval(T singleBound) {
        this.leftBound = this.rightBound = Objects.requireNonNull(singleBound);
    }

    public Interval(T leftBound, T rightBound) {
        this.leftBound = Objects.requireNonNull(leftBound);
        this.rightBound = Objects.requireNonNull(rightBound);
    }

    /**
     * Splits string into two substrings accordingly to a middlest space, then converts them into
     * two bounds with a given {@code Function}
     *
     * @param string string to be split
     * @param parser function which will be used to convert sting part into {@code <T extends Comparable<T>>}
     */
    public Interval(String string, Function<String, T> parser) {
        if (!ObjectUtils.allNotNull(string, parser)) {
            logger.fatal("One or more arguments is null! [Interval constructor]");
            throw new IllegalArgumentException(StringLiterals.NULL_ARG_EXC);
        }
        string = string.replaceAll("\\s+", " ");
        int centerSpaceIndex = Utils.centerIndexOf(string, ' ');
        if (centerSpaceIndex != -1) {
            String leftString = string.substring(0, centerSpaceIndex);
            String rightString = string.substring(centerSpaceIndex);
            this.leftBound = Objects.requireNonNull(parser.apply(leftString.trim()));
            this.rightBound = Objects.requireNonNull(parser.apply(rightString.trim()));
        } else {
            T singleBound = parser.apply(string.trim());
            this.leftBound = this.rightBound = Objects.requireNonNull(singleBound);
        }
    }

    public boolean contains(T value) {
        Objects.requireNonNull(value);
        return rightBound.compareTo(value) > -1 && leftBound.compareTo(value) < 1;
    }
}
