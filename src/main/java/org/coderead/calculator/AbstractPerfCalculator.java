package org.coderead.calculator;

import org.apache.commons.lang.StringUtils;
import org.coderead.model.Performance;

public abstract class AbstractPerfCalculator {

    public static AbstractPerfCalculator of(String type) {
        try {
            return (AbstractPerfCalculator) Class.forName(formatClassName(type)).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("unknown type:" + type);
        }
    }

    private static String formatClassName(String type) {
        return String.format("org.coderead.calculator.%sCalculator", StringUtils.capitalize(type));
    }

    public abstract double getCredits(Performance performance);

    public abstract int getAmount(Performance performance);
}
