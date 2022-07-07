package org.coderead.calculator;

import org.apache.commons.lang.StringUtils;
import org.coderead.model.Performance;

public abstract class AbstractPerfCalculator {

    public static AbstractPerfCalculator of(String playType) {
        try {
            return (AbstractPerfCalculator) Class.forName(getClassName(playType)).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("unknown type:" + playType);
        }
    }

    private static String getClassName(String playType) {
        return String.format("%s.%sCalculator", AbstractPerfCalculator.class.getPackage().getName(), StringUtils.capitalize(playType));
    }

    public abstract double getCredits(Performance performance);

    public abstract int getAmount(Performance performance);
}
