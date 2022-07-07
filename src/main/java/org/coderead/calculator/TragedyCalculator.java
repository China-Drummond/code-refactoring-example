package org.coderead.calculator;

import org.coderead.model.Performance;

public class TragedyCalculator extends AbstractPerfCalculator {
    @Override
    public double getCredits(Performance performance) {
        return Math.max(performance.getAudience() - 30, 0);
    }

    @Override
    public int getAmount(Performance performance) {
        int thisAmount;
        thisAmount = 40000;
        if (performance.getAudience() > 30) {
            thisAmount += 1000 * (performance.getAudience() - 30);
        }
        return thisAmount;
    }
}